package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class IntroWindow crates Stage and launches intro window of application.
 *
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */

public class IntroWindow extends Application {

    //Window opened when the application starts.
    /**
     * Sets scene name and size and shows scene.
     *
     * @param stage Stage.
     * @throws Exception Exception.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("intro.fxml"));
        stage.setTitle("Space Landing");
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
        stage.show();
        stage.setResizable(false);
    }

    /**
     *Launches the application.
     *
     * @param args Initial arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
