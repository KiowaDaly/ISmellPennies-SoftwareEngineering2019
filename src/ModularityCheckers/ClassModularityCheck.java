package ModularityCheckers;

import java.lang.reflect.Method;

public class ClassModularityCheck extends ModularityCheck {

    public ClassModularityCheck(Class myClass){
        super(myClass);
    }
    public int getNumberOfMethods(){
        //TODO read the number of method declaractions in the class
        return objectclass.getMethods().length;
    }








}
