package ObjectOrientedAbusers.RefusedBequestHelpers;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;

public class BetaFactory {

    /*
    used to test if a file is beta or not, if so make it Beta and get its alpha.
    */

    public boolean isBeta(ClassOrInterfaceDeclaration clase){
        //check if a class is interface (ignore if so).
        if(!clase.isInterface()){
            //if the class is a subclass (beta) it will have a number of inheritance greater than 0 (none).
            int numInherited  = clase.getExtendedTypes().size();
            if(numInherited>0)
               return true;//return true since we know the clase is Beta

        }
        //return false indicating either the class is an interface or does not inherit.
        return false;
    }

    public NodeList<ClassOrInterfaceType> getMultipleBeta(ClassOrInterfaceDeclaration cl){
        NodeList<ClassOrInterfaceType> extendedTypes = cl.getExtendedTypes();
        return extendedTypes;
    }

    public boolean isBetaMultiple(ClassOrInterfaceDeclaration clase){
        int numInherited = clase.getExtendedTypes().size();
        return (numInherited>1);
    }

    public void setUpMultipleBetas(ClassOrInterfaceDeclaration betas){

    }

    public String getAlpha(ClassOrInterfaceDeclaration betaClase){
        return betaClase.getExtendedTypes(0).getName().toString();
    }


    public Beta setUpBeta(ClassOrInterfaceDeclaration betaClase,ClassOrInterfaceDeclaration alphaClase){
        Beta beta = new Beta(betaClase, alphaClase);
        return beta;
    }


}
