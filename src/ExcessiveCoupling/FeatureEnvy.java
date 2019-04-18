package ExcessiveCoupling;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.ArrayList;
import java.util.List;

public class FeatureEnvy implements ExcessiveCoupling<Integer, ClassOrInterfaceDeclaration> {

    public Integer getNumClassInstances(ClassOrInterfaceDeclaration ci){
        List<VariableDeclarator> classInstances = new ArrayList<>();
        for(FieldDeclaration f:ci.getFields()){
            for(VariableDeclarator vl:f.getVariables()){
                   if(vl.getType().isClassOrInterfaceType()){
                        classInstances.add(vl);
                   }
            }
        }
        return classInstances.size();
    }
    public Integer getNumMethodCalls(ClassOrInterfaceDeclaration ci){
        List<MethodCallExpr> methodCalls= new ArrayList<>();
        for(MethodDeclaration m:ci.getMethods()){
            methodCalls.addAll(m.findAll(MethodCallExpr.class));
        }
        return methodCalls.size();
    }




}
