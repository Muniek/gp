/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.sql.*;
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
    private Connection conn;
    private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
    
    Monitor(){
         prevDrag =0;
         prevLift=0;
         prevr=0;
         prevt=0;
         prevtheta=0;
         
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connectDB();
        }
        catch(ClassNotFoundException ex) {
                        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
         this.start(); 
    }
    
    void connectDB() {

        conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/ahecdb", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connected to database");
        }
    }

    
    void setupDB() throws SQLException {
    String createStateTable =
        "create table ahecdb.SAVE " +
        "(SAVE_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "R DOUBLE NOT NULL, " +
        "T DOUBLE NOT NULL, " +
        "THETA DOUBLE NOT NULL, " +
        "DRAG DOUBLE NOT NULL, " + 
        "LIFT DOUBLE NOT NULL, " +
        "TIME TIMESTAMP NOT NULL, "  +
        "PRIMARY KEY (SAVE_ID))";

    Statement stmt = null;
    try {
        stmt = conn.createStatement();
        stmt.executeUpdate(createStateTable);
    } catch (SQLException e) {
        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
        String createUserTable =
        "create table ahecdb.SAVE " +
        "(LOG_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "USERNAME VARCHAR(30) NOT NULL,"+
        "PASS VARCHAR(30),"+
        "TIME TIMESTAMP NOT NULL,"+
        "PRIMARY KEY (LOG_ID))";

    stmt = null;
    try {
        stmt = conn.createStatement();
        stmt.executeUpdate(createUserTable);
    } catch (SQLException e) {
        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
    } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        
    stmt = null;
        
    String createBestTable =
        "create table ahecdb.best " +
        "(SAVE_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "R DOUBLE NOT NULL, " +
        "T DOUBLE NOT NULL, " +
        "THETA DOUBLE NOT NULL, " +
        "DRAG DOUBLE NOT NULL," + 
        "LIFT DOUBLE NOT NULL," +
        "TIME TIMESTAMP NOT NULL,"  +
        "PRIMARY KEY (SAVE_ID))";

        stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createUserTable);
        } catch (SQLException e) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
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
           saveStateValues(r,t,theta,drag,lift);
        }
    }
    
    
    public boolean saveStateValues(double r, double t, double theta, double drag, double lift) {
      Statement stmt;
        try {
      stmt = conn.createStatement();      
      String sql = "INSERT INTO SAVE(R ,T, THETA, DRAG, LIFT, TIME) " +
                   "VALUES (" + Double.toString(r) + ", " + Double.toString(t)+ ", " + Double.toString(theta) + ", "+
                    Double.toString(drag) + ", "+Double.toString(lift) + ", "+ getCurrentTimeStamp()+")";
      stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean saveBestResult(double r, double t, double theta, double drag, double lift) {
      Statement stmt;
        try {
      stmt = conn.createStatement();      
      String sql = "INSERT INTO BEST(R ,T, THETA, DRAG, LIFT, TIME) " +
                   "VALUES (" + Double.toString(r) + ", " + Double.toString(t)+ ", " + Double.toString(theta) + ", "+
                    Double.toString(drag) + ", "+Double.toString(lift) + ", "+ getCurrentTimeStamp()+")";
      stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean logUser(String username, String pass) {
      Statement stmt;
        try {
      stmt = conn.createStatement();      
      String sql = "INSERT INTO USER(USERNAME, PASSWORD, TIME) " +
                   "VALUES ('" + username + "', '" + pass +"', "+ getCurrentTimeStamp() +")";
      stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public double getRecoveredR() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SAVE \nWHERE SAVE_ID = (\n    SELECT IDENT_CURRENT('SAVE'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("R");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getRecoveredT() {
                double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SAVE \nWHERE SAVE_ID = (\n    SELECT IDENT_CURRENT('SAVE'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("T");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getRecoveredTetha() {

        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SAVE \nWHERE SAVE_ID = (\n SELECT IDENT_CURRENT('SAVE'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("THETA");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getRecoveredDrag() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SAVE \nWHERE SAVE_ID = (\n SELECT IDENT_CURRENT('SAVE'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("DRAG");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getRecoveredLift() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SAVE \nWHERE SAVE_ID = (\n SELECT IDENT_CURRENT('SAVE'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("LIFT");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getBestDrag() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM BEST \nWHERE SAVE_ID = (\n SELECT IDENT_CURRENT('BEST'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("DRAG");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
        public double getBestLift() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM BEST \nWHERE SAVE_ID = (\n SELECT IDENT_CURRENT('BEST'))";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            ret = rs.getDouble("LIFT");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private static String getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
	}
}
