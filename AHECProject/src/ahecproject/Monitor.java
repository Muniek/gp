/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author Dsiefus
 */
public class Monitor extends Thread {

    private double prevDrag, prevLift, prevr, prevt, prevtheta;
    private DBManager dbManager;
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    private boolean autoMode;    

    Monitor() {
        prevDrag = 0;
        prevLift = 0;
        prevr = 0;
        prevt = 0;
        prevtheta = 0;
        autoMode = true;
        dbManager = new DBManager();       
        AHECProject.optimiser.start();
        this.start();
    }
     
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }

            double drag, lift, r, t, theta;
            drag = AHECProject.optimiser.getDrag();
            lift = AHECProject.optimiser.getLift();
            r = AHECProject.optimiser.r;
            t = AHECProject.optimiser.t;
            theta = AHECProject.optimiser.theta;
            if (drag == prevDrag && lift == prevLift && autoMode) {
                this.reactivate();
            }

            prevDrag = drag;
            prevLift = lift;

            dbManager.saveBestResult(AHECProject.optimiser.bestr, AHECProject.optimiser.bestt,
                    AHECProject.optimiser.besttheta, AHECProject.optimiser.bestdrag,
                    AHECProject.optimiser.bestlift);
            System.out.println("saving r="+r);
            dbManager.saveStateValues(r, t, theta, drag, lift);
            System.out.println("recovered r="+dbManager.getRecoveredR());
        }
    }

    public void reactivate() {
        if (!AHECProject.dragSolver.isAlive() || !AHECProject.liftSolver.isAlive()) {
            System.out.println("Some solver is dead! Zombifying");
            AHECProject.liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
            AHECProject.dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.bestdrag = dbManager.getBestDrag();
            AHECProject.optimiser.bestlift = dbManager.getBestLift();
            AHECProject.optimiser.start();
        }

        if (!AHECProject.optimiser.isAlive()) {
            System.out.println("Optimiser is dead! Zombifying");
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.start();
        }
    }

    public boolean isAutoMode()
    {
        return autoMode;
    }
    
    public void setAutomaticMode() {
        autoMode = true;
    }

    public void setManualMode() {
        autoMode = false;
    }

}
