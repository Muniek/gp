package ahecproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for monitoring the optimisation process and
 * its restoration if one of the threads fail.
 * @author Dsiefus
 */
public class Monitor extends Thread {

    private double prevDrag, prevLift; // values obtained from the previous calculations
    //prevr, prevt, prevtheta;
    private boolean autoMode; // recovery mode (false: manual, true: auto)
    private String currentUser; // currently logged in user

    /**
     * Default constructor of the class.
     * Sets the initial values for the member variables,
     * then starts its own thread.
     */
    Monitor() {
        prevDrag = 0;
        prevLift = 0;
        //prevr = 0;
        //prevt = 0;
        //prevtheta = 0;
        autoMode = true;
        currentUser = "";
        this.start();
    }

    /**
     * Contains the main execution loop for the running thread.
     * Periodically gets the current users from the optimiser class,
     * if the results are the same as before "suspects" a failure and
     * tries to restore the system by calling the reactivate() function.
     * Save the results into the database at each step.
     * @see #reactivate()
     */
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
                    System.out.println("reactivating");
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

    /**
     * Sets the current user to an empty string.
     */
    public void logout() {
        currentUser = "";
    }

    /**
     * Public getter for the currentUser member variable.
     * @return currentUser
     * @see #currentUser
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Public setter for the currentUser member variable.
     * Also starts a new optimiser, since it is used only on user login.
     * @param user the name of the logged in user.
     * @return true
     * @see #currentUser
     */
    public boolean setCurrentUser(String user) {
        currentUser = user;
        
        AHECProject.optimiser = new Optimiser();
        AHECProject.optimiser.start();       
        return true;
    }

    /**
     * Method responsible for system reactivation.
     * Checks if the working threads are alive and if not,
     * creates and starts new instances of them.
     */
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

    /**
     * Public getter for the autoMode member variable.
     * @return autoMode
     * @see #autoMode
     */
    public boolean isAutoMode() {
        return autoMode;
    }

    /**
     * Sets autoMode to true.
     * @see #autoMode
     */
    public void setAutomaticMode() {
        autoMode = true;
    }

    /**
     * Sets autoMode to false.
     * @see #autoMode
     */
    public void setManualMode() {
        autoMode = false;
    }
}
