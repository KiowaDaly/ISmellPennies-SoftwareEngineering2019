package ObjectOrientedAbusers;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import utility_classes.ThreatLevel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class TemporaryFields {

    ///private ThreatLevel complexity;



    //hash that holds field and number of times it has been used.
    private HashMap<FieldDeclaration, Integer> fieldList;
    private ClassOrInterfaceDeclaration clase;


    public TemporaryFields(ClassOrInterfaceDeclaration c){
        clase = c;
        setUpFieldList();
    }

    private void setUpFieldList(){
        List<FieldDeclaration> fields = clase.getFields();
        for(FieldDeclaration field: fields){
            fieldList.put(field, 0);
        }
    }


    private boolean isFieldUsed(FieldDeclaration field, MethodDeclaration method){
        BlockStmt statementsInMethod= method.getBody().get();
        for(Statement statement: statementsInMethod.getStatements()){
            if(statementContainsField(field, statement)){
                return true;
            }
        }
        return false;
    }

    private boolean statementContainsField(FieldDeclaration field, Statement statement){
        for(Node n: statement.getChildNodes()){
            System.out.println(n.toString());
        }
        return false;
    }


    public void beginAnalysis(){
        List<MethodDeclaration> methodList = clase.getMethods();
        for(FieldDeclaration field: fieldList.keySet()){
            for(MethodDeclaration method: methodList){
                boolean res = isFieldUsed(field, method);
            }
        }
    }
}
