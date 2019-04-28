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


    public ThreatLevel getDuplicationLevel(ClassOrInterfaceDeclaration cl){
        int level = 0;
        double similarity = 0;
        for(int i = 0;i<cl.getMethods().size()-1;i++){
            for(int j=0;j<cl.getMethods().size()-1;j++){
                if(!cl.getMethods().get(i).getNameAsString().equals(cl.getMethods().get(j).getNameAsString())){
                    similarity = CalculateSimilarity(cl.getMethods().get(i), cl.getMethods().get(j));
                }

                if(similarity>0.7 && level<3) level++;
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
    public ThreatLevel LazyCodeCheck(ClassOrInterfaceDeclaration cl){
        //need to find class that doesnt do much,
        //may contain only data and less than 2 methods calls
        //examples would be being data only classes.
        //if it uses too much external calls, more than it self
        //methods are lacking
        List<SimpleName> declaredMethods = new ArrayList<>();
        List<MethodCallExpr> allMethodCalls = new ArrayList<>();

        int lazyStrike = 0;

        //all methods
        allMethodCalls.addAll(cl.findAll(MethodCallExpr.class));

        //declared methods
        for (MethodDeclaration md : cl.getMethods()){
            declaredMethods.add(md.getName());
        }

        //if there are less than 3 declared methods.
        if(declaredMethods.size() <= 3){
            lazyStrike = lazyStrike + 2;   //2 strikes,
        }

        if(isDataOnlyClass(cl)){            //if it is a data class
            lazyStrike ++;   //1 strikes,
        }

/*
        System.out.println("Lazy All Methods " +allMethodCalls );
        System.out.println("Lazy Declared methods " +declaredMethods );
        System.out.println("Lazy External methods" +externalMethodCalls);
        System.out.println("size " + externalMethodCalls.size());
        System.out.println(allMethodCalls);
*/
        if (lazyStrike == 3) return ThreatLevel.HIGH;
        if (lazyStrike == 2) return ThreatLevel.MEDIUM;
        if (lazyStrike == 1) return ThreatLevel.LOW;

        //else return no threat
        return ThreatLevel.NONE;
    }

    //check howmany times a declared method is used
    public ThreatLevel SpeculativeGeneralityChecker(ClassOrInterfaceDeclaration cl){
        //test cases, if code is not called, may recheck
        //dead METHODS
        //if mthod call is 0, print method name and its count and make it a threat
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        List<MethodCallExpr> externalCalls = new ArrayList<>();
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        List<SimpleName> unusedMethods = new ArrayList<>();

        int uncalled = 0;
            //adds all the called methods in the list
            methodCalls.addAll(cl.findAll(MethodCallExpr.class));

        //    System.out.println("DEADDDDD Method declarations: " + methodCalls);

        for (MethodDeclaration md : cl.getMethods()) {
            //if it the method is not contained in the list, and is also not called 'main'
            if(!methodCalls.toString().contains(md.getNameAsString()) && !md.getNameAsString().equals("main")){
                unusedMethods.add(md.getName());
                uncalled++;
            }

        }
/*        //testing
        System.out.println("uncalled" + uncalled);
        System.out.println("methodCalls: " + methodCalls + "'");
        System.out.println("unused: " + unusedMethods+ "'");
*/
        if (uncalled >= 3) return ThreatLevel.HIGH;
        if (uncalled == 2) return ThreatLevel.MEDIUM;
        if (uncalled == 1) return ThreatLevel.LOW;
        if (methodCalls.isEmpty()) return ThreatLevel.HIGH;
        //else return no threat
        return ThreatLevel.NONE;
    }


    //TO DO
    public ThreatLevel deadCodeChecker(ClassOrInterfaceDeclaration cl){
        //dead code that cannot be reached
        //find un used variables or
        List<FieldAccessExpr> allFields = new ArrayList<>();

        for (MethodDeclaration md : cl.getMethods()) {

        }

        //test
       // System.out.println("allFields: " + allFields);
        //else return no threat
        return ThreatLevel.NONE;
    }

    //TO DO
    public ThreatLevel overallWalkingDead(ClassOrInterfaceDeclaration cl){
        ThreatLevel dataOnlyThreat;

        int counter = 0;
        if(isDataOnlyClass(cl)){
            dataOnlyThreat = ThreatLevel.HIGH;
        }else {
            dataOnlyThreat = ThreatLevel.NONE;
        }


        counter += SpeculativeGeneralityChecker(cl).ordinal();
        counter += dataOnlyThreat.ordinal();
        counter += LazyCodeCheck(cl).ordinal();
        counter += deadCodeChecker(cl).ordinal();
        counter += getDuplicationLevel(cl).ordinal();

        System.out.println("allFields: " + counter);

        return ThreatLevel.values()[(int) counter/5];
    }

}

