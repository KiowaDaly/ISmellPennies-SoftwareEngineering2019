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
import utility_classes.ThreatLevel;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class DataHiding {
    private int numberOfFields;
    private int numberOfPrivates;
    String name;
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

    private boolean isFieldCommented(FieldDeclaration field){
        boolean isCommentedField = false;
        String[] fieldToString = field.toString().split("");
        if(fieldToString[0].equalsIgnoreCase("/"))
            isCommentedField=true;
        return isCommentedField;
    }
    private String getFieldName(String[] expression){
        //Return the field name given in an array (from fieldDeclaration).
        String fieldName = "";

        int expressionLength = expression.length;
        //check if the array 'expression' contains the char '='.
        boolean isAssigning = Arrays.asList(expression).contains("=");
        //if it does contain '=' we take it to just be of the form (modifier type fieldName;) with no assign.
        if(expressionLength>0 && !isAssigning){
            //replace the fieldNames semicolon with nothing. else it breaks the Statement maker.
            fieldName = expression[expressionLength-1].replace(";","");
        }
        else if(expressionLength>0 && isAssigning){

            int equalIndex = 0;
            //iterate through the expression until we meet the character '='
            while(equalIndex<expression.length && !expression[equalIndex].equalsIgnoreCase("="))
                equalIndex++;
            //return the previous index to the current index which has '='
            //this operation gives us the field name.
            fieldName = expression[equalIndex-1];
        }
        return fieldName;
    }

    private boolean isReturningDeclaredObject(MethodDeclaration method){
        boolean invalidReturn = false;
        BlockStmt blockStmt = method.getBody().get();
        //iterate through the statements in the method.
        for(Statement stmt: blockStmt.getStatements()){

            //only check if a statement is return statement.
            if(stmt.isReturnStmt()){
                //go through all the fields and check if they equal it
                for(FieldDeclaration f: clase.getFields()){
                    //check if the field f is commented out field. (if so we ignore).
                    if(isFieldCommented(f))
                        continue;
                    //check if the field is primitive
                    boolean isPrimitve = f.getElementType().isPrimitiveType();
                    boolean isPrivateField = f.isPublic();
                    //get the string name of the field (private static int age; returns "age")
                    //And (private static int age = 24; returns "age"
                    String fieldName = getFieldName(f.toString().split(" "));
                    //check if that method is being returned.
                    Statement s = StaticJavaParser.parseStatement("return "+fieldName+";");
                    //check if the return is equal to the fieldDeclaration and is it notPrimitive
                    if(s.equals(stmt) && !isPrimitve && isPrivateField) {
                        invalidReturn = true;
                    }
                }
            }
        }
        return invalidReturn;
    }

    private boolean isReturningMutable(){
        /*
        This method goes through the full methodList in the given class 'clase'
         */
        boolean returnsMutableObject = false;
        for(MethodDeclaration method: clase.getMethods()){
            // check if the methood is public and accessible from outside along with it returning and object
            //if return type is object and method is public
            if(!method.getType().isPrimitiveType() && method.isPublic()){
                //check if there exists a private object being returned as is!
                /*
                    A bad practice would be returning a privite object in the class directly,
                    without making some copy that doesn't reference it and returning that copy instead.
                 */
                returnsMutableObject = isReturningDeclaredObject(method);
                if(returnsMutableObject) {
                    return true;
                }
            }
        }
        return returnsMutableObject;
    }


    private int numberOfPrivateFields(){
        return numberOfPrivates;
    }

    private boolean isReturnObjects(){
        return isReturningMutable();
    }

    public ThreatLevel DataHidingComplexity(){
        ThreatLevel complexityLevel = ThreatLevel.NONE;

        double val = (double) numberOfPrivates/numberOfFields;
        if(val<0.1){
            complexityLevel = ThreatLevel.HIGH;
        }
        else if(val<0.6){
            complexityLevel = ThreatLevel.MEDIUM;
        }
        else if(val<0.8){
            complexityLevel = ThreatLevel.LOW;
        }



        return complexityLevel;

    }
}