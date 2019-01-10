package sample;

/**
 * Class MovementParameters represents class with movement parameters.
 *
 * @author Julia Szymczak and Sara Strzalka
 * @version 1.0
 */
public class MovementParameters {
    //Parameters of rocket's movement
    /**
     * Represents speed of rocket.
     */
    private double speed;
    /**
     * Represents height of rocket.
     */
    private double height;
    /**
     * Represents current mass of rocket.
     */
    private double mass;

    /**
     * Returns speed of rocket.
     *
     * @return speed of rocket.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets speed of rocket.
     *
     * @param speed speed of rocket.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Returns height of rocket.
     *
     * @return height of rocket.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height of rocket.
     *
     * @param height height of rocket.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns mass of rocket.
     *
     * @return mass of rocket.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Sets mass of rocket.
     *
     * @param mass mass of rocket.
     */
    public void setMass(double mass) {
        this.mass = mass;
    }


}
