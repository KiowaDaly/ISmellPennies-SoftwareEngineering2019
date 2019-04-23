package UI;
/* THIS FILE IS WHERE WE IMPLEMENT THE FUNCTIONS THAT TIE OUR CODE TO THE UI SCENE
 */
import Project_FileAnalyser.FileHandler;
import Project_FileAnalyser.SmellDetectorCalls;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.sun.javafx.jmx.HighlightRegion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import utility_classes.ThreatLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainController {

    String[] SmellStrings = {"Bloat Level","Complexity Level","Excessive Coupling"};

    public TextArea RESULTS;
    public ListView<String> UnitLists;
    public ListView<String> SmellList;
    public ListView<String> MethodList;
    public Pane DropPane;
    public Label SmellLabel;
    public MenuBar Menu;
    public MenuItem Help;
    public PieChart pieChart;
    public ProgressBar ThreatLevel;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> smellItems = FXCollections.observableArrayList();

    double bloat;
    double complexity;
    double Ec;





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
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        String s = UnitLists.getSelectionModel().getSelectedItem();
        SmellLabel.setText(s);
        SmellList.getItems().clear();
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            //since the method getSelectionModel returns a string we have to check for equivalence like this
            if(cl.getNameAsString() == s){


                bloat = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getBloatThreatLevel().ordinal();
                list.add(new PieChart.Data("Bloat Level",bloat));

                complexity = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getOOAbuseThreatLevel().ordinal();
                list.add(new PieChart.Data("Complexity Level",complexity));

                Ec = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getExcessiveCouplingThreatLevel().ordinal();
                list.add(new PieChart.Data("Excessive Coupling",Ec));
                smellItems.addAll(SmellStrings[0],SmellStrings[1],SmellStrings[2]);
                updateMethodList(cl);
            }
        }
        SmellList.setItems(smellItems);
        pieChart.setData(list);
    }

    public void updateMethodList(ClassOrInterfaceDeclaration cl){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(MethodDeclaration m:cl.getMethods()){
            list.add(m.getNameAsString());
        }
        MethodList.setItems(list);
    }
   public void updateProgressBar(){
       String s = SmellList.getSelectionModel().getSelectedItem();
       if(s.equals(SmellStrings[0])){
           ThreatLevel.setProgress(bloat/4);
       }
       if(s.equals(SmellStrings[1])){
           ThreatLevel.setProgress(complexity/4);
       }
       if(s.equals(SmellStrings[1])){
           ThreatLevel.setProgress(complexity/4);
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
