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
    private boolean autoMode;

    Monitor() {
        prevDrag = 0;
        prevLift = 0;
        prevr = 0;
        prevt = 0;
        prevtheta = 0;
        autoMode = true;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connectDB();
            setupDB();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        AHECProject.optimiser.start();
        this.start();
    }

    void connectDB() {

        conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/ahecdb;create=true;user=root;password=root");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connected to database");
        }
    }

    void setupDB() throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SYS.SYSSCHEMAS WHERE SCHEMANAME = 'AHECDB'");
        if (rs.next()) {
            System.out.println("Schema already exists");
            return;
        }

        String createStateTable
                = "create table ahecdb.SAVE "
                + "(SAVE_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "R DOUBLE NOT NULL, "
                + "T DOUBLE NOT NULL, "
                + "THETA DOUBLE NOT NULL, "
                + "DRAG DOUBLE NOT NULL, "
                + "LIFT DOUBLE NOT NULL, "
                + "SAVED_T TIMESTAMP NOT NULL, "
                + "PRIMARY KEY (SAVE_ID))";

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createStateTable);
        } catch (SQLException e) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        String createUserlogsTable
                = "create table ahecdb.USERLOGS "
                + "(LOG_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "USERNAME VARCHAR(30) NOT NULL,"
                + "SAVED_T TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (LOG_ID))";

        stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createUserlogsTable);
        } catch (SQLException e) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        String createUserTable
                = "create table ahecdb.USER "
                + "(LOG_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "USERNAME VARCHAR(30) NOT NULL,"
                + "PASS VARCHAR(30) NOT NULL,"
                + "SAVED_T TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (USER_ID))";

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

        String createBestTable
                = "create table ahecdb.BEST "
                + "(SAVE_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "R DOUBLE NOT NULL, "
                + "T DOUBLE NOT NULL, "
                + "THETA DOUBLE NOT NULL, "
                + "DRAG DOUBLE NOT NULL,"
                + "LIFT DOUBLE NOT NULL,"
                + "SAVED_T TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (SAVE_ID))";

        stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createBestTable);
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

            saveBestResult(AHECProject.optimiser.bestr, AHECProject.optimiser.bestt,
                    AHECProject.optimiser.besttheta, AHECProject.optimiser.bestdrag,
                    AHECProject.optimiser.bestlift);
            saveStateValues(r, t, theta, drag, lift);
        }
    }

    public void reactivate() {
        if (!AHECProject.dragSolver.isAlive() || !AHECProject.liftSolver.isAlive()) {
            System.out.println("Some solver is dead! Zombifying");
            AHECProject.liftSolver = new Solver(-12.0, 14.0, 60.0, 24.0);
            AHECProject.dragSolver = new Solver(9.0, -29.0, -26.0, 110.0);
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.bestdrag = this.getBestDrag();
            AHECProject.optimiser.bestlift = this.getBestLift();
            AHECProject.optimiser.start();
        }

        if (!AHECProject.optimiser.isAlive()) {
            System.out.println("Optimiser is dead! Zombifying");
            AHECProject.optimiser = new Optimiser();
            AHECProject.optimiser.start();
        }
    }

    public void setAutomaticMode() {
        autoMode = true;
    }

    public void setManualMode() {
        autoMode = false;
    }

    public boolean saveStateValues(double r, double t, double theta, double drag, double lift) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.SAVE(R , T , THETA , DRAG , LIFT , SAVED_T) VALUES(?,?,?,?,?,?)");
            pstmt.setDouble(1, r);
            pstmt.setDouble(2, t);
            pstmt.setDouble(3, theta);
            pstmt.setDouble(4, drag);
            pstmt.setDouble(5, lift);
            pstmt.setTimestamp(6, getCurrentTimeStamp());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean saveBestResult(double r, double t, double theta, double drag, double lift) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.BEST(R , T , THETA , DRAG , LIFT , SAVED_T) VALUES(?,?,?,?,?,?)");
            pstmt.setDouble(1, r);
            pstmt.setDouble(2, t);
            pstmt.setDouble(3, theta);
            pstmt.setDouble(4, drag);
            pstmt.setDouble(5, lift);
            pstmt.setTimestamp(6, getCurrentTimeStamp());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean logUser(String username, String pass) {
        PreparedStatement pstmt;

        pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.USERS(USERNAME, SAVED_T) VALUES(?,?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, pass);
            pstmt.setTimestamp(3, getCurrentTimeStamp());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public double getRecoveredR() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.SAVE ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.SAVE ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.SAVE ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret = rs.getDouble("THETA");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getBestR() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.BEST ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret = rs.getDouble("R");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getBestT() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.BEST ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret = rs.getDouble("T");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public double getBestTetha() {
        double ret = 0.0;
        try {
            Statement stmt = conn.createStatement();
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.BEST ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.SAVE ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.SAVE ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.BEST ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
            stmt.setMaxRows(1);
            String sql = "select * from AHECDB.BEST ORDER BY SAVE_ID DESC";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret = rs.getDouble("LIFT");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private static Timestamp getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
