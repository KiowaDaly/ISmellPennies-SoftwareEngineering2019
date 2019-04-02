package BloatCheckers;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class ClassBloatChecks implements BloatChecking<Integer,ClassOrInterfaceDeclaration> {



    public Integer getNumLines(ClassOrInterfaceDeclaration n) {
        return (n.getEnd().get().line - n.getBegin().get().line - 1);
    }

    public Integer getNumComments(ClassOrInterfaceDeclaration n) {
        return n.getAllContainedComments().size();
    }

    public Integer getNumFields(ClassOrInterfaceDeclaration cl) {
        return cl.getFields().size();
    }

    public Integer[] getIDLengths(ClassOrInterfaceDeclaration n) {
        Integer[] array = {};
        return array;
    }

    public Integer getNumParameters(ClassOrInterfaceDeclaration n) {
        return 0;
    }

    public Integer getNumMethods(ClassOrInterfaceDeclaration n) {
        return n.getMethods().size();
    }
}
