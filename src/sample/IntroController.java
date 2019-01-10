package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class IntroController represents GUI control methods of IntroWindow.
 *
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class IntroController {

    //Controller of IntroWindow.
    /**
     * Represents Label with heading of rules.
     */
    @FXML
    private Label instructionTxt;

    /**
     * Represents buttons confirming the understanding of the rules.
     */
    @FXML
    private Button okBtn;

    /**
     * Represents Label with command to choose player.
     */
    @FXML
    private Label playerTxt;

    /**
     * Represents button that launches game window.
     */
    @FXML
    private Button playBtn;

    /**
     * Represents ImageView with Image of player one.
     */
    @FXML
    private ImageView klaudiaImage;

    /**
     * Represents ImageView with player two.
     */
    @FXML
    private ImageView mirekImage;

    /**
     * Represents Pane under ImageView with player two.
     */
    @FXML
    private Pane mirekPane;

    /**
     * Represents Pane under ImageView with player one.
     */
    @FXML
    private Pane klaudiaPane;

    /**
     * Represents label with rule one of the game.
     */
    @FXML
    private Label rule1;

    /**
     * Represents label with rule two of the game.
     */
    @FXML
    private Label rule2;

    /**
     * Represents label with rule three of the game.
     */
    @FXML
    private Label rule3;

    /**
     * Represents label with rule four of the game.
     */
    @FXML
    private Label rule4;

    /**
     * Represents player that user have chosen.
     */
    protected static String player = "m";

    /**
     * Initialize application after launching.
     */
    public void initialize() {

        //Initialize method - hides some of GUI elements
        playerTxt.setVisible(false);
        playBtn.setDisable(true);
        playBtn.setVisible(false);
        klaudiaImage.setVisible(false);
        mirekImage.setVisible(false);
        mirekPane.setVisible(false);
        klaudiaPane.setVisible(false);

    }

    /**
     * Method called when 'understood' is clicked. Shows "choose your fighter" menu.
     *
     * @param event okBtn clicked.
     */
    @FXML
    void okClicked(ActionEvent event) {
        //Method after button "understood" is clicked. Hides elements of GUI with instructions and itself.
        // Shows "choose your fighter" menu.

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
        rule1.setVisible(false);
        rule2.setVisible(false);
        rule3.setVisible(false);
        rule4.setVisible(false);


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

    /**
     *Method called after "Play" cliked. Opens main game window.
     *
     * @param event playBtn cliked.
     * @throws IOException Input/output exception.
     */
    @FXML
    void playClicked(ActionEvent event) throws IOException {
        //Method after "play" button clicked.
        //Creates new window with game.
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/sample.fxml"));
        primaryStage.setTitle("Space Landing");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getClassLoader().getResource("sample/Stylesheet.css").toExternalForm());
        primaryStage.show();
        primaryStage.setResizable(false);

        Stage stage = (Stage) playBtn.getScene().getWindow();
        stage.close();

    }


}
