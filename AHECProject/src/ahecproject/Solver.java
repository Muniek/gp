/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

/**
 *
 * @author Dsiefus
 */
public class Solver extends Thread {

    private double x1, x2, x3, x0;

    public Solver(double x3, double x2, double x1, double x0) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x0 = x0;
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double GetResults(double r, double t, double theta) {
        double x = (r / 3.0 + t / 3.0 + theta / 13.3) / 3.0;
        return x3 * x * x * x + x2 * x * x + x1 * x + x0;
    }
}
