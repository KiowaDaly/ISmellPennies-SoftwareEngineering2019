package UI;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class JavaFXStage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =  FXMLLoader.load( this.getClass().getClassLoader()
                .getResource("src/UI/baseScene.fxml"));


        // Load the FXML document
        primaryStage.setTitle("I smell Pennies");
        Scene scene =  new Scene(root,1000,600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("src/UI/baseScene.css");
        primaryStage.show();

    }
}

