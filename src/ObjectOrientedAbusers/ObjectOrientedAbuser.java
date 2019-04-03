package ObjectOrientedAbusers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ObjectOrientedAbuser {
    private static final String FILE_PATH = "src/test.java";
    private SwitchChecker switchChecker;
    public ObjectOrientedAbuser(){
        switchChecker = new SwitchChecker();
    }
    public static void main(String[] args) throws FileNotFoundException {
        /*
            The main method in this file is purely for testing functions.
            TODO Delete this method when completed.
         */

        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        CompilationUnitVisitor cuv = new CompilationUnitVisitor();
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        cuv.visit(cu, classes);

        //test
        for(ClassOrInterfaceDeclaration clase: classes){
            //System.out.println(clase.toString());
            List<MethodDeclaration> methods = clase.getMethods();
            for(MethodDeclaration m: methods){

            }
        }
    }
}
