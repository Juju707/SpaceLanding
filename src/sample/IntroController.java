package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class IntroController {

    @FXML
    private Label instructionTxt;

    @FXML
    private Button okBtn;

    @FXML
    private Label playerTxt;

    @FXML
    private Button playBtn;

    @FXML
    private ImageView klaudiaImage;

    @FXML
    private ImageView mirekImage;

    @FXML
    private Pane mirekPane;

    @FXML
    private Pane klaudiaPane;

    protected static String player = null;


    public void initialize() {

        playerTxt.setVisible(false);
        playBtn.setDisable(true);
        playBtn.setVisible(false);
        klaudiaImage.setVisible(false);
        mirekImage.setVisible(false);
        mirekPane.setVisible(false);
        klaudiaPane.setVisible(false);

    }

    @FXML
    void okClicked(ActionEvent event) {

        playerTxt.setVisible(true);
        playBtn.setDisable(false);
        playBtn.setVisible(true);
        instructionTxt.setVisible(false);
        okBtn.setVisible(false);
        okBtn.setDisable(true);
        klaudiaImage.setVisible(true);
        mirekImage.setVisible(true);
        mirekPane.setVisible(true);
        klaudiaPane.setVisible(true);


        klaudiaImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                player = "k";
                System.out.println(player);
            }
        });

        mirekImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                player = "m";
                System.out.println(player);

            }
        });


    }

    @FXML
    void playClicked(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Space Landing");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
        primaryStage.show();
        primaryStage.setResizable(false);

        Stage stage = (Stage) playBtn.getScene().getWindow();
        stage.close();

    }


}
