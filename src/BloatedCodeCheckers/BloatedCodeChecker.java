package BloatedCodeCheckers;

public class BloatedCodeChecker {


    protected Class class_object;//class object that we will perform test on
    public BloatedCodeChecker(){}
    public BloatedCodeChecker(Class classObject ){
        this.class_object = classObject;
    }

    public int getLength(){
        //TODO read the class from the file and count its length
        return 0;
    }
    public String getClassName(){
        return class_object.getSimpleName();
    }



}
