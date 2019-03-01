package ModularityCheckers;

import java.io.File;

public class ClassModularityCheck implements Countable {
    private File file;
    public ClassModularityCheck(File filename){
        this.file = filename;
    }

    public long getLength(){
        //TODO read the class from the file and count its length
        return 0;
    }
   public int getNumberOfMethods(){
        //TODO read the number of method declaractions in the class
        return 0;
   }

}
