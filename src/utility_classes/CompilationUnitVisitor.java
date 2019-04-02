package utility_classes;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.xml.internal.ws.wsdl.writer.document.Import;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CompilationUnitVisitor extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>> {


    public void visit(ClassOrInterfaceDeclaration cls, List<ClassOrInterfaceDeclaration> collector_class){
        super.visit(cls, collector_class);
       // System.out.println("Class found: " + md.getName());
        collector_class.add(cls);
    }



}
