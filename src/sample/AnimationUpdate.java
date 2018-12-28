package sample;

import com.sun.javafx.geom.Rectangle;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationUpdate implements Observer, Display{


    private Path path;//=new Path();
    double xc = 200;//+Math.sin(x.get(0));
    double yc = 250;//+Math.cos(x.get(0));
    double r = 150f;
    Circle circle = new Circle();

    void prepare(){

        //srodek okregu po ktorym porusza sie wahadło
        double xc = 200;//+Math.sin(x.get(0));
        double yc = 250;//+Math.cos(x.get(0));
        double r = 150f;

        //utworzenie kulki wahadła

        circle.setCenterX(xc + r);
        circle.setCenterY(yc);
        circle.setRadius(5.0);
        circle.setStrokeWidth(5);
        circle.setFill(Color.MAGENTA);
        path=new Path();
        path.getElements().add(new MoveTo(xc , yc));

        //tworze nowe okno
        Stage stage = new Stage();
        Group root = new Group(circle);

        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("Pendulum Animation");
        stage.show();


    }

    @Override
    public void update(MovementParameters movementParameters) {

        Platform.runLater(() -> {
            path.getElements().add(new LineTo(xc, yc+movementParameters.getHeight()));
        });

        display();
    }

    @Override
    public void display() {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(circle);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        //uruchomienie animacji kulki i linii
        pathTransition.play();
    }
}
