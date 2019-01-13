package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

/**
 * Class AnimationUpdate represents GUI updating methods.
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class AnimationUpdate implements Observer, Display {
    /**
     * Object representing rocket's image path.
     */
    File rocketFile;
    /**
     *Object representing rocket's image visualisation.
     */
    ImageView rocket = new ImageView();
    /**
     *Attribute representing rocket's x start position.
     */
    private double xc = 390;
    /**
     *Attribute representing rocket's y start position.
     */
    private double yc = 10; //start
    /**
     *Attribute representing rocket's y end position.
     */
    private double yend = 400;
    /**
     *Attribute representing rocket's current y position.
     */
    private double pos = 0; //obliczanie pozycji statku
    /**
     *Attribute representing rocket's position in reality.
     */
    private double startHeight = 50000;
    /**
     *Attribute representing rocket's path.
     */
    private double path = yend - yc;
    /**
     *Attribute representing rocket's height adjustment factor.
     */
    private double factor = startHeight / path;
    /**
     *Attribute representing counter for position validation.
     */
    private int counter;
    /**
     *Attribute representing Controller for intro window.
     */
    private IntroController i = new IntroController();
    /**
     *Method responsible for rocket's start preparation.
     * @param label1
     * @param label2
     * @param pane
     */
    public void prepare(Pane pane, Label label1, Label label2) {
        counter = 0;
        //Choosing proper rocket image
        if (i.player.equals("k"))
            rocketFile = new File("src/sample/resources/Klaudia.png"); //tu bedzie zmiana przy wyborze gracza
        else rocketFile = new File("src/sample/resources/Mirek.png");

        if (rocketFile.exists()) {
            Image diverImage = new Image(rocketFile.toURI().toString());
            rocket.setImage(diverImage);
        }
        //Setting rocket's postion on the start
        rocket.setX(xc);
        rocket.setY(yend - startHeight / factor);
        //Adding all of the elements
        pane.getChildren().addAll(rocket, label1, label2);


    }
    /**
     *Method responsible for removing all data from pane.
     */
    public void removeAll(Pane pane) {
        //Remove previous rocket and everything associated with it
        pane.getChildren().removeAll(pane.getChildren());

    }
    /**
     *Method responsible for updating rocket's position.
     * @param movementParameters
     */
    @Override
    public void update(MovementParameters movementParameters) {
        //Update rocket's current position
        Platform.runLater(() -> {
            pos = (movementParameters.getHeight() / factor);
            counter++;
        });

        display();
    }
    /**
     * Method displaying updated parameters at chart.
     */
    @Override
    public void display() {
        //Displaying rocket's current position
        if (counter > 0) {
            rocket.setX(xc);
            rocket.setY(yend - pos);
        }
    }
}
