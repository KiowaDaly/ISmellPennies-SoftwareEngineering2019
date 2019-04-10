package ExcessiveCoupling;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;


public class FeatureEnvy implements ExcessiveCoupling<Integer, ClassOrInterfaceDeclaration> {

    public Integer getNumClassInstances(ClassOrInterfaceDeclaration ci){
        return 0;
    }
    public Integer getNumMethodCalls(ClassOrInterfaceDeclaration ci){
        return 0;
    }


}
