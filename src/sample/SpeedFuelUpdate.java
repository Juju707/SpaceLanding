package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;


public class SpeedFuelUpdate implements Observer, Display {

    private double speed = 0;
    private double height1=0;
    private TextField speedField;
    private TextField fuelLeftField;
    private double massLeft;
    private TextField fuelUsage;
    private TextField height;

    public SpeedFuelUpdate(TextField speedField, TextField fuelLeftField, TextField fuelUsage, TextField height) {
        this.speedField = speedField;
        this.fuelLeftField = fuelLeftField;
        this.fuelUsage = fuelUsage;
        this.height=height;
    }

    @Override
    public void display() {
        //Displaying current speed and fuel's mass left in text fields
        String result1 = String.format("%.2f", speed);
        String result2 = String.format("%.2f", massLeft);
        String result3 = String.format("%.2f", height1);
        speedField.setText(result1);
        fuelLeftField.setText(result2);
        height.setText(result3);
    }

    @Override
    public void update(MovementParameters movementParameters) {
        //Updating the current data
        Platform.runLater(() -> {
            speed = movementParameters.getSpeed();
            height1=movementParameters.getHeight();

            if (movementParameters.getMass() <= 0) massLeft = 1730.14;
            else massLeft = movementParameters.getMass() - 1000;

            if (movementParameters.getMass() == 1000) fuelUsage.setText("0.0");

        });

        display();

    }
}
