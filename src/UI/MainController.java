package UI;
/* THIS FILE IS WHERE WE IMPLEMENT THE FUNCTIONS THAT TIE OUR CODE TO THE UI SCENE
 */
import Project_FileAnalyser.FileHandler;
import Project_FileAnalyser.SmellDetectorCalls;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainController {

    private String[] SmellStrings = {"Bloat Level","Complexity Level","Excessive Coupling","God Class Threat Level"};

    public TextArea RESULTS;
    public ListView<String> UnitLists;
    public ListView<String> SmellList;
    public ListView<String> MethodList;
    public Pane DropPane;
    public Label SmellLabel;
    public Label MethodsLabel;
    public Label ThreatLabel;
    public MenuBar Menu;
    public MenuItem Help;
    public PieChart pieChart;
    public ProgressBar ThreatLevel;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> smellItems = FXCollections.observableArrayList();

    private double bloat;
    private double complexity;
    private double Ec;
    private double Gc;





    //function that is called when we click the "choose file" button.
        public void selectFile() throws FileNotFoundException {
        FileHandler FileExplorer = new FileHandler();
        File ourProject = FileExplorer.selectFolder();
        FileExplorer.getResults(ourProject);
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
            if(cl.getNameAsString().equals(s)){

                disableMethodList();
                bloat = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getBloatThreatLevel().ordinal();
                list.add(new PieChart.Data(SmellStrings[0],bloat));

                complexity = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getOOAbuseThreatLevel().ordinal();
                list.add(new PieChart.Data(SmellStrings[1],complexity));

                Ec = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getExcessiveCouplingThreatLevel().ordinal();
                list.add(new PieChart.Data(SmellStrings[2],Ec));

                Gc = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getGodObjectThreatLevel().ordinal();
                list.add(new PieChart.Data(SmellStrings[3],Gc));

                smellItems.addAll(SmellStrings[0],SmellStrings[1],SmellStrings[2],SmellStrings[3]);
                updateMethodList(cl);
            }
        }

        SmellList.setItems(smellItems);
        pieChart.setData(list);
    }
    private void disableMethodList(){
        MethodList.setVisible(false);
        MethodsLabel.setVisible(false);
    }
    private void updateMethodList(ClassOrInterfaceDeclaration cl){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(MethodDeclaration m:cl.getMethods()){
            list.add(m.getNameAsString());
        }
        MethodList.setItems(list);
    }
   public void updateProgressBar(){
            for(ClassOrInterfaceDeclaration cl:SmellDetectorCalls.getInstance().getDetections().keySet()){
                if(cl.getNameAsString().equals(UnitLists.getSelectionModel().getSelectedItem())){
                    String s = SmellList.getSelectionModel().getSelectedItem();
                    if(s.equals(SmellStrings[0])){
                        ThreatLevel.setProgress(bloat/4);
                        MethodList.setVisible(true);
                        MethodsLabel.setVisible(true);
                        ThreatLabel.setText(cl.getNameAsString()+"::"+SmellStrings[0]);
                    }
                    if(s.equals(SmellStrings[1])){
                        ThreatLevel.setProgress(complexity/4);
                        disableMethodList();
                        ThreatLabel.setText(cl.getNameAsString()+"::"+SmellStrings[1]);

                    }
                    if(s.equals(SmellStrings[2])){
                        ThreatLevel.setProgress(Ec/4);
                        disableMethodList();
                        ThreatLabel.setText(cl.getNameAsString()+"::"+SmellStrings[2]);
                    }
                    if(s.equals(SmellStrings[3])){
                        ThreatLevel.setProgress(Gc/4);
                        disableMethodList();
                        ThreatLabel.setText(cl.getNameAsString()+"::"+SmellStrings[3]);
                    }
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
        FileExplorer.getResults(myFile);
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            items.add(cl.getNameAsString());
        }
        UnitLists.setItems(items);

    }










}
