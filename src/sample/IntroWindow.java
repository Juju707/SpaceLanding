package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IntroWindow extends Application {

    //Window opened when the application starts.
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("intro.fxml"));
        stage.setTitle("Space Landing");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
