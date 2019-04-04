package utility_classes;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class CompilationUnitVisitor extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>> {


    public void visit(ClassOrInterfaceDeclaration cls, List<ClassOrInterfaceDeclaration> collector_class){
        super.visit(cls, collector_class);
        collector_class.add(cls);
    }



}
