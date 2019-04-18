package ExcessiveCoupling;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.lang.reflect.Method;
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
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        for(MethodDeclaration m:ci.getMethods()){
            methodDeclarations.add(m);
            methodCalls.addAll(m.findAll(MethodCallExpr.class));
        }
        for(int i=0; i < methodCalls.size(); i++){
            for(int j=0;j<methodDeclarations.size(); j++){
                if(methodCalls.get(i).getName().equals(methodDeclarations.get(j).getName())){
                    methodCalls.remove(i);
                }
            }
        }
        return methodCalls.size();
    }




}
