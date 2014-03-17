package ahecproject;

import view.*;

/**
 * Main entry point of the program, the controller class.
 * @author Dsiefus
 */
public class AHECProject {

	// Static variables for storing objects of the working classes and providing global access.
    public static Solver liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
    public static Solver dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
    public static Optimiser optimiser = new Optimiser();
    public static Monitor monitor = new Monitor();
    public static DBManager dbManager = new DBManager();
    public static JFrameLogIn loginFrame = new JFrameLogIn();
    
    /**
     * The main method in the controll class, that is actually going to run.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        loginFrame.setVisible(true);

        System.out.println("it's running!");
    }
}
