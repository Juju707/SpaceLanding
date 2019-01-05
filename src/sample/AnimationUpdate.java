package sample;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


public class AnimationUpdate implements Observer, Display {


    private double xc = 200;
    private double yc = 10; //start
    private double yend = 400;
    private Circle circle = new Circle(); //to statek
    private Image image=new Image ("file:Rocket100.png");
    private double pos = 0; //obliczanie pozycji statku

    public void prepare(Pane pane) {

        //start i stop
        Circle end = new Circle();
        end.setCenterX(200);
        end.setCenterY(yend);
        end.setRadius(5);
        end.setStrokeWidth(5);

        Circle start = new Circle();
        start.setCenterX(xc);
        start.setCenterY(yc);
        start.setRadius(5);
        start.setStrokeWidth(5);

        //statek
        circle.setCenterX(xc);
        circle.setCenterY(yc);
        circle.setRadius(5.0);
        circle.setStrokeWidth(5);
        //circle.setFill(new ImagePattern(image));
        pane.getChildren().addAll(circle, end, start);


    }

    public void removeAll(Pane pane){


        pane.getChildren().removeAll(pane.getChildren());


    }

    @Override
    public void update(MovementParameters movementParameters) {

        Platform.runLater(() -> {
            pos = (movementParameters.getHeight() / 38.46); //to 12.82 jest skad ze 5000 czyli wysokosc na 390 czyli droga do przejscia

        });

        display();
    }

    @Override
    public void display() {
        //wyslanie statku na odpowiednia pozycje
        circle.setCenterX(xc);
        circle.setCenterY(yend - pos);
    }
}
