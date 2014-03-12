/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dsiefus
 */
public class Optimiser extends Thread {

    Solver dragSolver;
    Solver liftSolver;
    double r, t, theta, drag, lift;

    public Optimiser() {        
        r = Math.random() * 9;
        t = Math.random() * 9;
        theta = Math.random() * 39 + 1.0;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {                      
                drag = AHECProject.dragSolver.GetResults(r, t, theta);                
                lift = AHECProject.liftSolver.GetResults(r, t, theta);
                
                System.out.println("drag=" + drag + ",lift=" + lift + ",ratio=" + (lift / drag));
                r = (r + Math.random() * 9) % 9.0;
                t = (t + Math.random() * 9) % 9.0;
                theta = ((r + Math.random() * 39) % 40.0) + 1.0;
                Thread.sleep(2500);
            }
        } catch (InterruptedException ex) {
            System.out.println("Optimiser interrupted!");
        }

    }

    public double GetLift() {
        return lift;
    }

    public double GetDrag() {
        return drag;
    }
}
