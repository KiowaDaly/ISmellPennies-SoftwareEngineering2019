package ObjectOrientedAbusers.RefusedBequestHelpers;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class Beta {

    private ClassOrInterfaceDeclaration alpha;
    private ClassOrInterfaceDeclaration beta;

    private int invocateAlpha;
    private int invocateBeta;

    public Beta(ClassOrInterfaceDeclaration b, ClassOrInterfaceDeclaration a){
        beta = b;
        alpha = a;
    }

    public double numberOfAlphasOverBetas(){
        return (double)invocateBeta/invocateAlpha;
    }


    public String toString(){
        return "{Beta: '"+beta.getName().toString()+"', Alpha: '"+alpha.getName().toString()+"'}";
    }

    public void invocatesAlpha(){invocateAlpha++;}
    public void invocatesBeta(){invocateBeta++;}

    public ClassOrInterfaceDeclaration getBeta(){return beta;}
    public ClassOrInterfaceDeclaration getAlpha(){return alpha;}


}
