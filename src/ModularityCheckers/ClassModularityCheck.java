package ModularityCheckers;

import java.lang.reflect.Method;

public class ClassModularityCheck implements Countable {
    private Class objectclass;//class object that we will perform test on

        public ClassModularityCheck(Class classObject){
        this.objectclass = classObject;
    }

    public long getLength(){
        //TODO read the class from the file and count its length
        return 0;
    }
   public int getNumberOfMethods(){
        //TODO read the number of method declaractions in the class
        Method[] methods = objectclass.getMethods();

        return methods.length;
   }

   public String getClassName(){
            return objectclass.getSimpleName();
   }


}
