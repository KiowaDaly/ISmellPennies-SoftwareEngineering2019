package BloatCheckers;



import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.metamodel.TypeDeclarationMetaModel;


public interface BloatChecking<T,U>{

    T getNumLines(U md);
    T getNumComments(U md);
    T getNumFields(U md);
    T[] getIDLengths(U md);
    T getNumParameters(U md);
    T getNumMethods(U md);

}
