package sample;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
/**
 * Class PathODE represents path for parameters integration.
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */

public class PathODE implements StepHandler {
    /**
     * Speed of height's change parameter for integration.
     */
    private double h;
    /**
     * Speed of velocity's change parameter for integration.
     */
    private double v;
    /**
     * Speed of mass's change parameter for integration.
     */
    private double m;

    /**
     * Method for getting the value of h parameter.
     * @return
     */
    public double getH() {
        return h;
    }
    /**
     * Method for setting the value of h parameter.
     * @param h
     */
    public void setH(double h) {
        this.h = h;
    }
    /**
     * Method for getting the value of v parameter.
     * @return
     */
    public double getV() {
        return v;
    }
    /**
     * Method for setting the value of h parameter.
     * @param v
     */
    public void setV(double v) {
        this.v = v;
    }
    /**
     * Method for getting the value of m parameter.
     * @return
     */
    public double getM() {
        return m;
    }
    /**
     * Method for setting the value of h parameter.
     * @param m
     */
    public void setM(double m) {
        this.m = m;
    }

    @Override
    public void init(double v, double[] doubles, double v1) {

    }
    /**\
     * Method called for handling integration steps
     * Responsible for setting parameters for integration.
     * @param stepInterpolator
     * @param b
     */
    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {

        //Saves current step to x vector.
        double[] x = stepInterpolator.getInterpolatedState();

        //If height equals 0 it does not change anymore.
        if (x[0] <= 0)
            h = 0;
        else h = x[0];

        v = x[1];

        //If current mass equals mass of the rocket it also does not change anymore.
        if (x[2] <= 1000)
            m = 1000;
        else m = x[2];


        System.out.println( h + " " + v + " " + m); //Displaying in console simulation's results

    }
}
