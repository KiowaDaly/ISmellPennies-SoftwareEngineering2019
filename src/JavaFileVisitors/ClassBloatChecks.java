package JavaFileVisitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class ClassBloatChecks extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>> {
    public void visit(ClassOrInterfaceDeclaration cl, List<ClassOrInterfaceDeclaration> collector) {
        super.visit(cl, collector);
        collector.add(cl);
    }
    public void GetClassLengths(CompilationUnit cu,List<ClassOrInterfaceDeclaration> collector){
        visit(cu,collector);
        collector.forEach(n -> {int length = (n.getEnd().get().line - n.getBegin().get().line - 1);
            System.out.println("Class: "+n.getNameAsString()+" has "+length+" lines of code");});
    }

}
