package ahecproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * General solver class for the optimisation workflow.
 * A solver contains its own equation, that it is going to use for its calculations.
 * @author Dsiefus
 */
public class Solver extends Thread {

    private final double x1, x2, x3, x0; // equation coefficients
    private double r, t, theta, result; // parameters

    /**
     * Parameterised constructor.
     * Sets the coefficients from the parameters and runs its own thread.
     * @param x3 coefficient for x^3
     * @param x2 coefficient for x^2
     * @param x1 coefficient for x^1
     * @param x0 coefficient for x^0
     */
    public Solver(double x3, double x2, double x1, double x0) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x0 = x0;
        this.start();
    }

    /**
     * Method that contains the used thread's execution loop.
     * Always starts in a waiting state and only restores when a result was requested from a calling class.
     */
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    wait();
                }
                double x = (r / 3.0 + t / 3.0 + theta / 13.3) / 3.0;
                result = x3 * x * x * x + x2 * x * x + x1 * x + x0;
            }
        } catch (InterruptedException ex) {
            System.out.println("Solver interrupted!");
        }
    }

    /**
     * Method used for performing the next set of calculations and return the result.
     * Mainly called from the Optimiser class.
     * After updateing the parameters it notifies its own thread to continue running.
     * @param r0 initial parameter for r
     * @param t0 initial parameter for t
     * @param theta0 initial parameter for theta
     * @return the result obtained from the current iteration
     */
    public double getResults(double r0, double t0, double theta0) {
        r = r0;
        t = t0;
        theta = theta0;
        synchronized (this) {
            this.notify();
        }
        try {
            Thread.sleep(200);
            //if interrupted, sleep forever
            if (!this.isAlive()) {
                Thread.sleep(500000);
            }

        } catch (InterruptedException ex) {
            System.out.println("get results interrupted!");
            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
