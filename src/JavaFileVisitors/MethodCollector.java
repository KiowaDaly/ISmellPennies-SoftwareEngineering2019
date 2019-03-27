package JavaFileVisitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import parser.JavaFileParser;
import com.github.javaparser.StaticJavaParser;

import java.util.List;

public class MethodCollector extends VoidVisitorAdapter<List<String>> {
    public void visit(MethodDeclaration md, List<String> collector) {
        super.visit(md, collector);
        collector.add(md.getNameAsString());
    }
}
