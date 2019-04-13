package ObjectOrientedAbusers;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import utility_classes.ThreatLevel;

import java.util.List;
import java.util.NoSuchElementException;

public class SwitchChecker {

    private ThreatLevel complexity;
    private int numberOfSwitches;
    private int numberOfComplexSwitches;
    private List<Integer> line; //line.get(0) and line.get(1) are the start and end of the switch line.


    public SwitchChecker(){
        numberOfSwitches = 0;
        numberOfComplexSwitches = 0;
        complexity = ThreatLevel.NONE;
    }

    private boolean containsSwitch(MethodDeclaration md){ //remove static
        int switchStatements = 0;
        try{
            BlockStmt code = md.getBody().get();
            //codeBlocks holds a list of statements to iterate through.
            NodeList<Statement> codeBlocks=code.getStatements();
            //iterate through every statement in codeBlocks
            for(Statement stmt: codeBlocks){
                if(stmt.isSwitchStmt())
                    return true;
            }
        }
        catch(NoSuchElementException e){

        }
        return false;
    }

    private Statement getSwitchStatement(MethodDeclaration md){
        BlockStmt code = md.getBody().get();
        //codeBlocks holds a list of statements to iterate through.
        NodeList<Statement> codeBlocks=code.getStatements();
        //iterate through every statement in codeBlocks
        for(Statement stmt: codeBlocks){
            if(stmt.isSwitchStmt())
                return stmt;
        }
        return null;
    }

    public int getNumberOfSwitches(ClassOrInterfaceDeclaration cl){
        int numSwitches = 0;
        List<MethodDeclaration> methods = cl.getMethods();
        //iterate through all the declared methods in the given class 'cl'
        for(MethodDeclaration m: methods){
            if(containsSwitch(m))
                numSwitches++;
        }
        return numSwitches;
    }
    public ThreatLevel complexityOfClass(ClassOrInterfaceDeclaration cl){
        int numSwitches = 0;
        List<MethodDeclaration> methods = cl.getMethods();
        //iterate through all the declared methods in the given class 'cl'
        for(MethodDeclaration m: methods){
            if(containsSwitch(m)){
                Statement switchStmt = getSwitchStatement(m);
                ThreatLevel complex = switchComplexity(switchStmt);
                //if complex is greater to complexity, we reached higher complexity so set it.
                if(complex.compareTo(complexity)>0){
                    complexity = complex;
                }
            }
            if(complexity==ThreatLevel.HIGH)
                break;
        }
        return complexity;
    }
    private ThreatLevel switchComplexity(Statement stmt){
        ThreatLevel complexity; //(0,1,2,3) (None, low, medium, high)
        int linesPerCase = 0;
        for(Node statementChildren: stmt.getChildNodes()){
            //if the current case complexity is higher then what we have, switch complexity to that.

            int begin = -1;
            for(Node interiorChildren: statementChildren.getChildNodes()){
                if(begin>=0) {
                    //System.out.println(interiorChildren.getChildNodes()+ " | "+interiorChildren.getChildNodes().size());
                    int numStmtsInCase = interiorChildren.getChildNodes().size();
                    if (linesPerCase < numStmtsInCase) {
                        linesPerCase = numStmtsInCase;
                    }
                }
                begin++;
            }
        }

        //set complexity according to linesPerCase found.
        if(linesPerCase >= 2){
            complexity = ThreatLevel.LOW;
        }
        else if(linesPerCase >= 5){
            complexity = ThreatLevel.MEDIUM;
        }
        else{
            complexity = ThreatLevel.HIGH;
        }
        return complexity;
    }
}
