package ObjectOrientedAbusers;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import javassist.compiler.ast.FieldDecl;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class DataHiding {
    private int numberOfFields;
    private int numberOfPrivates;

    private ClassOrInterfaceDeclaration clase;

    public DataHiding(ClassOrInterfaceDeclaration clase){
        this.clase = clase;
        numberOfFields = clase.getFields().size();;
        numberOfPrivates = getGlobalPrivates();
    }
    private int getGlobalPrivates(){
        //returns the number of private fields found.
       int numPrivates = 0;
       List<FieldDeclaration> fieldList =  clase.getFields();
       for(FieldDeclaration f: fieldList){
           //check if a field is private
           if(f.isPrivate()){
               //increment number of found
               numPrivates++;
           }
       }
        return numPrivates;
    }
    private boolean isReturningDeclaredObject(MethodDeclaration method){
        boolean invalidReturn = false;
        BlockStmt blockStmt = method.getBody().get();
        for(Statement stmt: blockStmt.getStatements()){
            if(stmt.isReturnStmt()){
                //go through all the fields and check if they equal it
                for(FieldDeclaration f: clase.getFields()){
                    //create a temporary return statement and see if they are equal.
                    int length = f.toString().split(" ").length;
                    String[] expression = f.toString().split(" ");
                    boolean isAssigning = Arrays.asList(expression).contains("=");
                    //if we have an expression and it doesn't contain equal (assign)
                    if(length>0 && !isAssigning){
                        //expression 'private static int age;' becomes 'age'
                        String fieldName= f.toString().split(" ")[length-1];
                        System.out.println("Field Name: "+fieldName);
                        Statement s = StaticJavaParser.parseStatement("return "+fieldName);
                        if(s.equals(stmt))
                            invalidReturn =  true;
                    }
                    else if(length>0 && isAssigning){
                        int i=0;
                        while(i<expression.length && !expression[i].equals('='))
                            i++;
                        --i;
                        String fieldName= expression[--i];
                        System.out.println("Field Name: "+fieldName);
                        Statement s = StaticJavaParser.parseStatement("return "+fieldName);
                        if(s.equals(stmt))
                            invalidReturn =  true;
                    }
                }
            }
        }
        return invalidReturn;
    }

    private boolean isReturningMutable(){
        /*
        if there exists a method that is returning an object (not primitve return type)
        we know that the object can

        check if there is a method that is public and returns an object (mutable).

         */
        boolean returnsMutableObject = false;
        for(MethodDeclaration method: clase.getMethods()){
            // check if the methood is public and accessible from outside along with it returning and object
            //if return type is object and method is public
            if(!method.getType().isPrimitiveType() && method.isPublic()){
                //check for static later.

                //Check if returning one of the global fields (private). (isReturningGlobalField)
                returnsMutableObject = isReturningDeclaredObject(method);
                if(returnsMutableObject)
                    return true;
            }
        }
        return returnsMutableObject;
    }


    private int numberOfPrivateFields(){
        return numberOfPrivates;
    }

    public boolean isReturnObjects(){
        return isReturningMutable();
    }


    public boolean isSufficientPrivatisation(){
        return (numberOfPrivates == numberOfFields);
    }
}
