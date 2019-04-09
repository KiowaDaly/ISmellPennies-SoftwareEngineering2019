


import BloatCheckers.BloatedCodeAbuseCheck;
import BloatCheckers.ClassBloatChecks;
import BloatCheckers.MethodBloatChecks;
import ObjectOrientedAbusers.SwitchChecker;
import Project_FileAnalyser.FileChooser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.StaticJavaParser;
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
        FileExplorer.loopFolders(ourProject);
    }


}
