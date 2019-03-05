package ModularityCheckers;

import java.lang.reflect.Method;

public class ModularityCheck {

    protected Class objectclass;//class object that we will perform test on
    public ModularityCheck(){}
    public ModularityCheck(Class classObject){
        this.objectclass = classObject;
    }
    public int getLength(){
        //TODO read the class from the file and count its length
        return 0;
    }
    public String getClassName(){
        return objectclass.getSimpleName();
    }
}
