package BloatedCodeCheckers;

public class BloatedCodeChecker {
    protected final int METHOD_NUMBER_THRESHOLD = 10;
    protected Class objectclass;//class object that we will perform test on
    public BloatedCodeChecker(){}
    public BloatedCodeChecker(Class classObject){
        this.objectclass = classObject;
    }

    public int getLength(){
        //TODO read the class from the file and count its length
        return 0;
    }
    public String getClassName(){
        return objectclass.getSimpleName();
    }

    public boolean hasTooManyMethods(){
        return objectclass.getMethods().length > METHOD_NUMBER_THRESHOLD;
    }

}
