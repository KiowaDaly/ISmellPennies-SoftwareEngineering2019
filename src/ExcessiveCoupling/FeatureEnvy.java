package ExcessiveCoupling;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.symbolsolver.javaparsermodel.declarators.VariableSymbolDeclarator;
import com.google.errorprone.annotations.Var;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Integer getNumVsriableCalls(ClassOrInterfaceDeclaration ci){
         List<FieldAccessExpr> variableCalls = new ArrayList<>();
         variableCalls.addAll(ci.findAll(FieldAccessExpr.class));
        for(int i = 0;i<variableCalls.size();i++){

           if(variableCalls.get(i).toString().contains("this.")){
                //variableCalls.remove(i);
              variableCalls.remove(i);
            }
        }


         return variableCalls.size();
    }




}
