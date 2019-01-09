package sample;

import javafx.application.Platform;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.ode.ExpandableStatefulODE;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math3.ode.nonstiff.MidpointIntegrator;
import org.apache.commons.math3.ode.nonstiff.RungeKuttaIntegrator;

import java.util.ArrayList;

public class RocketMovement implements Observable, Runnable {

    private MovementParameters movement;
    private Thread rocket;
    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private boolean isRunning;
    private double ut = 0; //prędkość zmiany paliwa,zmienna przez użytkownika
    private double ht = 50000;//[m]
    private double vt = -150;//[m/s]
    private double rocketsMass = 1000;//[kg]
    private double fuelsMass = 1730.14;//[kg]
    private double mt;//[kg]
    private double mi = -16.5;//[kg/s]
    private double lastSpeed = 0;


    public double getUt() {
        return ut;
    }

    public void setUt(double ut) {
        this.ut = ut;
    }


    private void getPosition() {
        //u(t) is the speed of fuel's usage and it can change
        //height is the speed of height's changing
        //rockets speed is the speed of rocket's speed changing
        //massChange is the speed of mass changing
        mt = rocketsMass + fuelsMass;
        FirstOrderDifferentialEquations ode;
        if (ut <= 0 && ut >= mi)
            ode = new ODE(ut);
        else ode = new ODE(mt);
        //Thanks to Apache Commons Math the differential equations could be easily solved with Euler integration method
        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.1);
        PathODE path = new PathODE();
        integrator.addStepHandler(path);
        double[] yStart = new double[]{ht, vt, mt};
        double[] yStop = new double[]{1, 1, 1};
        integrator.integrate(ode, 0, yStart, 1, yStop);

        ht = path.getH();
        vt = path.getV();
        mt = path.getM();

        movement = new MovementParameters();

        movement.setHeight(path.getH());
        movement.setSpeed(path.getV());
        movement.setMass(path.getM());


    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Platform.runLater(() -> {
            for (Observer o : observers)
                o.update(movement);
        });
    }

    void start() {
        rocket = new Thread(this, "Rocket movement");
        rocket.start();
    }

    public void stop() {
        isRunning = false;
    }


    void interrupt() {
        isRunning = false;
        rocket.interrupt();
    }

    @Override
    public void run() {
        isRunning = true;
        movement = new MovementParameters();
        movement.setHeight(ht);
        movement.setSpeed(vt);
        movement.setMass(ut);
        notifyObservers();
        while (isRunning) {
            try {
                fuelsMass += ut; //Calculates current fuel's mass
                if (fuelsMass < 0) fuelsMass = 0;
                if (movement.getHeight() == 0) {
                    interrupt(); //If the rocket has landed then we're finished
                }
                getPosition();
                notifyObservers();
                rocket.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }

        }
    }
}
