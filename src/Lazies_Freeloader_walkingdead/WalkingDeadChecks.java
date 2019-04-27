package Lazies_Freeloader_walkingdead;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.ArrayList;
import java.util.List;

import utility_classes.ThreatLevel;


public class WalkingDeadChecks {

    public boolean isDataOnlyClass(ClassOrInterfaceDeclaration cl) {
        List<MethodDeclaration> nonDataMethods = new ArrayList<>();
        for (FieldDeclaration f : cl.getFields()) {
            for (MethodDeclaration md : cl.getMethods()) {
                for (VariableDeclarator v : f.getVariables()) {
                    if ((md.getNameAsString().toLowerCase().contains(("get"))
                            || (md.getNameAsString().toLowerCase().contains("set")))) {
                        continue;
                    } else nonDataMethods.add(md);
                }
            }
        }

        return nonDataMethods.size() == 0;

    }

    //check how many times its called in the javaparser
    public ThreatLevel isDeadCode(ClassOrInterfaceDeclaration cl){
        //dead METHODS
        //if mthod call is 0, print method name and its count and make it a threat
        List<MethodCallExpr> methodCalls = new ArrayList<>();



        int uncalled = 0;
        for (MethodDeclaration md : cl.getMethods()) {
            //adds all the called methods in the list
            methodCalls.addAll(md.findAll(MethodCallExpr.class));
        }

        for (MethodDeclaration md : cl.getMethods()) {
            //if it the method is not contained in the list, and is also not called 'main'
            if(!methodCalls.toString().contains(md.getNameAsString()) && !md.getNameAsString().equals("main")){
                //  System.out.println("it contains dead code. that is '" + md.getNameAsString() + "'");
                uncalled++;
            }
        }

        if (uncalled >= 3) return ThreatLevel.HIGH;
        if (uncalled == 2) return ThreatLevel.MEDIUM;
        if (uncalled == 1) return ThreatLevel.LOW;
        if (methodCalls.isEmpty()) return ThreatLevel.HIGH;
        //else return no threat
        return ThreatLevel.NONE;
    }
    //to do
    public boolean isLazyCode(ClassOrInterfaceDeclaration c1){
        return false;
    }

    public boolean isDuplicateCode(ClassOrInterfaceDeclaration c1){

        return false;
    }


}

