package ahecproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
 * Class for managing database connection and operations.
 * @author Tiago
 */
public class DBManager {

    private Connection conn; // connection object
    private MessageDigest sha256; // hashing object

    /**
     * Default constructor for the class.
     * Gets an SHA-256 instance for hashing,
     * loads the aoache derby jdbc driver,
     * connects to the database and sets it up.
     * @see #connectDB()
     * @see #setupDB()
     */
    public DBManager() {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
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

    /**
     * Tries to connect to database on local host.
     */
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

    /**
     * Sets up database schema and inserts admin user.
     * @throws SQLException
     */
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

    /**
     * Creates the SAVE table in the database.
     * @return the executed SQL statement variable.
     * @throws SQLException
     */
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

    /**
     * Creates the BEST table in the database.
     * @throws SQLException
     */
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

    /**
     * Creates the user related tables in the database: USERLOGS, USERS.
     * @return the executed SQL statement variable.
     * @throws SQLException
     */
    private Statement createUserTables() throws SQLException {
        String createUserlogsTable = "create table ahecdb.USERLOGS "
                + "(LOG_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "USERNAME VARCHAR(32) NOT NULL,"
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
                + "USERNAME VARCHAR(32) NOT NULL,"
                + "PASS VARCHAR(64) NOT NULL,"
                + "SALT BLOB(256) NOT NULL,"
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

    /**
     * Inserts a user into the database, USERS table.
     * Uses salting and hashing techniques for password encryption.
     * @param user the name if the user.
     * @param password the password of the user.
     * @return
     */
    public boolean insertUser(String user, String password) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM AHECDB.USERS WHERE USERNAME = ?");
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            //if the user already exists
            if (rs.next()) {
                return false;
            }

            pstmt = conn.prepareStatement("INSERT INTO AHECDB.USERS(USERNAME,PASS,SALT) VALUES(?,?,?)");
            SecureRandom random = new SecureRandom();
            byte salt[] = new byte[256];
            random.nextBytes(salt);

            sha256.reset();
            sha256.update(salt);
            System.out.println("salt is in bytes " + salt);
            pstmt.setString(1, user);
            String pass = (new HexBinaryAdapter()).marshal(sha256.digest(password.getBytes()));
            System.out.println("pass is " + pass + ",length " + pass.length());
            pstmt.setString(2, pass);
            pstmt.setBytes(3, salt);
            pstmt.executeUpdate();
            System.out.println("user inserted");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Checks the user login details and logs the request into the USERLOGS table.
     * @param username the name of the user.
     * @param pass the password of the user.
     * @return whether the login was successful or not.
     */
    public boolean logUser(String username, String pass) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("SELECT PASS,SALT FROM AHECDB.USERS WHERE USERNAME = ?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            String hashedPass = rs.getString("PASS");
            byte salt[] = rs.getBytes("SALT");
            sha256.reset();
            sha256.update(salt);

            String testpass = (new HexBinaryAdapter()).marshal(sha256.digest(pass.getBytes()));            
             
            if(!testpass.equals(hashedPass))
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            pstmt = conn.prepareStatement("INSERT INTO AHECDB.USERLOGS(USERNAME, SAVED_T) VALUES(?,?)");
            pstmt.setString(1, username);
            pstmt.setTimestamp(2, getCurrentTimeStamp());
            pstmt.executeUpdate();
            AHECProject.monitor.setCurrentUser(username);
        } catch (SQLException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * Standard log method, saves everything from a certain calculation step.
     * @param r
     * @param t
     * @param theta
     * @param drag
     * @param lift
     * @return execution success indicator.
     */
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

    /**
     * Saves the current best results into the database.
     * @param r
     * @param t
     * @param theta
     * @param drag
     * @param lift
     * @return execution successful indicator.
     */
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

    /**
     * Recovers the parameter r from the database.
     * @return the recovered parameter r.
     */
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

    /**
     * Recovers the parameter t from the database.
     * @return the recovered parameter t.
     */
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

    /**
     * Recovers the parameter theta from the database.
     * @return the recovered parameter theta.
     */
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

    /**
     * Loads the best known value for r from the database.
     * @return the best known value for r.
     */
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

    /**
     * Loads the best known value for t from the database.
     * @return the best known value for t.
     */
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

    /**
     * Loads the best known value for theta from the database.
     * @return the best known value for theta.
     */
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

    /**
     * Recovers the value for drag from the database.
     * @return the recovered value for drag.
     */
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

    /**
     * Recovers the value for lift from the database.
     * @return the recovered value for lift.
     */
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

    /**
     * Recovers the best known value for drag from the database.
     * @return the best known value for drag.
     */
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

    /**
     * Recovers the best known value for lift from the database.
     * @return the best known value for drag.
     */
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

    /**
     * Helper function to get a current TimeStamp.
     * @return current TimeStamp.
     */
    private static Timestamp getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }

}
