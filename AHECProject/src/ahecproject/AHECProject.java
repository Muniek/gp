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
public class AHECProject {

    public static Solver liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
    public static Solver dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Optimiser op = new Optimiser();

        op.start();
        System.out.println("it's running!");
        try {
            TimeUnit.MILLISECONDS.sleep(3100);
        } catch (InterruptedException ex) {
            Logger.getLogger(AHECProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("killing solver");
        liftSolver = null;
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AHECProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        //creating new solver so it continues
        liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
        System.out.println("resuming");
        synchronized (op) {
            op.notify();
        }

    }
}
