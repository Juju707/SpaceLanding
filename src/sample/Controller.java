package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controller {

    private RocketMovement rocketMovement;

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


    public void initialize() {

    }

    @FXML
    void startClicked(ActionEvent event) {
        chart.getData().removeAll(chart.getData());
        rocketMovement=new RocketMovement();
        rocketMovement.setUt(0); //na poczatku tylko sobie spada
        rocketMovement.start();
        ChartUpdate chartUpdate=new ChartUpdate(chart);
        rocketMovement.addObserver(chartUpdate);
        AnimationUpdate au=new AnimationUpdate();
        au.prepare(animationPane);
        rocketMovement.addObserver(au);


    }

    @FXML
    void upClicked(ActionEvent event) {

        rocketMovement.setUt(rocketMovement.getUt()+1);

        if(rocketMovement.getUt()>0)
            rocketMovement.setUt(0);

        System.out.println(rocketMovement.getUt());

    }

    @FXML
    void downClicked(ActionEvent event) {


            rocketMovement.setUt(rocketMovement.getUt() - 1);
            System.out.println(rocketMovement.getUt());
            if (rocketMovement.getUt() < -16.5)
                rocketMovement.setUt(-16.5);


    }

}
