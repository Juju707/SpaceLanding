package sample;

import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class ChartUpdate implements  Observer, Display{

    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();

    public ChartUpdate(ScatterChart<Number, Number> chart) {
        this.chart = chart;
    }

    @Override
    public void update(MovementParameters movementParameters) {
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(movementParameters.getSpeed(), movementParameters.getHeight()));
        });

        display();
    }

    @Override
    public void display() {
        chart.setAnimated(false);
        chart.getData().removeAll(chart.getData());
        chart.getData().addAll(series);
    }
}
