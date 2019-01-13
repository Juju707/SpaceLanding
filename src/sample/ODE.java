package sample;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
/**
 * Class ODE represents computing core for given derivatives.
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class ODE implements FirstOrderDifferentialEquations {
    /**
     * Gravity's value parameter.
     */
    private double g = 1.63; //[m/s^2]
    /**
     * Muzzle velocity value parameter.
     */
    private double k = 636; //[m/s]
    /**
     * Fuel's usage value parameter.
     */
    private double u;
    /**
     * Method for setting the value of h parameter.
     * @return
     */
    public double getU() {
        return u;
    }
    /**
     * Method for setting the value of h parameter.
     * @param u
     */
    public void setU(double u) {
        this.u = u;
    }

    /**
     * Constructor for the class.
     * @param u
     */
    public ODE(double u) {
        this.u = u;
    }

    //Matrix dimension
    /**
     * Method called for setting matrix's dimension.
     */
    @Override
    public int getDimension() {
        return 3;
    }

    /**
     * Method called for computing derivatives.
     * @param dxdt
     * @param t
     * @param x a
     */
    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {
        //x[0] - h
        // x[1] - v
        // x[2] - m
        //Differential equations
        if (x[2] <= 1000) u = 0;
        dxdt[0] = x[1];
        dxdt[1] = -g - k * u / x[2];
        dxdt[2] = u;
    }
}



