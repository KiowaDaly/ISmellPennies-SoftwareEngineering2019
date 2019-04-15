import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class RefusedBequest{

    private int ignoredBehaviours;
    private int totalBehaviours;
    private boolean smell; //at construction of object do
    //if total - ignored != 0; smell.
    //constructor should just
    public boolean isSmell() {
        return false;
    }

    private boolean isIgnored(MethodDeclaration subMethod, MethodDeclaration parentMethod){

        return false;
    }
    //go through the list of methods.
    private void iterateThroughClass(ClassOrInterfaceDeclaration clase){

    }
    //get the parent of the clase. used for iterateThroughClass
    private ClassOrInterfaceDeclaration getParentOfClass(ClassOrInterfaceDeclaration clase){

        return null;
    }
}