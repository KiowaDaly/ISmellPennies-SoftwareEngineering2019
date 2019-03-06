package BloatedCodeCheckers;

public class ClassBloatedCodeChecker extends BloatedCodeChecker {

    public ClassBloatedCodeChecker(Class myClass){
        super(myClass);
    }
    public int getNumberOfMethods(){
        //TODO read the number of method declaractions in the class
        return objectclass.getMethods().length;
    }








}
