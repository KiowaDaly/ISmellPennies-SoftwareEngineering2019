package GodComplexes;

import java.util.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import utility_classes.*;
import BloatCheckers.*;

// Metrics mostly based on the work by Dr. Radu Marinescu:
// "Assessing and Improving Object-Oriented Design"
//   http://www.cnatdcu.ro/wp-content/uploads/2012/06/Teza-de-abilitare3.pdf
// "Measurement and Quality in Object-Oriented Design"
//   http://loose.upt.ro/download/papers/radum-phd-icsm05.pdf

// God Class = (WMC > VERY_HIGH) and (ATFD > FEW) and (TCC < ONE_THIRD)

public class GodClassCheck
{
  private ClassOrInterfaceDeclaration clin;

  private final Integer ATFD_THRES = 5;
  private final Double TCC_THRES = 0.33;
  private final Double WMC_LOW_THRES = 4.48;
  private final Double WMC_AV_THRES = 14.0;
  private final Double WMC_HIGH_THRES = 31.2;

  private List<MethodDeclaration> declaredMethods = new ArrayList<>();
  private List<MethodCallExpr> allMethodCalls = new ArrayList<>();
  private List<MethodCallExpr> externalMethodCalls = new ArrayList<>();

  private Integer classLinesOfCode = 0;
  private Integer numberOfMethods = 0;

  public GodClassCheck(ClassOrInterfaceDeclaration clin)
  {
    this.clin = clin;
    for (MethodDeclaration dm : clin.getMethods())
      declaredMethods.add(dm);

    allMethodCalls.addAll(clin.findAll(MethodCallExpr.class));

    externalMethodCalls.addAll(allMethodCalls); // add allMethodCalls to externalMethodCalls

    for (MethodCallExpr amc : allMethodCalls) // filter out any calls to declared in the class methods
      declaredMethods.forEach((dmc) -> {if (dmc.getName().equals(amc.getName())) externalMethodCalls.remove(amc);});

    ClassBloatChecks stats = new ClassBloatChecks();
    classLinesOfCode = stats.getNumLines(clin);
    numberOfMethods = stats.getNumMethods(clin);
  }

  // Access To Foreign Data
  public Integer getATFD()
  {
    return externalMethodCalls.size();
  }

  // Cyclomatic Complexity, used in the WMC calculation
  //   https://www.leepoint.net/principles_and_practices/complexity/complexity-java-method.html
  private Integer calcCYCLO()
  {
    int cyclo = 0;
    List<Statement> statements = new ArrayList<>();
    statements.addAll(clin.findAll(Statement.class));
    for (Statement statement : statements)
    {
      if (statement.isBreakStmt() || statement.isContinueStmt() || statement.isDoStmt() ||  statement.isForStmt() || statement.isTryStmt() ||
          statement.isIfStmt() || statement.isReturnStmt() || statement.isSwitchStmt() || statement.isThrowStmt() || statement.isWhileStmt())
      {
        cyclo++;
      }
    }
    return cyclo;
  }

  // Weighted Method Count, WMC = CYCLO/LOC * LOC/MethodNum * NOM
  // CYCLO/LOC - average cyclomatic number per line of code
  // LOC/MethodNum - average lines of code per method
  // NOM - number of methods in the class
  public Double getWMC()
  {
    int methodLinesOfCode = 0;
    int CYCLO = calcCYCLO();
    MethodBloatChecks localStats = new MethodBloatChecks();
    for (MethodDeclaration dm : clin.getMethods())
      methodLinesOfCode += localStats.getNumLines(dm);

    return (double) CYCLO/classLinesOfCode * methodLinesOfCode/numberOfMethods * numberOfMethods;
  }

// Map class fields to local methods using them, used by getTCC()
  private Map<String, List<MethodDeclaration>> getMethodVarPairs()
  {
    Map<String, List<MethodDeclaration>> methodFieldAccesses = new HashMap<>();
    List<FieldDeclaration> fields = new ArrayList<>();

    fields.addAll(clin.findAll(FieldDeclaration.class)); // store class fields
    for (MethodDeclaration mdec : declaredMethods) // iterate methods
    {
      for (AssignExpr assign : mdec.findAll(AssignExpr.class)) // iterate assignments within method
      {
        for (FieldDeclaration field : fields) // iterate class fields
        {
          if (assign.getTarget().toString().equals(field.getVariables().get(0).getName().toString())) // assignment(var name) matches class field
          {
            AssignExpr varMatch = assign;
            if (mdec.findFirst(VariableDeclarationExpr.class).isPresent()) // method has local variables
            {
              List<String> localVariables = new ArrayList<>();
              for (VariableDeclarationExpr locVar : mdec.findAll(VariableDeclarationExpr.class))
                localVariables.add(locVar.getVariables().get(0).getName().toString()); // local variable names

              if (!localVariables.contains(assign.getTarget().toString())) // local variable does not overwrite class field
                varMatch = assign;
              else
                break;
            }
            List<MethodDeclaration> relatedMethods = methodFieldAccesses.get(varMatch.getTarget().toString());
            if (relatedMethods == null || !relatedMethods.contains(mdec)) // count only 1 call per method per field
              methodFieldAccesses.computeIfAbsent(varMatch.getTarget().toString(), k -> new ArrayList<>()).add(mdec); // add related methods to field key
          }
        }
      }
    }
    return methodFieldAccesses;
  }

  // Tight Class Cohesion
  public Double getTCC()
  {
    Map<String, List<MethodDeclaration>> methodFieldAccesses = getMethodVarPairs();
    int methodPairs = numberOfMethods * (numberOfMethods-1)/2;
    int relatedMethodPairs = 0;
    for (List<MethodDeclaration> l : methodFieldAccesses.values())
      relatedMethodPairs+= l.size()*(l.size()-1)/2;
    return (double)relatedMethodPairs/methodPairs;
  }


  public ThreatLevel checkGodClass()
  {
    int atfd = getATFD();
    double wmc = getWMC();
    double tcc = getTCC();
    // if ((atfd > ATFD_THRES) && (wmc > WMC_HIGH_THRES) && (tcc > TCC_THRES))
    //   return ThreatLevel.HIGH;
    // if ((atfd > ATFD_THRES) && (wmc > WMC_AV_THRES) && (tcc > TCC_THRES))
    //   return ThreatLevel.MEDIUM;
    // if ((atfd > ATFD_THRES) && (wmc > WMC_LOW_THRES) && (tcc > TCC_THRES))
    //   return ThreatLevel.LOW;

    return ThreatLevel.NONE;
  }
}
