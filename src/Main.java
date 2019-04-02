


import BloatCheckers.ClassBloatChecks;
import ProjectReader.FileChooser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.StaticJavaParser;
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
            File myfile = new File("C:/Users/kiowa/Desktop/SOFTWARE ENGINEERING/src/test.java");
            CompilationUnit cu = StaticJavaParser.parse(myfile);
            List<ClassOrInterfaceDeclaration> list = new ArrayList<>();
            CompilationUnitVisitor myVisitor = new CompilationUnitVisitor();

            ClassBloatChecks check = new ClassBloatChecks();
            for (ClassOrInterfaceDeclaration cl:list) {
                System.out.println("\nChecking class: " + cl.getNameAsString()+"\n");
                System.out.println("\nNumber of lines: "+check.getNumLines(cl));
                System.out.println("\nNumber of Comments: "+check.getNumComments(cl));
                System.out.println("\nNumber of fields: "+check.getNumFields(cl));
                System.out.println("\nNumber of methods: "+check.getNumMethods(cl));


        }


    }
}
