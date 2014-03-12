/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dsiefus
 */
public class Monitor extends Thread {

    private double prevDrag, prevLift, prevr, prevt, prevtheta;     
    Optimiser optimiser = AHECProject.optimiser;
    Monitor() {
         prevDrag =0;
         prevLift=0;
         prevr=0;
         prevt=0;
         prevtheta=0;                  
         this.start();
    }

    @Override
    public void run() {        
        while(true)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }            
            double drag, lift, r, t, theta;
            drag = optimiser.GetDrag();
            lift = optimiser.GetLift();
            if (drag == prevDrag && lift == prevLift)
            {                
                if (!AHECProject.dragSolver.isAlive() || !AHECProject.liftSolver.isAlive())
                {
                    System.out.println("Some solver is dead! Zombifying");                    
                    AHECProject.liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
                    AHECProject.dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
                    optimiser = new Optimiser();
                }
            }
            if (!optimiser.isAlive())
            {
                System.out.println("Optimiser is dead! Zombifying");
                optimiser = new Optimiser();
            }
            prevDrag = drag;
            prevLift = lift;
           
        }
    }
    
    
    public static boolean postShapeParameters(double r, double t, double theta) {
        //TODO
        return true;
    }

    public static boolean postDrag() {
        //TODO
        return true;
    }

    public static boolean postLift() {
        //TODO
        return true;
    }

    public static boolean logUser(String username, String pass) {
        //TODO
        return true;
    }

    public static double getRecoveredR() {
        //TODO
        return 1.0;
    }

    public static double getRecoveredT() {
        //TODO
        return 1.0;
    }

    public static double getRecoveredTetha() {
        //TODO
        return 1.0;
    }

    public static double getRecoveredDrag() {
        //TODO
        return 1.0;
    }

    public static double getRecoveredLift() {
        //TODO
        return 1.0;
    }

    public static double getBestDragLift() {
        //TODO
        return 1.0;
    }

    public static boolean resetDB() {
        //TODO
        return true;
    }
}
