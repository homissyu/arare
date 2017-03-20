// package
package com.jay.database;

// imports
import com.jay.util.CommonConst;
import com.jay.util.JayException;
import java.sql.*;


//class
/**
 * This class encapsulates a connection to a database
 * @author      Manny Parasirakis
 */
public class DatabaseConnection {
    public static final String SOURCE_ID = "";
    private static final String SUBSYSTEM = "DatabaseConnection";
    
    public static final int ORACLE = CommonConst.ORACLE_TYPE;
    public static final int SYBASE = CommonConst.SYBASE_TYPE;
    public static final int MSSQLSERVER = CommonConst.MSSQLSERVER_TYPE;
    public static final int MYSQL = CommonConst.MYSQL_TYPE;
    public static final int TIBERO = CommonConst.TIBERO_TYPE;
    public static final int DB2 = CommonConst.DB2_TYPE;
    public static final int ALTIBASE = CommonConst.ALTIBASE_TYPE;
    public static final int CUBRID = CommonConst.CUBRID_TYPE;
    public static final int INFOMIX = CommonConst.INFOMIX_TYPE;
    public static final int POSTGRE = CommonConst.POSTGRE_TYPE;

    public static final String ORACLE_STRING = CommonConst.DATABASE_TYPE[0];
    public static final String SYBASE_STRING = CommonConst.DATABASE_TYPE[4];
    public static final String MSSQLSERVER_STRING = CommonConst.DATABASE_TYPE[1];
    public static final String MYSQL_STRING = CommonConst.DATABASE_TYPE[2];
    public static final String TIBERO_STRING = CommonConst.DATABASE_TYPE[5];
    public static final String DB2_STRING = CommonConst.DATABASE_TYPE[3];
    public static final String ALTIBASE_STRING = CommonConst.DATABASE_TYPE[6];
    public static final String CUBRID_STRING = CommonConst.DATABASE_TYPE[7];
    public static final String INFOMIX_STRING = CommonConst.DATABASE_TYPE[8];
    public static final String POSTGRE_STRING = CommonConst.DATABASE_TYPE[9];
    
    private static final int TWO_GIGS = 2147483647; // bytes
	
    private String mURL = null;
    private String mServerName = null;
    private String mHostName = null;
    private int mPort;
    private String mDriver = null;
    private int mDBType;
    private String mMasterUserName = null;
    private String mMasterPassword = null;
    private String mUserName = null;
    private String mPassword = null;
    private String mLoginUserName = null;
    private String mLoginPassword = null;

    private Connection mConnection = null;
    private Statement mLastStatement = null;

    /**
     * Class Constructor
     */
    public DatabaseConnection() throws Exception {
        // Load database specific driver
        loadDriver();
    }

    public DatabaseConnection(DBInfo aDBInfo) throws Exception {
    	try {
            mServerName = aDBInfo.getServerName();
            if (mServerName == null || mServerName.equals(""))
                throw new Exception("Database name is not specified in configuration file.");

            mHostName = aDBInfo.getHostName();
            if (mHostName == null || mHostName.equals(""))
                throw new Exception("Database hostname is not specified in configuration file.");
            mPort = aDBInfo.getPort();
            mDBType = aDBInfo.getDBType();
            mMasterUserName = aDBInfo.getUsername();
            mMasterPassword = aDBInfo.getPassword();

        } catch (Exception e) {
            throw new JayException(e);
        }

        loadDriver();
    }

