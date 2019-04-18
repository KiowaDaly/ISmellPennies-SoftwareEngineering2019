import ExcessiveCoupling.FeatureEnvy;
import Project_FileAnalyser.FileHandler;
import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final String FILE_PATH = "src/test.java";

    public static void main(String[] argc) throws Exception {


        System.out.println("----------> number of class declarations <----------");
        FeatureEnvy fe = new FeatureEnvy();
        CompilationUnit cl = StaticJavaParser.parse(new File("src/test.java"));
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
        compunitvisitor.visit(cl, classes);
        System.out.println(fe.getNumClassInstances(classes.get(0)));
        System.out.println(fe.getNumMethodCalls(classes.get(0)));
        System.out.println(fe.getNumVsriableCalls(classes.get(0)));

    }


}