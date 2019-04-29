package ObjectOrientedAbusers;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import utility_classes.ThreatLevel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemporaryFields {

    ///private ThreatLevel complexity;



    //hash that holds field and number of times it has been used.
    private HashMap<VariableDeclarator, Integer> fieldList = new HashMap<>();
    private ClassOrInterfaceDeclaration clase;


    public TemporaryFields(ClassOrInterfaceDeclaration c){
        clase = c;
        setUpFieldList();
    }

    private void setUpFieldList(){
        List<FieldDeclaration> fields = clase.getFields();
        if(fields.size() > 0){
            for(FieldDeclaration field: fields){
                for(VariableDeclarator vd: field.getVariables()) {
                    System.out.println("PUTTING: " + vd.getName());
                    fieldList.put(vd, 0);
                }
            }
        }

    }


    private boolean isFieldUsed(VariableDeclarator field, MethodDeclaration method){
        String methodBody = method.getBody().get().toString();
        System.out.println("Field Name: "+field.getName()+" MethodName: "+method.getName());

        String fieldName = ".*"+field.getName().toString()+".*";
        Pattern checkRegex = Pattern.compile(fieldName);
        Matcher regexMatcher = checkRegex.matcher(methodBody);
        while(regexMatcher.find()){
            if(regexMatcher.group().length() != 0){
                return true;
            }
        }
        return false;
    }

    private boolean statementContainsField(VariableDeclarator field, Statement statement){
        //System.out.println(statement.toString());
        return false;
    }


    public void beginAnalysis(){
        List<MethodDeclaration> methodList = clase.getMethods();
        for(VariableDeclarator field: fieldList.keySet()){
            for(MethodDeclaration method: methodList){
                boolean res = isFieldUsed(field, method);
                if(res){
                    System.out.println("OLD: "+fieldList.get(field));
                    int val = fieldList.get(field);
                    fieldList.replace(field, val+1);
                    System.out.println("NEW: "+fieldList.get(field));
                }
            }
        }
    }


    public ThreatLevel TemporaryFieldComplexity(){
        ThreatLevel complexityLevel = ThreatLevel.NONE;
        int totalFields = fieldList.size();
        double top = 0;
        for(VariableDeclarator fd: fieldList.keySet()){
            top += (double) (fieldList.get(fd))/clase.getMethods().size();
        }
        double val = (double)(top/totalFields);

        if(val<0.0){
            complexityLevel = ThreatLevel.NONE;
        }
        else if(val<0.3){
            complexityLevel = ThreatLevel.LOW;
        }
        else if(val<0.60){
            complexityLevel = ThreatLevel.MEDIUM;
        }
        else{
            complexityLevel = ThreatLevel.HIGH;
        }

        return complexityLevel;
    }

}