    private void loadDriver() throws Exception {
        String aDriverStr = null;
        try{
            switch (mDBType) {
                case ORACLE:
                    aDriverStr = CommonConst.ORACLE_DRIVER;
                    mURL = "jdbc:oracle:thin:@" + mHostName + ":" + mPort + ":" + mServerName;
                    break;
                case SYBASE:
                    aDriverStr = CommonConst.SYBASE_DRIVER;
                    mURL = new String("jdbc:sybase:Tds:" + mHostName + ":" + mPort);
                    break;
                case MSSQLSERVER:
                    aDriverStr = CommonConst.MSSQLSERVER_DRIVER;
                    mURL = new String("jdbc:sqlserver://" + mHostName + ":" + mPort + ";databaseName="+mServerName+";integratedSecurity=false;");
                    break;
                case MYSQL:
                    aDriverStr = CommonConst.MYSQL_DRIVER;
                    mURL = new String("jdbc:mysql://" + mHostName + ":" + mPort + "/" + mServerName + "?useOldAliasMetadataBehavior=true");
                    break;
                case TIBERO:
                    aDriverStr = CommonConst.TIBERO_DRIVER;
                    mURL = new String("jdbc:tibero:thin:@" + mHostName + ":" + mPort + ":" + mServerName);                   
                    break;
                case DB2:
                    aDriverStr = CommonConst.DB2_DRIVER;
                    mURL = new String("jdbc:db2://" + mHostName + ":" + mPort + "/" + mServerName);                   
                    break;
                case ALTIBASE:
                    aDriverStr = CommonConst.ALTIBASE_DRIVER;
                    mURL = new String("jdbc:Altibase://" + mHostName + ":" + mPort + "/" + mServerName);  
                    break;
                case CUBRID:
                    aDriverStr = CommonConst.CUBRID_DRIVER;
                    mURL = new String("jdbc:cubrid:" + mHostName + ":" + mPort + ":" + mServerName+":::");                   
                    break;
                case INFOMIX:
                    aDriverStr = CommonConst.INFOMIX_DRIVER;
                    mURL = new String("jdbc:informix-sqli://" + mHostName + ":" + mPort + ":informixserver=" + mServerName);                   
                    break;
                case POSTGRE:
                    aDriverStr = CommonConst.POSTGRE_DRIVER;
                    mURL = new String("jdbc:postgresql://" + mHostName + ":" + mPort + "/" + mServerName);                   
                    break;
                default:
                    throw new Exception("No Suitable Database Management System");
            }
//            aDriverStr = "org.jdbcdslog.DriverLoggingProxy";
            DriverManager.registerDriver((Driver) (Class.forName(aDriverStr)).newInstance());                    
//            mURL = "jdbc:jdbcdslog:"+mURL+";targetDriver="+aDriverStr; 
        } catch (Exception e) {
            throw new JayException("Unable to locate database driver software. Check if it is defined in CLASSPATH.");
        }
    }

    protected void finalize() throws JayException {
        try {
            if (isConnectionValid())
                disconnect();
        } catch (Exception e) {
            throw new JayException(e);
        } // do nothing
    }

    /**
     * This method connects to a database as the defined user
     */
    public synchronized void connect() throws Exception {
        connect(true);
    }

    /**
     * This method connects to a database as the defined user and passes validation flag
     * for connection initialization
     */
    public synchronized void connect(boolean validate) throws Exception {
        initializeConnection(mURL, mMasterUserName, mMasterPassword, validate);
    }

    /**
     * This method connects to a database as the defined user
     */
    public synchronized void connect(String aUser, String aPassword) throws Exception {
    	if (isConnectionValid())
            disconnect();
        try {
            mUserName = aUser.trim();
            mPassword = aPassword.trim();

            if (mUserName.length() == 0)
                throw new Exception("Invalid user name.");

            mConnection = DriverManager.getConnection(mURL, mUserName, mPassword);

            mConnection.close();
            mConnection = null;
        } catch (Exception e) {
            if (mConnection != null) {
                try { 
                    mConnection.close();
                } catch (Exception ee) {
                } // do nothing
                mConnection = null;
            }
            e.printStackTrace();
            throw new JayException(e.getLocalizedMessage());
        }
        initializeConnection(mURL, mMasterUserName, mMasterPassword, true);
    }

    /**
     * This method connects to a database as the defined user
     */
    public synchronized void connectAsLoginUser(String aUser, String aPassword) throws Exception {
        mLoginUserName = aUser.trim();
        mLoginPassword = aPassword.trim();

        if (mLoginUserName.length() == 0)
            throw new Exception("Invalid login user name.");

        connect(true);
    }

    /**
     * This method connects to a database as the given user.
     */
    public synchronized void connectAsUser(String aUser, String aPassword) throws Exception {
        try {
            if (isConnectionValid())
                disconnect();
        } catch (Exception e) {
        } // do nothing

        mUserName = aUser.trim();
        mPassword = aPassword.trim();

        initializeConnection(mURL, mUserName, mPassword, false);
    }

