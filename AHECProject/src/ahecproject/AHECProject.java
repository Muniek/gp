/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import view.*;

/**
 *
 * @author Dsiefus
 */
public class AHECProject {

    public static Solver liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
    public static Solver dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
    public static Optimiser optimiser = new Optimiser();
    public static Monitor monitor = new Monitor();
    public static DBManager dbManager = new DBManager();
    /**
     * @param args the comonitormonitorand line argumonitorents
     */
    public static void main(String[] args) {
        JFrameLogIn loginFrame = new JFrameLogIn();
        loginFrame.setVisible(true);

        System.out.println("it's running!");
    }
}
