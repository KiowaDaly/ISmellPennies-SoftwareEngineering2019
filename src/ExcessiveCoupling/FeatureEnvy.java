package ExcessiveCoupling;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.symbolsolver.javaparsermodel.declarators.VariableSymbolDeclarator;
import com.google.errorprone.annotations.Var;
import utility_classes.ThreatLevel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeatureEnvy implements ExcessiveCoupling<Integer, ClassOrInterfaceDeclaration> {

    //CHECK ALL FIELDS OF A CLASS, IF A FIELD IS A CLASS/INTERFACE CALL THEN WE INCREMENT NUMBER OF CLASS INSTANCES IN A CLASS
    public Integer getNumClassInstances(ClassOrInterfaceDeclaration ci) {
        List<VariableDeclarator> classInstances = new ArrayList<>();
        for (FieldDeclaration f : ci.getFields()) {
            for (VariableDeclarator vl : f.getVariables()) {
                if (vl.getType().isClassOrInterfaceType()) {
                    classInstances.add(vl);
                }
            }
        }
        return classInstances.size();
    }

    //CHECK ALL METHOD DECLARATIONS OF A CLASS, IF THE METHOD DEC IS AN EXTERNAL METHOD CALL THEN  INCREMENT NUMBER OF METHOD CALLS
    public Integer getNumMethodCalls(ClassOrInterfaceDeclaration ci) {
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        for (MethodDeclaration m : ci.getMethods()) {
            methodDeclarations.add(m);
            methodCalls.addAll(m.findAll(MethodCallExpr.class));
        }

        for (int i = 0; i < methodCalls.size(); i++) {
            for (int j = 0; j < methodDeclarations.size(); j++) {
                if (methodCalls.get(i).getName().equals(methodDeclarations.get(j).getName())) {
                    methodCalls.remove(i);
                }
            }
        }
        return methodCalls.size();
    }

    public Integer getNumVsriableCalls(ClassOrInterfaceDeclaration ci) {
        List<FieldAccessExpr> variableCalls = new ArrayList<>();
        variableCalls.addAll(ci.findAll(FieldAccessExpr.class));
        for (int i = 0; i < variableCalls.size(); i++) {
            if (variableCalls.get(i).toString().contains("this.")) {
                //variableCalls.remove(i);
                variableCalls.remove(i);
            }
        }
        return variableCalls.size();
    }

    public boolean isMiddleMan(ClassOrInterfaceDeclaration ci) {
        List<MethodDeclaration> middleManMethods = new ArrayList<>();
        for (MethodDeclaration md : ci.getMethods()) {
            List<MethodCallExpr> methodCallExprs = md.getBody().get().findAll(MethodCallExpr.class);
            int numberLines = md.getEnd().get().line - md.getBegin().get().line - 1;
            if (methodCallExprs.size() == numberLines) middleManMethods.add(md);
        }

        return ((double) middleManMethods.size() / (double) ci.getMethods().size() > 0.5);
    }

    //CHECK ALL METHOD DECLARATIONS IN A CLASS, CHECK NUMBER OF METHOD EXPRESSIONS IN EACH METHOD...
    //...THEN CHECK THE NUMBER OF LINES IN EACH METHOD
    //... PUT ONE OVER THE OTHER IF THAT IS GREATER THAN 50%, THE METHOD IS GUILTY OF FEATURE ENVY
    //CLASS OBTAINS HIGH MEDIUM OR LOW STATUS FOR HOW MANY METHODS HAVE FEATURE ENVY
    public ThreatLevel isFeatureEnvy(ClassOrInterfaceDeclaration ci) {
        List<MethodDeclaration> FeatureEnvyMethods = new ArrayList<>();
        for (MethodDeclaration md : ci.getMethods()) {
            List<MethodCallExpr> methodCallExprs = md.getBody().get().findAll(MethodCallExpr.class);
            int numberLines = md.getEnd().get().line - md.getBegin().get().line - 1;
            if ((double)methodCallExprs.size() /((double)numberLines - (double)methodCallExprs.size() ) > 0.5) FeatureEnvyMethods.add(md);
        }
        if(FeatureEnvyMethods.size() >= 3) return ThreatLevel.HIGH;
        if(FeatureEnvyMethods.size() == 2) return ThreatLevel.MEDIUM;
        if(FeatureEnvyMethods.size() == 1) return ThreatLevel.LOW;

        //else return no threat
        return ThreatLevel.NONE;
    }


    public ThreatLevel checkExcessiveCoupling() {
        //DO CHECKS
        return ThreatLevel.NONE;
    }

}
