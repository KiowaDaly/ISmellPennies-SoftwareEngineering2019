package Project_FileAnalyser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.fxml.FXML;

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

    public String getResults(File f) throws FileNotFoundException {
        List<CompilationUnit> units = loopFolders(f);
        SmellDetectorCalls smells = SmellDetectorCalls.init(units);
        smells.AnalyseProject();
        String S = smells.printResults();
        return S;
    }

    public List<CompilationUnit> loopFolders(File f) throws FileNotFoundException {

        File[] folders = f.listFiles();
        File[] Files = f.listFiles((dir, name) -> name.toLowerCase().endsWith(".java"));
        List<CompilationUnit> units = new ArrayList<>();

        for(File fi:folders){
            if(fi.isDirectory()){
                units.addAll(loopFolders(fi));
            }
            else{

                if(fi.getName().toLowerCase().endsWith(".java")){
                    units.add(StaticJavaParser.parse(fi));
                }

            }
        }

        return units;

    }


}

