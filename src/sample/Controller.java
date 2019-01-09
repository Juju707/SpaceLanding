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

public class Controller {

    private RocketMovement rocketMovement;
    private boolean run = false;

    @FXML
    private ScatterChart<Number, Number> chart;

    @FXML
    private Button btnUp;

    @FXML
    private Button btnDown;

    @FXML
    private Button btnStart;

    @FXML
    private Pane animationPane;

    @FXML
    private TextField FuelUsageField;

    @FXML
    private TextField speedField;

    @FXML
    private TextField FuelLeftField;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label bestScore;

    @FXML
    private Label endGameLabel;

    @FXML
    private TextField heightField;



    public void initialize() {
        //Initializes GUI elemtns.

        //nie sa elementami cyklu focusowania jak przesuwam strzałkami
        FuelUsageField.setFocusTraversable(false);
        heightField.setFocusTraversable(false);
        btnStart.setFocusTraversable(false);
        speedField.setFocusTraversable(false);
        chart.setFocusTraversable(false);
        FuelLeftField.setFocusTraversable(false);
        endGameLabel.setVisible(false);

        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));


        //wylaczam przyciski zeby ich uzytkownik nie kliknął bo bedzie błąd
        btnUp.setDisable(true);
        btnDown.setDisable(true);

    }

    @FXML
    void startClicked(ActionEvent event) {
        //When the game starts. Creates Thread and adds Observers. Kills previus Thread if existed.
        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));
        FuelLeftField.setText(String.valueOf(1730.14));
        //jesli juz bylo uruchamiane kiedys to najpierw zamkniecie poprzedniego watku
        if (run) {
            rocketMovement.interrupt();

            run = false;
        }

        rocketMovement = new RocketMovement();
        rocketMovement.setUt(0); //na poczatku tylko sobie spada

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

        //wlaczam mozliwosc sterowania paliwem
        btnUp.setDisable(false);
        btnDown.setDisable(false);

        //Listens if arrows up or down are clicked.
        //key listereny do strzałek połączone z przyciskami
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

    @FXML
    void upClicked(ActionEvent event) {
        moreFuel();
    } //method when button clicked.

    @FXML
    void downClicked(ActionEvent event) {
        lessFuel();
    } //method when button clicked.

    private void moreFuel() {
        //Increases fuel usage.
        rocketMovement.setUt(rocketMovement.getUt() - 1);
        if (rocketMovement.getUt() < -16.5)
            rocketMovement.setUt(-16.5);
        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }

    private void lessFuel() {
        //Decreases fuel usage.
        rocketMovement.setUt(rocketMovement.getUt() + 1);

        if (rocketMovement.getUt() > 0)
            rocketMovement.setUt(0);

        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }


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
