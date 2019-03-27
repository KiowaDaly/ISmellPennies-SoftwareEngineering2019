package JavaFileVisitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import parser.JavaFileParser;
import com.github.javaparser.StaticJavaParser;

import java.util.List;

public class MethodCollector extends VoidVisitorAdapter<List<MethodDeclaration>> {

    public void visit(MethodDeclaration md, List<MethodDeclaration> collector) {
        super.visit(md, collector);
        collector.add(md);
    }

    public void MethodLengths(MethodDeclaration md, List<MethodDeclaration> collector){
        visit(md,collector);
        collector.forEach(n -> {int length = (n.getEnd().get().line - n.getBegin().get().line - 1);
                                    System.out.println("Method: "+n.getNameAsString()+" has "+length+" lines of code");});
    }
}