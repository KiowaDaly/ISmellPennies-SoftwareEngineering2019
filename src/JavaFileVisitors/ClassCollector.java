package JavaFileVisitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class ClassCollector extends VoidVisitorAdapter<List<String>> {
    public void visit(ClassOrInterfaceDeclaration cl, List<String> collector) {
        super.visit(cl, collector);
        collector.add(cl.getNameAsString());
    }
}