    private synchronized void initializeConnection(String aURL, String aUser, String aPassword, boolean aCheckForValidInstallation) throws Exception {
        try {
            mConnection = DriverManager.getConnection(aURL, aUser, aPassword);

        } catch (Exception e) {
            
            if (!aUser.equals(mMasterUserName) || !aPassword.equals(mMasterPassword))
                throw new Exception("Invalid username/password has been specified in configuration file.  Login Failed.");
            else
                throw e;
        }

        try {
            // Set auto commit to false
            mConnection.setAutoCommit(false);

            switch (mDBType) {
                case ORACLE:
                    break;
                case SYBASE:
                    execute("set textsize " + TWO_GIGS);
                    break;
                case MSSQLSERVER:
                    break;
                case TIBERO:
                    break;
                case MYSQL:
                    break;
                case DB2:
                    break;
                case ALTIBASE:
                    break;
                case CUBRID:
                    break;
                case INFOMIX:
                    break;
                case POSTGRE:
                    break;
            }

        } catch (Exception e) {
            if (mConnection != null) {
                try {
                    mConnection.close();
                } catch (Exception ee) {
                    throw new JayException(ee);
                } // do nothing
                mConnection = null;
            }
            throw new JayException("Unable to connect to database.");
        }
    }

    /**
     * This method attempts to disconnect from a database
     */
    public synchronized void disconnect() throws Exception {
        if (mConnection == null)
            return;

        try {
            mConnection.rollback();
            mConnection.close();
            mConnection = null;
        } catch (Exception e) {
//            e.printStackTrace();
            throw new JayException(CommonConst.DISCONNECTED);
        }
    }

    public synchronized void setReadOnly(boolean ro) throws Exception {
        try {
            mConnection.setReadOnly(ro);
        } catch (Exception e) {
            throw new JayException(e);
        }
    }

    public synchronized void commit() throws Exception {
        try {
            mConnection.commit();
        } catch (Exception e) {
            throw new JayException(e);
        }
    }

    public synchronized void rollback() throws Exception {
        try {
            mConnection.rollback();
        } catch (Exception e) {
            throw new JayException(e);
        }
    }

    public synchronized SQLWarning getWarnings() throws Exception {
        try {
            return mLastStatement.getWarnings();
        } catch (Exception e) {
            throw new JayException(e);
        }
    }

    public synchronized void clearWarnings() throws Exception {
        try {
            mLastStatement.clearWarnings();
        } catch (Exception e) {
            throw new JayException(e);
        }
    }

    public synchronized boolean execute(String sql) throws Exception {
        if (sql == null || sql.equals(""))
            return true;

        try {
            mLastStatement = mConnection.createStatement();
            return mLastStatement.execute(sql);
        } catch (Exception e) {
            throw new JayException("Unable to execute query.");
        }
    }

    public synchronized int executeUpdate(String sql) throws Exception {
        if (sql == null || sql.equals(""))
            return 0;

        try {
            mLastStatement = mConnection.createStatement();
            return mLastStatement.executeUpdate(sql);
        } catch (Exception e) {
            throw new JayException("Unable to execute query.");
        }
    }

    public synchronized ResultSet executeQuery(String sql) throws Exception {
        if (sql == null || sql.equals(""))
            return null;

        try {
            mLastStatement = mConnection.createStatement();
            return mLastStatement.executeQuery(sql);
        } catch (Exception e) {
            throw new JayException("Unable to execute query.");
        }
    }

    public synchronized PreparedStatement prepareStatement(String sql) throws Exception {
    	if (sql == null || sql.equals(""))
            return null;

        try {
            return mConnection.prepareStatement(sql);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new JayException("Unable to prepare SQL statement.");
        }
    }

    public synchronized PreparedStatement prepareStatement(String sql, int scrolType, int readOnly) throws Exception {
        if (sql == null || sql.equals(""))
            return null;

        try {
            return mConnection.prepareStatement(sql,scrolType,readOnly);
        } catch (Exception e) {
            throw new JayException("Unable to prepare SQL statement.");
        }
    }

    public synchronized Statement createStatement() throws Exception {
        try {
            return mConnection.createStatement();
        } catch (Exception e) {
            throw new JayException("Unable to create SQL statement.");
        }
    }

    public synchronized boolean isConnectionValid() {
        if (mConnection == null)
            return false;

        try {
            return (! mConnection.isClosed());
        } catch (Exception e) {
            return false;
        }
    }

    // get/set
    public String getUsername() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getLoginUsername() {
        return mLoginUserName;
    }

    public String getLoginPassword() {
        return mLoginPassword;
    }

