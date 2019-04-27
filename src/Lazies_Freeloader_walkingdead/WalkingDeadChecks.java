package Lazies_Freeloader_walkingdead;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import com.github.javaparser.ast.expr.MethodCallExpr;

import java.text.DecimalFormat;
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



}

