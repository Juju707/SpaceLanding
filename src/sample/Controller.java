package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;

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
        au.prepare();
        rocketMovement.addObserver(au);

    }

    @FXML
    void upClicked(ActionEvent event) {

        rocketMovement.setUt(rocketMovement.getUt()+10);

        if(rocketMovement.getUt()>0)
            rocketMovement.setUt(0);

        System.out.println(rocketMovement.getUt());

    }

    @FXML
    void downClicked(ActionEvent event) {
        rocketMovement.setUt(rocketMovement.getUt()-10);

        if(rocketMovement.getUt()<-16.5)
            rocketMovement.setUt(-16.5);

    }

}
