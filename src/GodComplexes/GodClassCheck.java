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
  private final Integer ATFD_THRES = 5;
  private final Double TCC_THRES = 0.33;
  private final Double WMC_LOW_THRES = 4.48;
  private final Double WMC_AV_THRES = 14.0;
  private final Double WMC_HIGH_THRES = 31.2;
  //private final Double WMC_V_HIGH_THRES = 105.3;

  private List<SimpleName> declaredMethods = new ArrayList<>();
  private Integer classLinesOfCode = 0;
  private Integer numberOfMethods = 0;

  private void calcStats(ClassOrInterfaceDeclaration clin)
  {
    for (MethodDeclaration dm : clin.getMethods())
      declaredMethods.add(dm.getName());

    ClassBloatChecks stats = new ClassBloatChecks();
    classLinesOfCode = stats.getNumLines(clin);
    numberOfMethods = stats.getNumMethods(clin);
  }

  // Access To Foreign Data
  public Integer getATFD(ClassOrInterfaceDeclaration clin)
  {
    List<MethodCallExpr> allMethodCalls = new ArrayList<>();
    List<SimpleName> externalMethodCalls = new ArrayList<>();
    calcStats(clin);
    allMethodCalls.addAll(clin.findAll(MethodCallExpr.class));
    for (MethodCallExpr mcall : allMethodCalls)
    {
      if (!declaredMethods.contains(mcall.getName()) && !externalMethodCalls.contains(mcall.getName()))
        externalMethodCalls.add(mcall.getName()); // method is not declared within the class and counted once
    }
    return externalMethodCalls.size();
  }

  // Cyclomatic Complexity, used in the WMC calculation
  //   https://www.leepoint.net/principles_and_practices/complexity/complexity-java-method.html
  private Integer calcCYCLO(ClassOrInterfaceDeclaration clin)
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
  public Double getWMC(ClassOrInterfaceDeclaration clin)
  {
    int methodLinesOfCode = 0;
    int CYCLO = calcCYCLO(clin);
    calcStats(clin);
    MethodBloatChecks localStats = new MethodBloatChecks();
    for (MethodDeclaration dm : clin.getMethods())
      methodLinesOfCode += localStats.getNumLines(dm);

    return (double) CYCLO/classLinesOfCode * methodLinesOfCode/numberOfMethods * numberOfMethods;
  }

  // Tight Class Cohesion
  public Integer getTCC(ClassOrInterfaceDeclaration clin)
  {
    return 1;
  }


  public ThreatLevel checkGodClass(ClassOrInterfaceDeclaration clin)
  {
    int atfd = getATFD(clin);
    double wmc = getWMC(clin);
    int tcc = getTCC(clin);
    if ((atfd > ATFD_THRES) && (wmc > WMC_HIGH_THRES) && (tcc > TCC_THRES))
      return ThreatLevel.HIGH;

    return ThreatLevel.NONE;
  }
}
