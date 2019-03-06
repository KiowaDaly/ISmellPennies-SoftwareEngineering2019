import BloatedCodeCheckers.ClassBloatedCodeChecker;

import BloatedCodeCheckers.BloatedCodeChecker;


public class Main {
    public static void main(String[] argc){

//        FileChooser file = new FileChooser();
//        file.selectFile();
        ClassBloatedCodeChecker modularCheck = new ClassBloatedCodeChecker(BloatedCodeChecker.class);
        System.out.println(modularCheck.getClassName() +" has "+ modularCheck.getNumberOfMethods() +" Methods");

    }
}
