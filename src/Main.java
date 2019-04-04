


import BloatCheckers.BloatedCodeAbuseCheck;
import BloatCheckers.ClassBloatChecks;
import BloatCheckers.MethodBloatChecks;
import ObjectOrientedAbusers.SwitchChecker;
import ProjectReader.FileChooser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String FILE_PATH = "src/test.java";

    public static void main(String[] argc) throws Exception {

        FileChooser FileExplorer = new FileChooser();
        File ourProject = FileExplorer.selectFolder();
        loopFolders(ourProject);
    }

    public static void loopFolders(File f) throws FileNotFoundException {
        File[] folders = f.listFiles();
        File[] Files = f.listFiles((dir, name) -> name.toLowerCase().endsWith(".java"));

        for(File fi:folders){
            if(fi.isDirectory()){
                loopFolders(fi);
            }
            else{
                    compUnitLoop(fi);
            }


        }
    }
    public static void compUnitLoop(File fi) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(fi);
        ClassBloatChecks check_bloat = new ClassBloatChecks();
        MethodBloatChecks method_bloat = new MethodBloatChecks();
        SwitchChecker switchAnalysis = new SwitchChecker();
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
        compunitvisitor.visit(cu, classes);
//        for(ClassOrInterfaceDeclaration n:classes){
//            System.out.println();
//            System.out.println("====== TESTING " + n.getNameAsString().toUpperCase()+" ======\n");
//            System.out.println("Number of lines: "+ check_bloat.getNumLines(n));
//            System.out.println("Number of Comments: "+ check_bloat.getNumComments(n));
//            System.out.println("Number of fields: "+ check_bloat.getNumFieldsOrVariables(n));
//            System.out.println("Number of methods: "+ check_bloat.getNumMethods(n));
//            System.out.println("\nNumber of switches: "+ switchAnalysis.getNumberOfSwitches(n));
//            System.out.println("\nComplexity of the switch statements: "+switchAnalysis.complexityOfClass(n));
//            for (MethodDeclaration m:n.getMethods()) {
//                System.out.println("\n=================================");
//                System.out.println("Method: " + m.getNameAsString());
//                System.out.println("Number of Lines: "+ method_bloat.getNumLines(m));
//                System.out.println("Number of Comments: "+ method_bloat.getNumComments(m));
//                System.out.println("Number of parameter: "+ method_bloat.getNumParameters(m));
//                System.out.println("Number of Variables: "+ method_bloat.getNumFieldsOrVariables(m));
//                System.out.println("");
//
//            }
//        }
        BloatedCodeAbuseCheck checkBloats = new BloatedCodeAbuseCheck(classes);
        HashMap<ClassOrInterfaceDeclaration,HashMap> map = checkBloats.getClassThreats();
        checkBloats.performBloatChecks();
        for (ClassOrInterfaceDeclaration cl : map.keySet()) {

            HashMap value = map.get(cl);

            Set<BloatedCodeAbuseCheck.ThreatLevel> t =  value.keySet();
            for(BloatedCodeAbuseCheck.ThreatLevel tl:t ){
                System.out.println("CLASS: "+cl.getNameAsString().toUpperCase()+" has a Bloat threat level of: "+tl.toString());
            }
        }

    }
}
