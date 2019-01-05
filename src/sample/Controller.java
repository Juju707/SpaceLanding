package sample;

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
    private boolean run=false;

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


    public void initialize() {

        //nie sa elementami cyklu focusowania jak przesuwam strzałkami
        FuelUsageField.setFocusTraversable(false);
        btnStart.setFocusTraversable(false);
        speedField.setFocusTraversable(false);
        chart.setFocusTraversable(false);
        FuelLeftField.setFocusTraversable(false);

        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));


        //wylaczam przyciski zeby ich uzytkownik nie kliknął bo bedzie błąd
        btnUp.setDisable(true);
        btnDown.setDisable(true);

    }

    @FXML
    void startClicked(ActionEvent event) {
        bestScore.setText(String.valueOf(readScore("src\\sample\\score.txt")));
        FuelLeftField.setText(String.valueOf(1730.14));
        //jesli juz bylo uruchamiane kiedys to najpierw zamkniecie poprzedniego watku
        if(run){
            rocketMovement.interrupt();

            run=false;
        }

        rocketMovement=new RocketMovement();
        rocketMovement.setUt(0); //na poczatku tylko sobie spada

        rocketMovement.start();
        run=true;

        chart.getData().removeAll(chart.getData());
        ChartUpdate chartUpdate=new ChartUpdate(chart);

        AnimationUpdate au=new AnimationUpdate();
        au.removeAll(animationPane);
        au.prepare(animationPane, bestScore, scoreLabel);

        SpeedFuelUpdate sfu=new SpeedFuelUpdate(speedField, FuelLeftField);

        rocketMovement.addObserver(chartUpdate);
        rocketMovement.addObserver(au);
        rocketMovement.addObserver(sfu);

        FuelUsageField.setText(String.valueOf(rocketMovement.getUt()));

        //wlaczam mozliwosc sterowania paliwem
        btnUp.setDisable(false);
        btnDown.setDisable(false);

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
    }

    @FXML
    void downClicked(ActionEvent event) {
        lessFuel();
    }

    private void moreFuel(){
        rocketMovement.setUt(rocketMovement.getUt() - 1);
        if (rocketMovement.getUt() < -16.5)
            rocketMovement.setUt(-16.5);
        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }

    private void lessFuel(){
        rocketMovement.setUt(rocketMovement.getUt()+1);

        if(rocketMovement.getUt()>0)
            rocketMovement.setUt(0);

        System.out.println(rocketMovement.getUt());
        FuelUsageField.setText(String.valueOf(Math.abs(rocketMovement.getUt())));
    }

    private void saveScore(String name, double score){

        //utworzenie obiektu PrintWriter
        PrintWriter save;
        try { //otoczony blokiem try i catch zapis do pliku
            save = new PrintWriter(name); //przypisanie referencji do obiektu
                save.write(String.valueOf(score));
            save.close(); //zamniecie zapisu

        } catch (FileNotFoundException e1) { //obsługa wyjątków
            e1.printStackTrace();
        }
    }

    private double readScore(String name){
        double result=0;
        try { //otoczony blokiem try i catch zapis do pliku
            File file =
                    new File(name);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                result= Double.valueOf(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

}
