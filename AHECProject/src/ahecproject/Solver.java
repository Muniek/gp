/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dsiefus
 */
public class Solver extends Thread {

    private final double x1, x2, x3, x0;
    private double r, t, theta, result;
   
    public Solver(double x3, double x2, double x1, double x0) {        
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x0 = x0;        
        this.start();
    }

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
            if(!this.isAlive())
                Thread.sleep(500000); 
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return result;
    }

}
