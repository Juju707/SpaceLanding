package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class endGame implements Observer, Display {

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
            fuelUsed = Math.round(fuelUsed * 100) / 100;

            if (((speed < 0 && speed > -2) || (speed > 0 && speed < 2)) && height == 0) {
                String fuel = String.format("%.2f", fuelUsed);

                if (fuelUsed < bestScore) {
                    fuel = fuel.replace('-', ' ');
                    System.out.println(fuel);
                    label.setText("New  best  score  " + fuel);
                    saveScore("src\\sample\\score.txt", fuel);
                    bestScoreLabel.setText(fuel);
                } else {
                    label.setText("Score  " + fuel);
                }

                pane.getChildren().add(label);
                label.setVisible(true);


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
            speed = movementParameters.getSpeed();
            height = movementParameters.getHeight();
            counter++;
            if (movementParameters.getMass() <= 0) fuelUsed = 0;
            else fuelUsed = 2730.14 - (movementParameters.getMass());

        });
        display();
    }

    private void saveScore(String name, String score) {

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
