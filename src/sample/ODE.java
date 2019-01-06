package sample;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class ODE implements FirstOrderDifferentialEquations {
    private double g = 1.63; //[m/s^2]
    private double k = 636; //[m/s]
    private double u;

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public ODE(double u) {
        this.u = u;
    }

    //Ponieważ zaimplementowaliśmy interface musimy przesłonić dane w interface'ie metody
    //Ustalamy wielkość naszej macierzy wektora
    @Override
    public int getDimension() {
        return 3;
    }

    //Metoda pozwalająca na ustalenie równań/wielkości wyznaczających naszą macierz wektorową
    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {
        //załóżmy że nasze x[0] to h,x[1] to v,x[2] to m
        if (x[2] <= 1000) u = 0;
        dxdt[0] = x[1];
        dxdt[1] = -g - k * u / x[2];
        dxdt[2] = u;
    }
}



