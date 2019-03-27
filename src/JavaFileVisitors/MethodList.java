package JavaFileVisitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.lang.reflect.Method;

public class MethodList extends VoidVisitorAdapter<Method> {
    public void visit(MethodDeclaration md, List<Method> arg) {
        super.visit(md, arg);

        ;
    }
}
