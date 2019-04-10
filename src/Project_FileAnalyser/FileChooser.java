package Project_FileAnalyser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class FileChooser {
   //open file explorer

    //function that opens the users file explorer to select and return a folder
    public File selectFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(chooser.FILES_AND_DIRECTORIES);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    public void loopFolders(File f) throws FileNotFoundException {
        File[] folders = f.listFiles();
        File[] Files = f.listFiles((dir, name) -> name.toLowerCase().endsWith(".java"));
        List<CompilationUnit> units = new ArrayList<>();

        for(File fi:folders){
            if(fi.isDirectory()){
                loopFolders(fi);
            }
            else{
                units.add(StaticJavaParser.parse(fi));
            }
        }
        SmellDetectorCalls smells = new SmellDetectorCalls(units);
        smells.AnalyseProject();
        smells.printResults();

    }


}

