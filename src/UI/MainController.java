package UI;

import Project_FileAnalyser.FileChooser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class MainController {



    @FXML
    public TextArea RESULTS;



    @FXML
    public void selectFile() throws FileNotFoundException {
        FileChooser FileExplorer = new FileChooser();
        File ourProject = FileExplorer.selectFolder();
        String s = FileExplorer.getResults(ourProject);

        RESULTS.setText("----------> SMELL CHECK RESULTS <----------\n\n" + s);
    }

    public void InterpretFile(DragEvent event) throws FileNotFoundException {

        FileChooser FileExplorer = new FileChooser();
        File ourProject = new File(event.getDragboard().getFiles().toString());
        System.out.println("revieved folder : "+ event.getDragboard().getFiles().toString());
        System.out.println("----------> SMELL CHECK RESULTS <----------");
        FileExplorer.loopFolders(ourProject);
    }

}
