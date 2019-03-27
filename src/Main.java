import BloatedCodeCheckers.ClassBloatedCodeChecker;
import BloatedCodeCheckers.BloatedCodeChecker;
import BloatedCodeCheckers.MethodBloatedCodeChecker;
import ProjectReader.FileChooser;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import parser.JavaFileParser;
import com.github.javaparser.StaticJavaParser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;


public class Main {
    private static final String FILE_PATH = "C:/Users/rebec/IdeaProjects/ISmellPennies-SoftwareEngineering2019/src";

    public static void main(String[] argc) throws IOException, ClassNotFoundException, Exception {
//        FileChooser folder = new FileChooser();
//        System.out.println("List of files and directories");
//        for (String s : folder.selectFolder().list()) {
//        //    System.out.println(s + "\n");
//        }
//        JavaFileParser ourParser = new JavaFileParser();
      //  List myclass = ourParser.parseFileToClassObject(new File("C://Users//kiowa//Desktop//classLoader/test.java"));
      //  System.out.println("List of classed in file: " + myclass.get(0));


        //VistorStarted

        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
    }




       //THIS FILE IS FOR TESTING PURPOSES - NOT THE REAL MAIN METHOD
       // myClassLoader loader = new myClassLoader();
//        ClassBloatedCodeChecker bloatedCheck = new ClassBloatedCodeChecker(BloatedCodeChecker.class);
//        System.out.println(bloatedCheck.getClassName() +" has "+ bloatedCheck.getNumberOfMethods() +" user defined methods");
//        System.out.println(methodCheck.methodHasTooManyParameters());

/*
        //06/03/2019 - obtaining methods/ variable types from classes
        //example using BloatedCodeChecker

        Field df[] = MethodBloatedCodeChecker.class.getDeclaredFields();
        Field f[] = MethodBloatedCodeChecker.class.getFields();

        //Protected, private, public
        System.out.println("\nDeclared fields: ");
        for(Field f1: df){
            System.out.println(f1.toString());
        }
        //Just public
        System.out.println("\nFields: ");
        for(Field f2: f) System.out.println("\n" + f2.toString());

        //Class methods
        System.out.println("\nClass Methods: ");
        for(Method m: MethodBloatedCodeChecker.class.getMethods()) System.out.println(m.toString());

        //this whole section of code can be snipped and slotted into proper classes once created - not to stay in class.Main
    */

}
