package GodComplexes;

import java.util.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import utility_classes.*;

// Metrics mostly based on the work by Dr. Radu Marinescu:
// "Assessing and Improving Object-Oriented Design"
//   http://www.cnatdcu.ro/wp-content/uploads/2012/06/Teza-de-abilitare3.pdf
// "Measurement and Quality in Object-Oriented Design"
//   http://loose.upt.ro/download/papers/radum-phd-icsm05.pdf

// God Class = (WMC > VERY_HIGH) and (ATFD > FEW) and (TCC < ONE_THIRD)
//   https://books.google.com/books?id=gdLbgnaMaa0C&pg=PA16

public class GodClassCheck
{
  private final Integer ATFD_THRES = 5;
  private final Double TCC_THRES = 0.33;
  private final Integer WMC_LOW_THRES = 5;
  private final Integer WMC_AV_THRES = 14;
  private final Integer WMC_HIGH_THRES = 47;

  // Access To Foreign Data
  public Integer getATFD(ClassOrInterfaceDeclaration clin)
  {
    List<SimpleName> declaredMethods = new ArrayList<>();
    List<MethodCallExpr> allMethodCalls = new ArrayList<>();
    List<SimpleName> externalMethodCalls = new ArrayList<>();

    for (MethodDeclaration dm : clin.getMethods())
      declaredMethods.add(dm.getName());

    allMethodCalls.addAll(clin.findAll(MethodCallExpr.class));

    for (MethodCallExpr mcall : allMethodCalls)
    {
      if (!declaredMethods.contains(mcall.getName()) && !externalMethodCalls.contains(mcall.getName()))
        externalMethodCalls.add(mcall.getName()); // method is not declared within the class and counted once
    }
    System.out.println(externalMethodCalls.size());
    return externalMethodCalls.size();
  }

  // Weighted Method Count
  public Double getWMC(ClassOrInterfaceDeclaration clin)
  {
    return 1.0;
  }
  // Tight Class Cohesion
  public Integer getTCC(ClassOrInterfaceDeclaration clin)
  {
    return 1;
  }


  public ThreatLevel checkGodClass(ClassOrInterfaceDeclaration clin)
  {
    //int atfd = getATFD(clin);
    // double wmc = getWMC(clin);
    // int tcc = getTCC(clin);
    // if (atfd > ATFD_THRES) && (wmc > WMC_HIGH_THRES) && (tcc > TCC_THRES)
    //   return ThreatLevel.HIGH;
    //
    //
    return ThreatLevel.NONE;
  }
}
