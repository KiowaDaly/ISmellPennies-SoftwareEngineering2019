package Lazies_Freeloader_walkingdead;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
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
    public boolean isDeadCode(ClassOrInterfaceDeclaration c1){
        //dead methods
        //if mthod calls is 0, print method name and its count and make it a threat
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        c1.isCallableDeclaration();
        c1.getMethods();

        return false;
    }

        //to do
    public boolean isLazyCode(ClassOrInterfaceDeclaration c1){
        return false;
    }

    public boolean isDuplicateCode(ClassOrInterfaceDeclaration c1){
        return false;
    }


}
