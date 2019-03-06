import BloatedCodeCheckers.ClassBloatedCodeChecker;

import BloatedCodeCheckers.BloatedCodeChecker;
import BloatedCodeCheckers.MethodBloatedCodeChecker;


public class Main {
    public static void main(String[] argc){

//        FileChooser file = new FileChooser();
//        file.selectFile();
        //THIS FILE IS FOR TESTING PURPOSES - NOT THE REAL MAIN METHOD
        MethodBloatedCodeChecker methodCheck = new MethodBloatedCodeChecker(ClassBloatedCodeChecker.class.getMethods()[1]);
        ClassBloatedCodeChecker modularCheck = new ClassBloatedCodeChecker(BloatedCodeChecker.class);
        System.out.println(modularCheck.getClassName() +" has "+ modularCheck.getNumberOfMethods() +" Methods-including its subclasses");
        System.out.println(methodCheck.methodHasTooManyParameters());

    }
}
