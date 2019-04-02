package utility_classes;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnitVisitor extends GenericVisitorAdapter {


    public ArrayList<Name> visit(Name n, ArrayList<Name> Collector){
        super.visit(n,Collector);
        Collector.add(n);
        return Collector;
    }


}
