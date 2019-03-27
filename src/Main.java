
import JavaFileVisitors.ClassCollector;
import JavaFileVisitors.MethodCollector;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.StaticJavaParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FILE_PATH = "src/test.java";

    public static void main(String[] argc) throws Exception {

        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        List<MethodDeclaration> methodNames = new ArrayList<>();
        VoidVisitor<List<MethodDeclaration>> myMethods = new MethodCollector();
        System.out.println("\n -----------METHOD LENGTH BLOAT CHECK-----------\n");
        ((MethodCollector) myMethods).MethodLengths(cu,methodNames);
        System.out.println("\n -----------COMMENT BLOAT CHECK-----------\n");
        List<MethodDeclaration> methodName = new ArrayList<>();
        ((MethodCollector) myMethods).getNumCommentsOfEachMethod(cu,methodName);
        System.out.println("\n -----------OTHER CHECKS-----------\n");
        List<String> ClassNames = new ArrayList<>();
        VoidVisitor<List<String>> ClassNameCollector = new ClassCollector();
        ClassNameCollector.visit(cu, ClassNames);
        ClassNames.forEach(n -> System.out.println("Classes in File: " + n));

    }
}
