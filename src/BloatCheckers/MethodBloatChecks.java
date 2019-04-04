package BloatCheckers;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.List;

@SuppressWarnings("ALL")
public class MethodBloatChecks implements BloatChecking<Integer,MethodDeclaration> {

    /*code below gets the starting line of the method and the endline and subtracts start line from end line to get-
      The number of lines in the code, it then prints out the method name and the length of it.
    */

    public Integer getNumLines(MethodDeclaration md) {
        return (md.getEnd().get().line - md.getBegin().get().line - 1);
    }

    public Integer getNumComments(MethodDeclaration md) {
        return md.getAllContainedComments().size();
    }


    public Integer getNumFieldsOrVariables(MethodDeclaration md) {

        return md.findAll(VariableDeclarator.class).size();
    }


    public Integer[] getIDLengths(MethodDeclaration md) {
        return null;
    }


    public Integer getNumParameters(MethodDeclaration md) {
        return md.getParameters().size();
    }


}
