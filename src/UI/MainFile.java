package UI;

import Project_FileAnalyser.SmellDetectorCalls;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainFile extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("src/UI/baseScene.fxml")));

        SmellDetectorCalls smells = SmellDetectorCalls.init();
        // Load the FXML document
        primaryStage.setTitle("I smell Pennies");
        Scene scene =  new Scene(root,1300,800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("src/UI/baseScene.css");
        primaryStage.show();
        primaryStage.setResizable(false);

    }
}