    public String getMasterUsername() {
        return mMasterUserName;
    }

    public String getMasterPassword() {
        return mMasterPassword;
    }

    public Connection getJDBCConnection() {
        return mConnection;
    }

    public String getServerName() {
        return mServerName;
    }

    public void setServerName(String aName) {
        mServerName = aName;
    }

    public String getHostName() {
        return mHostName;
    }

    public void setHostName(String aName) {
        mHostName = aName;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int aPort) {
        mPort = aPort;
    }

    public int getDBType() {
        return mDBType;
    }

    public void setDBType(int aDBType) {
        mDBType = aDBType;
    }

    public String getDriver() {
        return mDriver;
    }

    public void setDriver(String aDriver) {
        mDriver = aDriver;
    }

    public static int stringToDBType(String aDB) throws Exception {
        try {
            String db = aDB.toUpperCase();
            if (db.equals(ORACLE_STRING))
                return ORACLE;
            else if (db.equals(SYBASE_STRING))
                return SYBASE;
            else if (db.equals(MSSQLSERVER_STRING))
                return MSSQLSERVER;
            else if (db.equals(MYSQL_STRING))
                return MYSQL;
            else if (db.equals(TIBERO_STRING))
                return TIBERO;
            else if (db.equals(DB2_STRING))
                return DB2;
            else if (db.equals(ALTIBASE_STRING))
                return ALTIBASE;
            else if (db.equals(CUBRID_STRING))
                return CUBRID;
            else if (db.equals(INFOMIX_STRING))
                return INFOMIX;
            else if (db.equals(POSTGRE_STRING))
                return POSTGRE;
            else
                throw new Exception();
        } catch (Exception e) {
            throw new JayException("Unable to determine database type.");
        }
    }

    public String dbTypeToString() {
        return dbTypeToString(mDBType);
    }

    public static String dbTypeToString(int aType) {
        switch (aType) {
            case ORACLE:
                return ORACLE_STRING;
            case SYBASE:
                return SYBASE_STRING;
            case MSSQLSERVER:
                return MSSQLSERVER_STRING;
            case MYSQL:
                return MYSQL_STRING;
            case TIBERO:
                return TIBERO_STRING;
            case DB2:
                return DB2_STRING;
            case ALTIBASE:
                return ALTIBASE_STRING;
            case CUBRID:
                return CUBRID_STRING;
            case INFOMIX:
                return INFOMIX_STRING;
            case POSTGRE:
                return POSTGRE_STRING;
            default:
                return "";
        }
    }

     public String dbInfoShortString() {
        String dbInfoString = "DB Name: " +
                                mServerName + "     " +
                                "DB Type: " +
                                dbTypeToString() + "     " +
                                "DB Host: " +
                                mHostName + "[" + mPort + "]";

        return dbInfoString;
    }

    public String dbInfoString() {
        String dbInfoString = "DB Name: " +
                                mServerName + "     " +
                                "DB Type: "+
                                dbTypeToString() + "     " +
                                "DB Host: " +
                                mHostName + "[" + mPort + "]     " +
                                "DB Owner: " +
                                mMasterUserName;

        return dbInfoString;
    }

    public synchronized boolean isConnectionAlive() throws JayException {
        boolean ret = false;
        Statement stmt = null;
//System.out.println("mConnection:"+mConnection);        
        if (mConnection == null)
            return ret;
//System.out.println("1:"+ret);
        try {
            stmt = mConnection.createStatement();
            ret = true;
// System.out.println("2:"+ret);            
        } catch ( SQLException sqle ) {
// System.out.println("3:"+ret);
            sqle.printStackTrace();
            ret = false;
        } catch ( Exception e ) {
            e.printStackTrace();
// System.out.println("4:"+ret);
            ret =  false;
        } finally {
            try {
                stmt.close();
                stmt = null;
            } catch ( SQLException e ) {
                throw new JayException(e);
            }
        }
//System.out.println("5:"+ret);
        return ret;
    }

    protected void closeResources(Statement stmt, ResultSet rset) throws JayException {
        try{
            if( rset != null ) {
                rset.close();
                rset = null;
            }
            if( stmt != null ) {
                stmt.close();
                stmt = null;
            }
        } catch(Exception e) {
            throw new JayException(e);
        } finally{
            rset = null;
            stmt = null;
        }
    }
}