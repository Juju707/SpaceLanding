package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;


public class AnimationUpdate implements Observer, Display {

    File rocketFile;
    ImageView rocket = new ImageView();
    private double xc = 390;
    private double yc = 10; //start
    private double yend = 400;
    private double pos = 0; //obliczanie pozycji statku
    private double startHeight=15000;
    private double path=yend-yc;
    private double factor=startHeight/path;
    private int counter; //pierwszy pomiar jaki daje mi thread jest jakis lewy wiec wywalam go w ten sposob, ze mam licznik


    public void prepare(Pane pane, Label label1, Label label2) {
        counter=0;
        rocketFile = new File("src\\sample\\Klaudia.png"); //tu bedzie zmiana przy wyborze gracza
        if(rocketFile.exists())
        {
            System.out.println("co jest");
            Image diverImage = new Image(rocketFile.toURI().toString());
            rocket.setImage(diverImage);
        }

        rocket.setX(xc);
        rocket.setY(yend-startHeight/factor);

        pane.getChildren().addAll(rocket, label1, label2);


    }

    public void removeAll(Pane pane){

        pane.getChildren().removeAll(pane.getChildren());

    }

    @Override
    public void update(MovementParameters movementParameters) {

        Platform.runLater(() -> {

            pos = (movementParameters.getHeight() / factor); //to 12.82 jest skad ze 5000 czyli wysokosc na 390 czyli droga do przejscia
            counter++;
        });

        display();
    }

    @Override
    public void display() {
        //wyslanie statku na odpowiednia pozycje
        if(counter>0) {
            rocket.setX(xc);
            rocket.setY(yend - pos);
        }
    }
}
