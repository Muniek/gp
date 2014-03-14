/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dsiefus
 */
public class Monitor extends Thread {

    private double prevDrag, prevLift, prevr, prevt, prevtheta;
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    private boolean autoMode;
    private String currentUser;

    Monitor() {
        prevDrag = 0;
        prevLift = 0;
        prevr = 0;
        prevt = 0;
        prevtheta = 0;
        autoMode = true;

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!currentUser.isEmpty()) {
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

                AHECProject.dbManager.saveBestResult(AHECProject.optimiser.bestr, AHECProject.optimiser.bestt,
                        AHECProject.optimiser.besttheta, AHECProject.optimiser.bestdrag,
                        AHECProject.optimiser.bestlift);
                AHECProject.dbManager.saveStateValues(r, t, theta, drag, lift);
            }
        }
    }

    public void logout() {
        currentUser = "";
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public boolean setCurrentUser(String user) {
        currentUser = user;

        AHECProject.optimiser.start();
        this.start();
        return true;
    }

    public void reactivate() {
        if (!AHECProject.dragSolver.isAlive() || !AHECProject.liftSolver.isAlive()) {
            System.out.println("Some solver is dead! Zombifying");
            AHECProject.liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
            AHECProject.dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.bestdrag = AHECProject.dbManager.getBestDrag();
            AHECProject.optimiser.bestlift = AHECProject.dbManager.getBestLift();
            AHECProject.optimiser.start();
        }

        if (!AHECProject.optimiser.isAlive()) {
            System.out.println("Optimiser is dead! Zombifying");
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.start();
        }
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    public void setAutomaticMode() {
        autoMode = true;
    }

    public void setManualMode() {
        autoMode = false;
    }

}
