package sample;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;


public class PathODE implements StepHandler {

    private double h;
    private double v;
    private double m;

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    @Override
    public void init(double v, double[] doubles, double v1) {

    }

    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {

        double t = stepInterpolator.getCurrentTime();
        double[] x = stepInterpolator.getInterpolatedState();

        if (x[0] <= 0)
            h = 0;
        else h = x[0];

        v = x[1];

        if (x[2] <= 1000)
            m = 1000;
        else m = x[2];


        System.out.println("t= " + t + " " + h + " " + v + " " + m); //Displaying in console simulation's results

    }
}
