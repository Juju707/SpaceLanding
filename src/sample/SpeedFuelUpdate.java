package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 * Class SpeedFuelUpdate represents class that updates TextFields with current movement parameters. Implements Observer and Display interface.
 *
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */

public class SpeedFuelUpdate implements Observer, Display {

    /**
     * Represents variable with current speed of rocket.
     */
    private double speed = 0;
    /**
     * Represents variable with current height of rocket
     */
    private double height1=0;
    /**
     * Represents TextField with current speed of rocket.
     */
    private TextField speedField;
    /**
     * Represents TextField with current state of fuel.
     */
    private TextField fuelLeftField;
    /**
     * Represent variable with mass of fuel that left.
     */
    private double massLeft;
    /**
     * Represents TextField with current fuel usage.
     */
    private TextField fuelUsage;
    /**
     * Represents TextField with current height.
     */
    private TextField height;

    /**
     * Creates object with given parameters.
     *
     * @param speedField TextField that displays current speed of rocket.
     * @param fuelLeftField TextField that displays amount of fuel left.
     * @param fuelUsage TextField that displays current usage of fuel.
     * @param height TextField that displays current height of rocket.
     */
    public SpeedFuelUpdate(TextField speedField, TextField fuelLeftField, TextField fuelUsage, TextField height) {
        this.speedField = speedField;
        this.fuelLeftField = fuelLeftField;
        this.fuelUsage = fuelUsage;
        this.height=height;
    }

    /**
     * Displays in TextFields values of current movement parameters.
     */
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

    /**
     * Updates variables with current movement parameters and calls method display.
     *
     * @param movementParameters Updated movement parameters.
     */
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
