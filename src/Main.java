import BloatedCodeCheckers.ClassBloatedCodeChecker;
import BloatedCodeCheckers.BloatedCodeChecker;
import BloatedCodeCheckers.MethodBloatedCodeChecker;
import ProjectReader.FileChooser;

import java.net.MalformedURLException;


public class Main {
    public static void main(String[] argc) throws MalformedURLException, ClassNotFoundException {



        FileChooser folder = new FileChooser();
        System.out.println("List of files and directories");
        for(String s:folder.selectFolder().list()){
            System.out.println(s+"\n");
        }
        //THIS FILE IS FOR TESTING PURPOSES - NOT THE REAL MAIN METHOD
       // myClassLoader loader = new myClassLoader();
//        MethodBloatedCodeChecker methodCheck = new MethodBloatedCodeChecker(ClassBloatedCodeChecker.class.getMethods()[1]);
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
}
