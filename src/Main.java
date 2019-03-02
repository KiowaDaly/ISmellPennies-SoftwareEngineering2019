import ModularityCheckers.ClassModularityCheck;
import ModularityCheckers.Countable;
import ProjectReader.FileChooser;

public class Main {
    public static void main(String[] argc){

//        FileChooser file = new FileChooser();
//        file.selectFile();
        ClassModularityCheck modularCheck = new ClassModularityCheck(Countable.class);
        System.out.println(modularCheck.getClassName() +" has "+ modularCheck.getNumberOfMethods() +" Methods");

    }
}
