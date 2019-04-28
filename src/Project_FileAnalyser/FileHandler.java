package Project_FileAnalyser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class FileHandler {
   //open file explorer

    //function that opens the users file explorer to select and return a folder
    int counter;
    public File selectFolder() {
        DirectoryChooser Directory = new DirectoryChooser();
        Directory.setTitle("Select desired source folder");
        return  Directory.showDialog(null);
    }

    public String getResults(File f) throws FileNotFoundException {
        List<CompilationUnit> units = loopFolders(f);

        SmellDetectorCalls.getInstance().AnalyseProject(units);
        SmellDetectorCalls.getInstance().setNumFiles(counter);
        String S = SmellDetectorCalls.getInstance().printResults();
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
                    counter++;
                }

            }
        }

        return units;

    }


}

