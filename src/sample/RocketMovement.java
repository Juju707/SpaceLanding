package sample;

import javafx.application.Platform;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

import java.util.ArrayList;

public class RocketMovement implements Observable,Runnable {

    private MovementParameters movement;
    private Thread rocket;
    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private boolean isRunning;
    private double ut=0; //prędkość zmiany paliwa,zmienna przez użytkownika
    private double ht = 15000;//[m]
    private double vt = -150;//[m/s]
    private double rocketsMass = 1000;//[kg]
    private double fuelsMass = 1730.14;//[kg]
    private double mt ;//[kg]
    private double mi = -16.5;//[kg/s]
    private double lastSpeed=0;



    public double getUt() {
        return ut;
    }

    public void setUt(double ut) {
        this.ut = ut;
    }


    private void getPosition() {
        //u(t) to prędkość spalania paliwa i jej wartość może być zmienna
        //height to szybkość zmiany wysokości, rockets speed to prędkość zmiany szybkości rakiety,a massChange to prędkość zmiany masy rakiety
        //szybkość zmiany wysokości to po prostu prędkość rakiety w danej chwili czasu
        //szybkość zmiany masy to ilość spalanego paliwa
        mt =rocketsMass+fuelsMass;
        FirstOrderDifferentialEquations ode;
        if (ut<=0&&ut>=mi)
            ode=new ODE(ut);
        else ode=new ODE(mt);

        FirstOrderIntegrator euler=new EulerIntegrator(1);
        PathODE path= new PathODE();
        euler.addStepHandler(path);
        double [] yStart=new double[]{ht,vt,mt};
        double [] yStop=new double[]{1,1,1};
        euler.integrate(ode,0,yStart,1,yStop);

        ht=path.getH();
        vt=path.getV();
        mt=path.getM();

        movement=new MovementParameters();

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
        movement=new MovementParameters();
        movement.setHeight(ht);
        movement.setSpeed(vt);
        movement.setMass(ut);
        notifyObservers();
        while (isRunning) {
            try {
                fuelsMass+=ut; //oblicza obecna mase paliwa
                if(fuelsMass<0) fuelsMass=0;
                if(movement.getHeight()==0) {
                    interrupt(); //jak dotknie ksiezyca to wyladował i koniec
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
