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
    Connection conn;
    
    Monitor(){
         prevDrag =0;
         prevLift=0;
         prevr=0;
         prevt=0;
         prevtheta=0;
         
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connectDB();
        } catch (SQLException ex) {
                        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);

        }
        catch(ClassNotFoundException ex) {
                        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);

        }
         this.start();
         
    }
    
    void connectDB() throws SQLException {

    conn = null;

        conn = DriverManager.getConnection(
                         "jdbc:derby://localhost:1527/ahecdb","root","root");
        System.out.println("Connected to database");
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
            drag = AHECProject.optimiser.getDrag();
            lift = AHECProject.optimiser.getLift();
            r=AHECProject.optimiser.r;
            t=AHECProject.optimiser.t;
            theta=AHECProject.optimiser.theta;
            if (drag == prevDrag && lift == prevLift)
            {                
                if (!AHECProject.dragSolver.isAlive() || !AHECProject.liftSolver.isAlive())
                {
                    System.out.println("Some solver is dead! Zombifying");                    
                    AHECProject.liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
                    AHECProject.dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);                    
                    AHECProject.optimiser = new Optimiser();
                }
            }
            System.out.println("opt is alive. "+AHECProject.optimiser.isAlive());
            if (!AHECProject.optimiser.isAlive())
            {
                System.out.println("Optimiser is dead! Zombifying");
                AHECProject.optimiser = new Optimiser();
            }
            prevDrag = drag;
            prevLift = lift;
           saveShapeParameters(r,t,theta);
           saveDrag();
           saveLift();
        }
    }
    
    
    public static boolean saveShapeParameters(double r, double t, double theta) {
        //TODO
        return true;
    }

    public static boolean saveDrag() {
        //TODO
        return true;
    }

    public static boolean saveLift() {
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
