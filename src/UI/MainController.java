package UI;
/* THIS FILE IS WHERE WE IMPLEMENT THE FUNCTIONS THAT TIE OUR CODE TO THE UI SCENE
 */
import Project_FileAnalyser.FileHandler;
import Project_FileAnalyser.JSONReport;
import Project_FileAnalyser.SmellDetectorCalls;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.json.JSONException;
import utility_classes.ThreatLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainController {

    private String[] SmellStrings = {"Bloat Level","Complexity Level","Excessive Coupling","God Class Threat Level", "The Walking Dead", "Overall Smelliness"};


    public TextArea RESULTS;
    public ListView<String> UnitLists;
    public ListView<String> SmellList;
    public ListView<String> MethodList;
    public Pane DropPane;
    public Label SmellLabel;
    public Label MethodsLabel;
    public AreaChart AreaChart;
    public Label ThreatLabel;
    public MenuBar Menu;
    public MenuItem Help;
    public PieChart pieChart;
    public ProgressBar ThreatLevel;
    public TextArea FINALRESULTS;
    public BarChart barChart;
    private int numberOfLines;

    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> smellItems = FXCollections.observableArrayList();


    private double bloat;
    private double complexity;
    private double Ec;
    private double Gc;
    private double Wd;




    //function that is called when we click the "choose file" button.
        public void selectFile() throws FileNotFoundException {
            this.items.clear();
            this.smellItems.clear();
            this.numberOfLines = 0;
        FileHandler FileExplorer = new FileHandler();
        File ourProject = FileExplorer.selectFolder();
        FileExplorer.getResults(ourProject);
        //RESULTS.setText("----------> SMELL CHECK RESULTS <----------\n\n" + s);
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            items.add(cl.getNameAsString());
            numberOfLines += (cl.getEnd().get().line - cl.getBegin().get().line - 1);
        }

        UnitLists.setItems(items.sorted());
            displayFinalResults();
            smellItems.addAll(SmellStrings);
            SmellList.setItems(smellItems.sorted());
    }

    public void displayFinalResults(){
        String finalRes = "";
        finalRes += "We have scanned your project and have found " +SmellDetectorCalls.getInstance().getNumFiles()+ " files and " +numberOfLines+ " lines of code\n";
        finalRes += "We have detected that " +String.format("%.2f",SmellDetectorCalls.getInstance().getOverallThreatLevels()[0])+ "% of your project smells of Bloat\n";
        finalRes += "We have also detected that " +String.format("%.2f",SmellDetectorCalls.getInstance().getOverallThreatLevels()[1])+ "% of your project contains Object Orientated Abuse\n";
        finalRes += "Your code showed signs of Excessive Coupling " +String.format("%.2f",SmellDetectorCalls.getInstance().getOverallThreatLevels()[2])+ "%'s worth! \n";
        finalRes += "Your project also displayed " +String.format("%.2f",SmellDetectorCalls.getInstance().getOverallThreatLevels()[3])+ "% worth of God classes\n";
        finalRes += "Finally, we scanned your project and found that " +String.format("%.2f",SmellDetectorCalls.getInstance().getOverallThreatLevels()[4])+ "% of it may suffer from Walking Dead smells\n";
        FINALRESULTS.setText(finalRes);

    }
   public void bloatLink() throws IOException {
       java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://refactoring.guru/refactoring/smells/bloaters"));
   }
    public void ooLink() throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://refactoring.guru/refactoring/smells/oo-abusers"));
    }
    public void ECLink() throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://refactoring.guru/refactoring/smells/couplers"));
    }
    public void WDLink() throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://refactoring.guru/refactoring/smells/dispensables"));
    }

    //A function that reads the selected item on our Item list and displays the corresponding classes details
    public void displaySelectedAnalysis(){

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        String s = UnitLists.getSelectionModel().getSelectedItem();
        SmellLabel.setText(s);
        barChart.getData().clear();

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

                Wd = (double) SmellDetectorCalls.getInstance().getDetections().get(cl).getWalkingDeadThreatLevel().ordinal();
                list.add(new PieChart.Data(SmellStrings[4],Wd));

                //smellItems.addAll(SmellStrings[0],SmellStrings[1],SmellStrings[2],SmellStrings[3], SmellStrings[4], );
                //smellItems.addAll(SmellStrings);
                updateMethodList(cl);
                resetProgressBar(cl);
                populateBarChart(cl);
            }
        }
        SortedList<String> sortedList = new SortedList(smellItems);
        SmellList.setItems(sortedList);
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
                        MethodsLabel.setText(cl.getNameAsString()+"'s Methods");
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
                    if(s.equals(SmellStrings[4])){
                        ThreatLevel.setProgress(Gc/4);
                        disableMethodList();
                        ThreatLabel.setText(cl.getNameAsString()+"::"+SmellStrings[4]);
                    }
                    if(s.equals(SmellStrings[5])){

                        resetProgressBar(cl);

                    }
                }
            }

   }
   private void populateBarChart(ClassOrInterfaceDeclaration cl){
            new CategoryAxis().setLabel("Smells Detected");
            new NumberAxis().setLabel("% of smells Detected");
            XYChart.Series series = new XYChart.Series<String,ThreatLevel>();
            series.setName(cl.getNameAsString()+"::Smells");
            double total = bloat+complexity+Ec+Gc;
            series.getData().add(new XYChart.Data<>(SmellStrings[0],(bloat/total)*100));
            series.getData().add(new XYChart.Data(SmellStrings[1],(complexity/total)*100));
            series.getData().add(new XYChart.Data(SmellStrings[2],(Ec/total)*100));
            series.getData().add(new XYChart.Data(SmellStrings[3],(Gc/total)*100));
            series.getData().add(new XYChart.Data(SmellStrings[4],(Wd/total)*100));
            barChart.getData().add(series);
        }
   public void resetProgressBar(ClassOrInterfaceDeclaration cl){
       ObservableList<AreaChart.Data> list = FXCollections.observableArrayList();
       ThreatLevel.setProgress((bloat/4 + complexity/4 + Gc/4 + Ec/4 + Wd/4)/5);

       disableMethodList();
       ThreatLabel.setText(cl.getNameAsString() + "::" + SmellStrings[5]);

   }
   public void getMethodSelectedBloat(){
            String s = SmellList.getSelectionModel().getSelectedItem();
            for(ClassOrInterfaceDeclaration cl:SmellDetectorCalls.getInstance().getDetections().keySet()){
                        for(MethodDeclaration m:SmellDetectorCalls.getInstance().getMethodThreats().keySet()){
                            if(m.getNameAsString().equals(s)){
                                ThreatLevel.setProgress(SmellDetectorCalls.getInstance().getMethodThreats().get(m).ordinal()/4);
                                System.out.println(cl.getNameAsString()+"::"+m.getNameAsString()+"::"+SmellStrings[0]);
                                ThreatLabel.setText(cl.getNameAsString()+"::"+m.getNameAsString()+"::"+SmellStrings[0]);
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
        this.items.clear();
        this.smellItems.clear();
        this.numberOfLines = 0;
        List<File> f = event.getDragboard().getFiles();
        File myFile = f.get(0);
        System.out.println(myFile.getName());
        FileHandler FileExplorer = new FileHandler();
        FileExplorer.getResults(myFile);
        for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet()){
            items.add(cl.getNameAsString());
            numberOfLines += (cl.getEnd().get().line - cl.getBegin().get().line - 1);
        }
        smellItems.addAll(SmellStrings);
        SmellList.setItems(smellItems);
        UnitLists.setItems(items);
        displayFinalResults();
    }
    public void saveFile() throws JSONException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", new String[]{"*.json"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        JSONReport report = new JSONReport();
        report.toFile(file);
    }

    public void togglePieChart(){
        pieChart.setVisible(!pieChart.isVisible());
        barChart.setVisible(!barChart.isVisible());

    }
}
