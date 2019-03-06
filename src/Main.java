import BloatedCodeCheckers.ClassBloatedCodeChecker;
import BloatedCodeCheckers.BloatedCodeChecker;
import BloatedCodeCheckers.MethodBloatedCodeChecker;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main {
    public static void main(String[] argc){

//        FileChooser file = new FileChooser();
//        file.selectFile();
        //THIS FILE IS FOR TESTING PURPOSES - NOT THE REAL MAIN METHOD
        MethodBloatedCodeChecker methodCheck = new MethodBloatedCodeChecker(ClassBloatedCodeChecker.class.getMethods()[1]);
        ClassBloatedCodeChecker modularCheck = new ClassBloatedCodeChecker(BloatedCodeChecker.class);
        System.out.println(modularCheck.getClassName() +" has "+ modularCheck.getNumberOfMethods() +" Methods-including its subclasses");
        System.out.println(methodCheck.methodHasTooManyParameters());

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
    }
}
