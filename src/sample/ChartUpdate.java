package sample;

import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
/**
 * Class ChartUpdate represents updating GUI visualisation methods.
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class ChartUpdate implements Observer, Display {
    /**
     * Represents Scatter Chart with visual representation of rocket's movement.
     */
    private ScatterChart<Number, Number> chart;
    /**
     * Represents data series containing rocket's position parameters.
     */
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();

    /**
     * Constructor for this class.
     * @param chart
     */
    public ChartUpdate(ScatterChart<Number, Number> chart) {
        this.chart = chart;
    }

    /**
     *Method responsible for updating rocket's position.
     * @param movementParameters
     */
    @Override
    public void update(MovementParameters movementParameters) {

        //updates series with new data.
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(movementParameters.getSpeed(), movementParameters.getHeight()));
        });

        display();
    }

    /**
     * Method displaying updated parameters at chart.
     */
    @Override
    public void display() {
        //displays new data on chart.
        chart.setAnimated(false);
        chart.getData().removeAll(chart.getData());
        chart.getData().addAll(series);
    }
}
