import ModularityCheckers.ClassModularityCheck;

import ModularityCheckers.ModularityCheck;


public class Main {
    public static void main(String[] argc){

//        FileChooser file = new FileChooser();
//        file.selectFile();
        ClassModularityCheck modularCheck = new ClassModularityCheck(ClassModularityCheck.class);
        System.out.println(modularCheck.getClassName() +" has "+ modularCheck.getNumberOfMethods() +" Methods");

    }
}
