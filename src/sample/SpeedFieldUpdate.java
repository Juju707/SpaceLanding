package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;


public class SpeedFieldUpdate implements Observer, Display {

    private double speed=0;
    private TextField speedField;

    public SpeedFieldUpdate(TextField speedField) {
        this.speedField = speedField;
    }

    @Override
    public void display() {
        String result = String.format("%.2f", speed);
        speedField.setText(result);
    }

    @Override
    public void update(MovementParameters movementParameters) {
        Platform.runLater(() -> {
           speed=movementParameters.getSpeed();
                   //(Math.round( );
        });

        display();

    }
}
