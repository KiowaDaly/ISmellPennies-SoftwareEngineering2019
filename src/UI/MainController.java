package UI;
/* THIS FILE IS WHERE WE IMPLEMENT THE FUNCTIONS THAT TIE OUR CODE TO THE UI SCENE
 */
import Project_FileAnalyser.FileHandler;
import Project_FileAnalyser.SmellDetectorCalls;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainController {


    public TextArea RESULTS;
    public ListView<String> UnitLists;
    public Pane DropPane;
    public MenuBar Menu;
    public MenuItem Help;
    ObservableList<String> items = FXCollections.observableArrayList();





    //function that is called when we click the "choose file" button.
    public void selectFile() throws FileNotFoundException {
        FileHandler FileExplorer = new FileHandler();
        File ourProject = FileExplorer.selectFolder();
        String s = FileExplorer.getResults(ourProject);
        //RESULTS.setText("----------> SMELL CHECK RESULTS <----------\n\n" + s);
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            items.add(cl.getNameAsString());
        }
        UnitLists.setItems(items);
    }

    //A function that reads the selected item on our Item list and displays the corresponding classes details
    public void displaySelectedAnalysis(){
        String s = UnitLists.getSelectionModel().getSelectedItem();

        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            //since the method getSelectionModel returns a string we have to check for equivalence like this
            if(cl.getNameAsString() == s){
                RESULTS.setText("Bloat level: " + SmellDetectorCalls.getInstance().getDetections().get(cl).getBloatThreatLevel()+"\nComplexity Level: "+
                        SmellDetectorCalls.getInstance().getDetections().get(cl).getOOAbuseThreatLevel()+"\n" + cl.toString());
            }
        }
    }

    /*The next two methods deal with the drag and drop section*/
    public void getDroppedFile(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    public void handleDropEvent(DragEvent event) throws FileNotFoundException {
        List<File> f = event.getDragboard().getFiles();
        File myFile = f.get(0);
        System.out.println(myFile.getName());
        FileHandler FileExplorer = new FileHandler();
        String s = FileExplorer.getResults(myFile);
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            items.add(cl.getNameAsString());
        }
        UnitLists.setItems(items);

    }










}
