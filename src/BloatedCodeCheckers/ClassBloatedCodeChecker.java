package BloatedCodeCheckers;

public class ClassBloatedCodeChecker extends BloatedCodeChecker {
    protected final int METHOD_NUMBER_THRESHOLD = 10;

    public ClassBloatedCodeChecker(Class myClass){
        super(myClass);
    }

    public int getNumberOfMethods(){
        //TODO read the number of method declaractions in the class
        return class_object.getMethods().length;
    }
    public boolean hasTooManyMethods(){
        return getNumberOfMethods() > METHOD_NUMBER_THRESHOLD;
    }








}
