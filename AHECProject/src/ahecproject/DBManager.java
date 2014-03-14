/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author Dsiefus
 */
public class DBManager {

    private Connection conn;
    private MessageDigest md5;

    public DBManager() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connectDB();
            setupDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectDB() {
        conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/ahecdb;create=true;user=root;password=root");
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connected to database");
        }
    }

    private void setupDB() throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SYS.SYSSCHEMAS WHERE SCHEMANAME = 'AHECDB'");
        if (rs.next()) {
            System.out.println("Schema already exists");
            return;
        }
        createStateTable();
        createUserTables();
        createBestTable();

        insertUser("admin", "admin");
    }

    public void insertUser(String user, String password) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.USERS(USERNAME,PASS) VALUES(?,?)");
            pstmt.setString(1, user);
            String pass = (new HexBinaryAdapter()).marshal(md5.digest(password.getBytes()));
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createBestTable() throws SQLException {
        Statement stmt;
        String createBestTable = "create table ahecdb.BEST "
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

    private Statement createUserTables() throws SQLException {
        String createUserlogsTable = "create table ahecdb.USERLOGS "
                + "(LOG_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "USERNAME VARCHAR(30) NOT NULL,"
                + "SAVED_T TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (LOG_ID))";
        Statement stmt = null;
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
        String createUserTable = "create table ahecdb.USERS "
                + "(USER_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
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
        return stmt;
    }

    private Statement createStateTable() throws SQLException {
        String createStateTable = "create table ahecdb.SAVE "
                + "(SAVE_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "R DOUBLE NOT NULL, "
                + "T DOUBLE NOT NULL, "
                + "THETA DOUBLE NOT NULL, "
                + "DRAG DOUBLE NOT NULL, "
                + "LIFT DOUBLE NOT NULL, "
                + "SAVED_T TIMESTAMP NOT NULL, "
                + "PRIMARY KEY (SAVE_ID))";
        Statement stmt = null;
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
        return stmt;
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
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM AHECDB.USERS WHERE USERNAME = ? AND PASS = ?");
            pstmt.setString(1, username);
            pass = (new HexBinaryAdapter()).marshal(md5.digest(pass.getBytes()));
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.USERLOGS(USERNAME, SAVED_T) VALUES(?,?)");
            pstmt.setString(1, username);
            pstmt.setTimestamp(2, getCurrentTimeStamp());
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
