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
import java.util.NoSuchElementException;

/**
 * Class RocketMovement represents Observable that calculates movement parameters and updates Observers.
 *
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class RocketMovement implements Observable, Runnable {

    /**
     * Represents parameters of rocket movement.
     */
    private MovementParameters movement;
    /**
     * Represents Thread.
     */
    private Thread rocket;
    /**
     * Represents list of observers.
     */
    private volatile ArrayList<Observer> observers = new ArrayList<>();
    /**
     * Represents state of Thread.
     */
    private boolean isRunning;
    /**
     * Represents fuel usage in kilograms.
     */
    private double ut = 0; //[kg]
    /**
     * Represents current height of rocket in meters.
     */
    private double ht = 50000;//[m]
    /**
     * Represents current speed of rocket in meters per second.
     */
    private double vt = -150;//[m/s]
    /**
     * Represents mass of rocket without fuel in kilograms.
     */
    private double rocketsMass = 1000;//[kg]
    /**
     * Represents mass of fuel in kilograms.
     */
    private double fuelsMass = 1730.14;//[kg]
    /**
     * Represents whole mass of rocket and fuel in kilograms.
     */
    private double mt;//[kg]
    /**
     * Represents upper limit of fuel usage.
     */
    private double mi = -16.5;//[kg/s]

    /**
     * Gets current usage of fuel.
     *
     * @return current fuel usage.
     */
    public double getUt() {
        return ut;
    }

    /**
     * Sets usage of fuel.
     *
     * @param ut Fuel usage in current time.
     */
    public void setUt(double ut) {
        this.ut = ut;
    }

    /**
     * Calculates current position of rocket. Integrates equations.
     */
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

    /**
     * Adds observer to the list of Observers if the list does not contains this observer.
     *
     * @param observer Observer.
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    /**
     * Removes Observer from list of Observers, when it contains this Observer.
     *
     * @param observer Observer
     * @throws NoSuchElementException if there is no such observer in the list of Observers.
     */
    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) observers.remove(observer);
    }


    /**
     * Updates Observers with current movement parameters.
     */
    @Override
    public void notifyObservers() {
        Platform.runLater(() -> {
            for (Observer o : observers)
                o.update(movement);
        });
    }

    /**
     * Creates reference and starts Thread.
     */
    void start() {
        rocket = new Thread(this, "Rocket movement");
        rocket.start();
    }

    /**
     * Stops Thread.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Interrupts Thread.
     */
    void interrupt() {
        isRunning = false;
        rocket.interrupt();
    }

    /**
     * Calculates movement parameters when the Thread is started.
     */
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
