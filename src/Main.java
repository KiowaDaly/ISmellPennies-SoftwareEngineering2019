


import BloatCheckers.ClassBloatChecks;
import ProjectReader.FileChooser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FILE_PATH = "src/test.java";

    public static void main(String[] argc) throws Exception {

//        FileChooser FileExplorer = new FileChooser();
//        File ourProject = FileExplorer.selectFolder();
//        File[] f = ourProject.listFiles((dir, name) -> name.toLowerCase().endsWith(".java"));
//        for(File fi:f){
//            CompilationUnit cu = StaticJavaParser.parse(fi);
//            List<MethodDeclaration> methodNames = new ArrayList<>();
//            MethodBloatChecks myMethods = new MethodBloatChecks();
//            System.out.println("\n -----------METHOD LENGTH BLOAT CHECK-----------\n");
//            myMethods.MethodLengths(cu,methodNames);
//            System.out.println("\n -----------COMMENT BLOAT CHECK-----------\n");
//            List<MethodDeclaration> methodName = new ArrayList<>();
//            myMethods.getNumCommentsOfEachMethod(cu,methodName);
//
//            System.out.println("\n -----------CLASS LENGTH CHECKS-----------\n");
//            List<ClassOrInterfaceDeclaration> ClassNames = new ArrayList<>();
//            ClassBloatChecks ClassNameCollector = new ClassBloatChecks();
//            ClassNameCollector.GetClassLengths(cu,ClassNames);
//        }
            File myfile = new File("src/test.java");
            CompilationUnit cu = StaticJavaParser.parse(myfile);
//            List<ClassOrInterfaceDeclaration> list = new ArrayList<>();
//
//        GenericVisitorAdapter compilationUnitVisitor = new CompilationUnitVisitor();
//        ((CompilationUnitVisitor) compilationUnitVisitor).visit(cu, list);
//        System.out.println("List:" + list.size());

//
//        list.forEach(n -> {
//            System.out.println("\nChecking class: " + n.getNameAsString()+"\n");
//            System.out.println("\nNumber of lines: "+check.getNumLines(n));
//            System.out.println("\nNumber of Comments: "+check.getNumComments(n));
//            System.out.println("\nNumber of fields: "+check.getNumFields(n));
//            System.out.println("\nNumber of methods: "+check.getNumMethods(n));
//        });
        ClassBloatChecks check_bloat = new ClassBloatChecks();
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
        compunitvisitor.visit(cu, classes);
        classes.forEach(n -> {
            System.out.println("Name of class: " + n.getName());
            System.out.println("\nNumber of lines: "+ check_bloat.getNumLines(n));
            System.out.println("\nNumber of Comments: "+ check_bloat.getNumComments(n));
            System.out.println("\nNumber of fields: "+ check_bloat.getNumFields(n));
            System.out.println("\nNumber of methods: "+ check_bloat.getNumMethods(n));
        });






    }
}
