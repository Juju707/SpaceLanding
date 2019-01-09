package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class endGame implements Observer, Display {

    //Class that updates labels and score.txt after the game ended.
    private Label label;
    private double speed;
    private double fuelUsed;
    private Pane pane;
    private double bestScore;
    private double height;
    private int counter = 0;
    private Label bestScoreLabel;

    public endGame(Label label, Pane pane, double bestScore, Label bestScoreLabel) {
        this.label = label;
        this.pane = pane;
        this.bestScoreLabel = bestScoreLabel;
        this.bestScore = bestScore;
    }

    @Override
    public void display() {
        if (counter > 0) {
            //Calculates fuel usage.
            fuelUsed = Math.round(fuelUsed * 100) / 100;

            //Checks if rocket landed properly
            if (((speed < 0 && speed > -2) || (speed > 0 && speed < 2)) && height == 0) {
                String fuel = String.format("%.2f", fuelUsed);

                //Checks if score is better that current best score.
                //If it is save new best score to file and displays it.
                if (fuelUsed < bestScore) {
                    fuel = fuel.replace('-', ' ');
                    System.out.println(fuel);
                    label.setText("New  best  score  " + fuel);
                    saveScore("src\\sample\\score.txt", fuel);
                    bestScoreLabel.setText(fuel);
                } else {
                    //If not shows only current score.
                    label.setText("Score  " + fuel);
                }


                pane.getChildren().add(label);
                label.setVisible(true);

                //If rocket did not land properly shows "game over" label
            } else if (height == 0 && (!(speed < 0 && speed > -2) || !(speed > 0 && speed < 2))) {
                label.setText("Game over");
                pane.getChildren().add(label);
                label.setVisible(true);
            } else {
                label.setText("");
                pane.getChildren().remove(label);
            }
        }
    }

    @Override
    public void update(MovementParameters movementParameters) {
        Platform.runLater(() -> {
            //updates parameters with Thread
            speed = movementParameters.getSpeed();
            height = movementParameters.getHeight();
            counter++;
            if (movementParameters.getMass() <= 0) fuelUsed = 0;
            else fuelUsed = 2730.14 - (movementParameters.getMass());

        });
        display();
    }

    private void saveScore(String name, String score) {
        //Saves to file.

        //utworzenie obiektu PrintWriter
        PrintWriter save;
        try { //otoczony blokiem try i catch zapis do pliku
            save = new PrintWriter(name); //przypisanie referencji do obiektu
            String toSave = score.replace(',', '.');
            save.write(toSave);
            save.close(); //zamniecie zapisu
        } catch (FileNotFoundException e1) { //obsługa wyjątków
            e1.printStackTrace();
        }
    }
}
