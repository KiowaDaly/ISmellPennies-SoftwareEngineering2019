package ObjectOrientedAbusers;

import ObjectOrientedAbusers.RefusedBequestHelpers.Beta;
import ObjectOrientedAbusers.RefusedBequestHelpers.BetaFactory;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import utility_classes.CompilationUnitVisitor;
import utility_classes.ThreatLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RefusedBequest{
    private HashMap<String, Beta> betaMapping;
    private List<ClassOrInterfaceDeclaration> classes; //holds all the classes.



    public RefusedBequest(){
        betaMapping = new HashMap<>();
        classes = new ArrayList<ClassOrInterfaceDeclaration>();
    }

    public void createClasses(List<CompilationUnit> units){
        for(CompilationUnit cu: units){
            List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
            CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
            compunitvisitor.visit(cu, classes);
            for(ClassOrInterfaceDeclaration cl: classes){
                addClass(cl);

            }
        }
        setUpBetas();
        beginAnalysis();


    }

    public ThreatLevel refuseBequestLevels(ClassOrInterfaceDeclaration cl){
        ThreatLevel tl = ThreatLevel.NONE;
        for(Beta b: betaMapping.values()){
            if(b.getBeta().getName().toString().equals(cl.getName().toString())){
                double val = b.numberOfAlphasOverBetas();
                if(val<0.3){
                    tl = ThreatLevel.LOW;
                }
                else if(val<0.6){
                    tl = ThreatLevel.MEDIUM;
                }
                else{
                    tl = ThreatLevel.HIGH;
                }
            }
        }
        return tl;
    }



    private void addClass(ClassOrInterfaceDeclaration clase){classes.add(clase);}

    private void setUpBetas(){
        for(ClassOrInterfaceDeclaration cl: classes){
            BetaFactory bf = new BetaFactory();
            if(bf.isBeta(cl)){
                if(bf.isBetaMultiple(cl)){
                    //Do something here.
                    for(ClassOrInterfaceType clType: bf.getMultipleBeta(cl)){
                        //get the ClassOrInterfaceDeclartion from a given ClassOrInterfaceType.
                        ClassOrInterfaceDeclaration clFromType = getClass(clType.getName().toString());
                        betaProductions(bf, clFromType);
                    }
                }
                else{
                    betaProductions(bf, cl);
                }
            }
        }
    }

    private void betaProductions(BetaFactory bf, ClassOrInterfaceDeclaration cl){
        String getCurrentAlpha = bf.getAlpha(cl);
        ClassOrInterfaceDeclaration alpha =getClass(getCurrentAlpha);
        Beta b = bf.setUpBeta(cl, alpha);
        addBeta(b);
    }

    public ClassOrInterfaceDeclaration getClass(String claseName){
        for(ClassOrInterfaceDeclaration cl: classes){
            if(cl.getName().toString().equals(claseName)){
                return cl;
            }
        }
        //Not possible to get here. Only written for Java boiler plates.
        return null;
    }

    /*
            Next step is to iterate through the list of classes, see if we use one of our betas
            if we do check if the method being called check if it is Alpha method or beta.
                    i.e.
                        if(isMethodCalledAlpha())
                            invocAlpha++
                        else
                            invocBeta++;

     */

    private void BetaAlphaClientCalls(Beta beta, ClassOrInterfaceDeclaration host){
        //check for global.
        if(host.isInterface())
            return;
        //getting all
        for(MethodDeclaration md: host.getMethods()){
            List<MethodCallExpr> amd = new ArrayList<>();
            amd.addAll(md.findAll(MethodCallExpr.class));
            for(MethodCallExpr mce: amd){
                //just check if the method being called is one in beta.

//                System.out.println("\t\t"+mce.toString()+" - "+mce.getName()+" = "+mce.getScope().toString()+" || "+mce.getArguments());
//                Optional<Expression> variableName = mce.getScope();
                if(isMethodInBeta(beta, mce.getName().toString())){
                    //check if message is declared in beta or alpha.
                    declarationPoint(beta, mce);
//                    System.out.println("Expression: "+variableName.get());
//                    findExpressionType(variableName.get().toString(), md);

                }

            }
        }
    }
//    private void findExpressionType(String varName, MethodDeclaration md){
//        List<FieldAccessExpr> variableCalls = new ArrayList<>();
//
//        List<FieldDeclaration> fields = new ArrayList<>();
//
//        variableCalls.addAll(md.findAll(FieldAccessExpr.class));
//
//        for(FieldAccessExpr fae: variableCalls){
//            System.out.println("FAE : "+fae+" = "+varName);
//        }
//    }
    private void declarationPoint(Beta beta, MethodCallExpr mce){
        //chcek if in alpha.
        boolean foundAlpha = false;
        for(MethodDeclaration md: beta.getAlpha().getMethods()){
            if(md.getName().toString().equals(mce.getName().toString())){
                beta.invocatesAlpha();
                foundAlpha = true;
            }
        }
        if(!foundAlpha)
            beta.invocatesBeta();
    }


    private boolean isMethodInBeta(Beta beta, String methodName){
        for(MethodDeclaration md: beta.getBeta().getMethods()){
            if(md.getName().toString().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private void beginAnalysis(){
        for(Beta beta: betaMapping.values()){
            for(ClassOrInterfaceDeclaration cl: classes){
                boolean isBetaEqualClass = cl.getName().toString().equals(beta.getBeta().getName().toString());
                boolean isClassAlphaOfBeta = cl.getName().toString().equals(beta.getAlpha().getName().toString());
                if(!isBetaEqualClass && !isClassAlphaOfBeta){
                    BetaAlphaClientCalls(beta, cl);
                }
            }
        }
    }




    private void addBeta(Beta beta){
        betaMapping.put(beta.getBeta().getName().asString(), beta);
    }
}
