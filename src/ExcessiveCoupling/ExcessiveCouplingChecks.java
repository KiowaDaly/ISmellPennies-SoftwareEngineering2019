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

public class ExcessiveCouplingChecks {
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
    //find ratio between normal method declarations and method calls from other classes
    public Integer getNumMethodCalls(ClassOrInterfaceDeclaration ci) {
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        List<MethodCallExpr> externalCalls = new ArrayList<>();
        for (MethodDeclaration m : ci.getMethods()) {
            methodDeclarations.add(m);
            methodCalls.addAll(m.findAll(MethodCallExpr.class));
        }


        for (int i = 0; i < methodCalls.size(); i++) {
            for (int j = 0; j < methodDeclarations.size(); j++) {
                if (!methodCalls.get(i).getName().equals(methodDeclarations.get(j).getName())) {
                    externalCalls.add(methodCalls.get(i));
                }
            }
        }
        return externalCalls.size() / methodDeclarations.size();
    }

    public Integer getNumVariableCalls(ClassOrInterfaceDeclaration ci) {
        List<FieldAccessExpr> variableCalls = new ArrayList<>();
        List<FieldDeclaration> fields = new ArrayList<>();
        for(FieldDeclaration fd: ci.getFields()){
            fields.add(fd);
        }


        variableCalls.addAll(ci.findAll(FieldAccessExpr.class));
        for (int i = 0; i < variableCalls.size(); i++) {
            if (variableCalls.get(i).toString().contains("this.")) {

                variableCalls.remove(i);
            }
        }
        if(fields.size() == 0) return 0;

        return variableCalls.size()/fields.size();
    }

    public ThreatLevel isMiddleMan(ClassOrInterfaceDeclaration ci) {
        List<MethodDeclaration> middleManMethods = new ArrayList<>();
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        for (MethodDeclaration md : ci.getMethods()) {
            if(md.getBody().isPresent()) {
                 methodCallExprs.addAll(md.getBody().get().findAll(MethodCallExpr.class));
            }
            int numberLines = md.getEnd().get().line - md.getBegin().get().line - 1;
            if (methodCallExprs.size() == numberLines) middleManMethods.add(md);
        }

        if((double) middleManMethods.size() / (double) ci.getMethods().size() > 0.5) return ThreatLevel.HIGH;



        return ThreatLevel.NONE;
    }

    //CHECK ALL METHOD DECLARATIONS IN A CLASS, CHECK NUMBER OF METHOD EXPRESSIONS IN EACH METHOD...
    //...THEN CHECK THE NUMBER OF LINES IN EACH METHOD
    //... PUT ONE OVER THE OTHER IF THAT IS GREATER THAN 50%, THE METHOD IS GUILTY OF FEATURE ENVY
    //CLASS OBTAINS HIGH MEDIUM OR LOW STATUS FOR HOW MANY METHODS HAVE FEATURE ENVY
    public ThreatLevel isFeatureEnvy(ClassOrInterfaceDeclaration ci) {
        List<MethodDeclaration> FeatureEnvyMethods = new ArrayList<>();
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        for (MethodDeclaration md : ci.getMethods()) {
            if(md.getBody().isPresent()){
                methodCallExprs.addAll(md.getBody().get().findAll(MethodCallExpr.class));
            }

            int numberLines = md.getEnd().get().line - md.getBegin().get().line - 1;
            if ((double) methodCallExprs.size() / ((double) numberLines - (double) methodCallExprs.size()) > 0.5)
                FeatureEnvyMethods.add(md);
        }
        if (FeatureEnvyMethods.size() >= 3) return ThreatLevel.HIGH;
        if (FeatureEnvyMethods.size() == 2) return ThreatLevel.MEDIUM;
        if (FeatureEnvyMethods.size() == 1) return ThreatLevel.LOW;

        //else return no threat
        return ThreatLevel.NONE;
    }
    public ThreatLevel isInappropriateIntimacy(ClassOrInterfaceDeclaration ci){
        int methodCount = getNumMethodCalls(ci);
        int variableCount = getNumVariableCalls(ci);
        int threatCount = 0;
        //Ratios:   MEDIUM == 1        LOW < 1    HIGH > 1 can go up to three
        if(methodCount > 1 && variableCount > 1) return ThreatLevel.HIGH;
        if(((variableCount == 1) && (methodCount == 1)) || ((variableCount > 1 || methodCount > 1))){
            return ThreatLevel.MEDIUM;
        }
        if(variableCount < 1 && methodCount < 1) return ThreatLevel.LOW;
        return ThreatLevel.NONE;
    }

    //EACH CLASS WILL HAVE A FRACTION OF HOW MUCH EXCESSIVE COUPLING IT HAS - I TURN THIS FRACTION INTO A THREAT LEVEL
    public ThreatLevel checkExcessiveCoupling(ClassOrInterfaceDeclaration ci) {
        //FOR NOW THIS IS JUST A SUMMATION OF ALL THREATLEVEL POSSIBILITIES FROM FEATURE ENVY AND MIDDLEMAN = 6
        double totalThreatCount = 10;

        double featureEnvyThreatNumber = isFeatureEnvy(ci).ordinal();
        double middleManThreatNumber = isMiddleMan(ci).ordinal();
        double inappropriateIntimacy = isInappropriateIntimacy(ci).ordinal();

        //ADDTION OF ALL THREAT NUMBERS
        double addedThreatNumbers = featureEnvyThreatNumber + middleManThreatNumber + inappropriateIntimacy;

        //FINAL FRACTION
        //double threatLevelFraction = addedThreatNumbers / totalThreatCount;
        double threatLevelFraction = addedThreatNumbers / totalThreatCount;

        //USING FINAL FRACTION TO DECIDE THREAT LEVEL
        if(threatLevelFraction > 0.66) return ThreatLevel.HIGH;
        if(threatLevelFraction > 0.33 && threatLevelFraction <= 0.66) return ThreatLevel.MEDIUM;
        if(threatLevelFraction <= 0.33 && threatLevelFraction > 0.00) return ThreatLevel.LOW;
        System.out.println(threatLevelFraction);
        System.out.println(isMiddleMan(ci).ordinal());
        return ThreatLevel.NONE;
    }
}