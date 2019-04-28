import ExcessiveCoupling.ExcessiveCouplingChecks;
import Lazies_Freeloader_walkingdead.WalkingDeadChecks;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {
  //  private static final String FILE_PATH = "src/test.java";

    public static void main(String[] argc) throws Exception {


        System.out.println("----------> number of class declarations <----------");
        ExcessiveCouplingChecks fe = new ExcessiveCouplingChecks();
        CompilationUnit cl = StaticJavaParser.parse(new File("src/testDataonly.java"));
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
        compunitvisitor.visit(cl, classes);
//        System.out.println(fe.getNumClassInstances(classes.get(0)));
//        System.out.println(fe.getNumMethodCalls(classes.get(0)));
//        System.out.println(fe.getNumVsriableCalls(classes.get(0)));
      //  System.out.println(fe.isMiddleMan(classes.get(0)));




        System.out.println("Feature Envy? " + fe.getNumVariableCalls(classes.get(0)));
        WalkingDeadChecks wd = new WalkingDeadChecks();
        for(ClassOrInterfaceDeclaration cx:classes){
           System.out.println("Threat level of duplication in "+cx.getNameAsString()+": " + wd.getDuplicationLevel(cx));
            }

        System.out.println(classes.get(0).getNameAsString()+" is data only?: " + wd.isDataOnlyClass(classes.get(0)));
        System.out.println(classes.get(0).getNameAsString()+" Speculative Generality threat level: : " + wd.SpeculativeGeneralityChecker(classes.get(0)));
        System.out.println(classes.get(0).getNameAsString()+" Lazy:  " + wd.LazyCodeCheck(classes.get(0)));
        System.out.println(classes.get(0).getNameAsString()+" Dead:  " + wd.deadCodeChecker(classes.get(0)));
        System.out.println(classes.get(0).getNameAsString()+" Overall:  " + wd.overallWalkingDead(classes.get(0)));

    }


}