package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SpeedFuelUpdate implements Observer, Display {

    private double speed=0;
    private TextField speedField;
    private TextField fuelLeftField;
    private double massLeft;
    private  TextField fuelUsage;

    public SpeedFuelUpdate(TextField speedField, TextField fuelLeftField, TextField fuelUsage) {
        this.speedField = speedField;
        this.fuelLeftField=fuelLeftField;
        this.fuelUsage=fuelUsage;
    }

    @Override
    public void display() {
        String result1 = String.format("%.2f", speed);
        String result2=String.format("%.2f", massLeft);
        speedField.setText(result1);
        fuelLeftField.setText(result2);
    }

    @Override
    public void update(MovementParameters movementParameters) {
        Platform.runLater(() -> {
           speed=movementParameters.getSpeed();

           if(movementParameters.getMass()<=0) massLeft=1730.14;
           else massLeft=movementParameters.getMass()-1000;

           if(movementParameters.getMass()==1000) fuelUsage.setText("0.0");

        });

        display();

    }
}
