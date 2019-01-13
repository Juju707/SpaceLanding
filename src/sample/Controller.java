package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.*;
import java.util.Scanner;

/**
 * Class Controller represents GUI control methods of Main Window.
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class Controller {
    /**
     * Attribute representing rocket's movement thread.
     */
    private RocketMovement rocketMovement;
    /**
     * Attribute representing game's actual status of running.
     */
    private boolean run = false;
    /**
     * Represents Scatter Chart with visual representation of rocket's movement .
     */
    @FXML
    private ScatterChart<Number, Number> chart;
    /**
     * Represents Button responsible for adding more fuel.
     */
    @FXML
    private Button btnUp;
    /**
     * Represents Button responsible for reducing the amount of fuel.
     */
    @FXML
    private Button btnDown;
    /**
     * Represents Button responsible for starting the game.
     */
    @FXML
    private Button btnStart;
    /**
     * Represents Pane containing the main scene of game.
     */
    @FXML
    private Pane animationPane;
    /**
     * Represents Text Field with information about current fuel's usage.
     */
    @FXML
    private TextField FuelUsageField;
    /**
     * Represents Text Field with information about rocket's current speed.
     */
    @FXML
    private TextField speedField;
    /**
     * Represents Text Field with information about current fuel's capacity.
     */
    @FXML
    private TextField FuelLeftField;
    /**
     * Represents Label with information about setting new best score.
     */
    @FXML
    private Label scoreLabel;
    /**
     * Represents Label with information about best score.
     */
    @FXML
    private Label bestScore;
    /**
     * Represents Label with information about lost game.
     */
    @FXML
    private Label endGameLabel;
    /**
    * Represents Text Field with information about rocket's current height.
    */
    @FXML
    private TextField heightField;


    /**
     * Initialize application after launching.
     */
    public void initialize() {
        //Initializes GUI elements.
        FuelUsageField.setFocusTraversable(false);
        heightField.setFocusTraversable(false);
        btnStart.setFocusTraversable(false);
        speedField.setFocusTraversable(false);
        chart.setFocusTraversable(false);
        FuelLeftField.setFocusTraversable(false);
        endGameLabel.setVisible(false);
        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));
        btnUp.setDisable(true);
        btnDown.setDisable(true); }

    /**
     * Method called when 'start' is clicked.
     * Starts the game.
     * @param event
     */
    @FXML
    void startClicked(ActionEvent event) {
        //When the game starts. Creates Thread and adds Observers. Kills previus Thread if existed.
        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));
        FuelLeftField.setText(String.valueOf(1730.14));
        if (run) {
            rocketMovement.interrupt();

            run = false;
        }

        rocketMovement = new RocketMovement();
        rocketMovement.setUt(0);
        rocketMovement.start();
        run = true;

        chart.getData().removeAll(chart.getData());
        ChartUpdate chartUpdate = new ChartUpdate(chart);

        AnimationUpdate au = new AnimationUpdate();
        au.removeAll(animationPane);
        au.prepare(animationPane, bestScore, scoreLabel);

        SpeedFuelUpdate sfu = new SpeedFuelUpdate(speedField, FuelLeftField, FuelUsageField, heightField);

        endGame endGame = new endGame(endGameLabel, animationPane, readScore("src\\sample\\score.txt"), bestScore);

        rocketMovement.addObserver(chartUpdate);
        rocketMovement.addObserver(au);
        rocketMovement.addObserver(sfu);
        rocketMovement.addObserver(endGame);

        FuelUsageField.setText(String.valueOf(rocketMovement.getUt()));
        btnUp.setDisable(false);
        btnDown.setDisable(false);

        //Listeners if arrows up or down are clicked.
        btnUp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    moreFuel();
                }
            }
        });

        btnDown.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN) {
                    lessFuel();
                }
            }
        });

    }
    /**
     * Method called when 'up' is clicked.
     * Adds more fuel.
     * @param event
     */
    @FXML
    void upClicked(ActionEvent event) {
        moreFuel();
    } //method when button clicked.
    /**
     * Method called when 'down' is clicked.
     * Reduces the amount of fuel.
     * @param event
     */
    @FXML
    void downClicked(ActionEvent event) {
        lessFuel();
    } //method when button clicked.
    /**
     * Method called for rising the amount of used fuel.
     * Changes parameter Ut in rocket's parameters which are linked with integrating class.
     */
    private void moreFuel() {
        //Increases fuel usage.
        rocketMovement.setUt(rocketMovement.getUt() - 1);
        if (rocketMovement.getUt() < -16.5)
            rocketMovement.setUt(-16.5);
        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }

    /**\
     * Method called for reducing the amount of used fuel.
     * Changes parameter Ut in rocket's parameters which are linked with integrating class.
     */
    private void lessFuel() {
        //Decreases fuel usage.
        rocketMovement.setUt(rocketMovement.getUt() + 1);

        if (rocketMovement.getUt() > 0)
            rocketMovement.setUt(0);

        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }

    /**\
     * Method called for reading best score.
     * Reads the best score (the amount of used fuel) form file.
     * @param name
     */
    private double readScore(String name) {
        //Reads current best score from file.
        double result = 0;
        try { //otoczony blokiem try i catch zapis do pliku
            File file =
                    new File(name);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                result = Double.valueOf(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }


}
