package Lazies_Freeloader_walkingdead;
import com.github.javaparser.ast.body.*;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import utility_classes.ThreatLevel;

//delete some comments later

public class WalkingDeadChecks {

    public boolean isDataOnlyClass(ClassOrInterfaceDeclaration cl) {

        List<MethodDeclaration> nonDataMethods = new ArrayList<>();

        for (FieldDeclaration f : cl.getFields()) {
            for (MethodDeclaration md : cl.getMethods()) {
                for (VariableDeclarator v : f.getVariables()) {

                    String retType = md.findAll(ReturnStmt.class).toString();
                    String fieldName = v.getName().toString();

                        //getter
                        if(retType.contains(fieldName)){
                            continue;
                        }

                        //check method has no call
                        if(md.findAll(MethodCallExpr.class).isEmpty()){ continue;}

                        //setters
                        for (Parameter p : md.getParameters()){
                            String setterBody = md.getBody().toString(); //body of the method
                            String setterMethod = "this."+fieldName + " = " + p.getName(); //this.fieldname = parameter

                            //continue if it is a setter method
                            if(setterBody.contains(setterMethod)){continue;}
                            continue;

                        }

                        //check if method has call, but is returning a statement.
                        if(!md.findAll(MethodCallExpr.class).isEmpty()){
                            if(!md.findAll(ReturnStmt.class).isEmpty()){
                                continue;
                            } else
                                nonDataMethods.add(md);
                        }
                     else
                         //if its a method that doesnt return a data
                        nonDataMethods.add(md);
                        //to see which methods are not data
                       // System.out.println("NON DATA Method name: " + md.getName());

                }
            }
        }

        return nonDataMethods.isEmpty();
    }

    //check how many times its called in the javaparser
    public ThreatLevel isDeadCode(ClassOrInterfaceDeclaration cl){
        //dead METHODS
        //if mthod call is 0, print method name and its count and make it a threat
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        List<MethodCallExpr> externalCalls = new ArrayList<>();
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();

        int uncalled = 0;
        for (MethodDeclaration md : cl.getMethods()) {
            //adds all the called methods in the list
            methodDeclarations.add(md);
            methodCalls.addAll(md.findAll(MethodCallExpr.class));
        }


    //    System.out.println("DEADDDDD Method declarations: " + methodCalls);

        for (MethodDeclaration md : cl.getMethods()) {
            //if it the method is not contained in the list, and is also not called 'main'
            if(!methodCalls.toString().contains(md.getNameAsString()) && !md.getNameAsString().equals("main")){
                 System.out.println("it contains dead code. that is '" + md.getNameAsString() + "'");
                uncalled++;
            }

        }
        //testing
        System.out.println("uncalled" + uncalled);

        if (uncalled >= 3) return ThreatLevel.HIGH;
        if (uncalled == 2) return ThreatLevel.MEDIUM;
        if (uncalled == 1) return ThreatLevel.LOW;
        if (methodCalls.isEmpty()) return ThreatLevel.HIGH;
        //else return no threat
        return ThreatLevel.NONE;
    }

    public ThreatLevel getDuplicationLevel(ClassOrInterfaceDeclaration cl){
        int level = 0;
        double similarity = 0;
        for(int i = 0;i<cl.getMethods().size()-1;i++){
            for(int j=0;j<cl.getMethods().size()-1;j++){
                if(!cl.getMethods().get(i).getNameAsString().equals(cl.getMethods().get(j).getNameAsString())){
                    similarity = CalculateSimilarity(cl.getMethods().get(i), cl.getMethods().get(j));
                }

                if(similarity>0.7) level++;
            }
        }
        return ThreatLevel.values()[level];
    }

    private double CalculateSimilarity(MethodDeclaration method1,MethodDeclaration method2){
        DecimalFormat df = new DecimalFormat(("#.##"));
        String method1Body = method1.getBody().toString();
        String method2Body = method2.getBody().toString();
        String longer = method1Body, shorter = method2Body;
        if (method1Body.length() < method2Body.length()) { // longer should always have greater length
            longer = method2Body; shorter = method1Body
            ;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
       // out.println(method1.getNameAsString()+" has a similarity level of: "+df.format(((longerLength - editDistance(longer, shorter)) / (double) longerLength)*100)+"% with "+method2.getNameAsString());
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }


    //TO DO
    public boolean isLazyCode(ClassOrInterfaceDeclaration cl){
        //need to find class that doesnt do much,
        //may contain only data and less than 2 methods calls
        //examples would be being data only classes.
        //if it uses too much external calls, more than it self
        //methods are lacking
        //COUNT EMPTY METHODS
        List<SimpleName> declaredMethods = new ArrayList<>();
        List<MethodCallExpr> allMethodCalls = new ArrayList<>();
        List<SimpleName> externalMethodCalls = new ArrayList<>();

        int emptyMethods = 0;
        for (MethodDeclaration md : cl.getMethods()){
            declaredMethods.add(md.getName());

            if(!md.findAll(ReturnStmt.class).isEmpty()){
                continue;
            } else
            emptyMethods++;
        }
        System.out.println("Lazy Declared calls calls" +declaredMethods + " counter: " +emptyMethods);
//if(md.findAll(MethodCallExpr.class).isEmpty()){ continue;}
            allMethodCalls.addAll(cl.findAll(MethodCallExpr.class));
            for (MethodCallExpr mcall : allMethodCalls) {

            if (!declaredMethods.contains(mcall.getName()) && !externalMethodCalls.contains(mcall.getName()))
                externalMethodCalls.add(mcall.getName()); // method is not declared within the class and counted once
            }

        for (MethodDeclaration md : cl.getMethods()) {
            //if it the method is not contained in the list, and is also not called 'main'
            if(!allMethodCalls.toString().contains(md.getNameAsString())){
                 System.out.println("it contains dead code. that is '" + md.getNameAsString() + "'");
                //uncalled++;
            }

        }


        System.out.println("Lazy External calls" +externalMethodCalls);

        System.out.println(externalMethodCalls.size());

        externalMethodCalls.size();

        return false;
    }


    //TO DO
    public boolean SpeculativeGeneralityChecker(ClassOrInterfaceDeclaration cl){
        //test cases, if code is not called, may recheck
        return false;
    }


}

