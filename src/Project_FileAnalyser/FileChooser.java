package Project_FileAnalyser;
import BloatCheckers.BloatedCodeAbuseCheck;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import utility_classes.CompilationUnitVisitor;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class FileChooser {
   //open file explorer

    //function that opens the users file explorer to select and return a folder
    public File selectFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(chooser.FILES_AND_DIRECTORIES);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
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
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
        compunitvisitor.visit(cu, classes);

        BloatedCodeAbuseCheck checkBloats = new BloatedCodeAbuseCheck(classes);
        HashMap<ClassOrInterfaceDeclaration, HashMap> map = checkBloats.getClassThreats();
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

