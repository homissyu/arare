// package
package com.jay.database;

// imports
import com.jay.util.CommonConst;
import java.io.Serializable;

//class
public class DBInfo implements Serializable {
//    public static final String SOURCE_ID = "";

    // History 1 : Below 1 line is added by dragon to sustain the compatibility with previous version.
    static final long serialVersionUID = 5928722563395418748L;

    public String mServerName = null;
    public String mHostName = null;

    public String getmHostName() {
        return mHostName;
    }

    public void setmHostName(String mHostName) {
        this.mHostName = mHostName;
    }
    public int mPort = 5000;
    public int mDBType = DatabaseConnection.ORACLE;
    public String mUsername = null;
    public String mPassword = null;
    public String mTermType = null;

    public DBInfo(){
    }
    
    public DBInfo(String aServerName, String aHostName, int aPort, String aDBType, String aUsername, String aPassword) {
        mServerName = aServerName.trim();
        mHostName = aHostName.trim();
        mPort = aPort;
        if (aDBType.trim().toUpperCase().equals(DatabaseConnection.ORACLE_STRING))
            mDBType = DatabaseConnection.ORACLE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.SYBASE_STRING))
            mDBType = DatabaseConnection.SYBASE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.MSSQLSERVER_STRING))
            mDBType = DatabaseConnection.MSSQLSERVER;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.MYSQL_STRING))
            mDBType = DatabaseConnection.MYSQL;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.TIBERO_STRING))
            mDBType = DatabaseConnection.TIBERO;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.DB2_STRING))
            mDBType = DatabaseConnection.DB2;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.CUBRID_STRING))
            mDBType = DatabaseConnection.CUBRID;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.INFOMIX_STRING))
            mDBType = DatabaseConnection.INFOMIX;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.ALTIBASE_STRING))
            mDBType = DatabaseConnection.ALTIBASE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.POSTGRE_STRING))
            mDBType = DatabaseConnection.POSTGRE;
        else // default to ORACLE if unknown
            mDBType = DatabaseConnection.ORACLE;
        mUsername = aUsername;
        mPassword = aPassword;
    }

    public DBInfo(String aServerName, String aHostName, int aPort, int aDBType, String aUsername, String aPassword) {
        mServerName = aServerName.trim();
        mHostName = aHostName.trim();
        mPort = aPort;
        mDBType = aDBType;
        mUsername = aUsername;
        mPassword = aPassword;
    }

    public String getServerName() {
        return mServerName;
    }
    
    public String getTermType() {
        return mTermType;
    }

    public void setServerName(String serverName) {
        mServerName = serverName;
    }

    public String getHostName() {
        return mHostName;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int port) {
        mPort = port;
    }
    
    public void setTermType(String temrType) {
        mTermType = temrType;
    }

    public int getDBType() {
        return mDBType;
    }

    public String getDBTypeString()  {
        String ret = null;
        switch(mDBType){
            case DatabaseConnection.ORACLE:
                ret = DatabaseConnection.ORACLE_STRING;
                break;
            case DatabaseConnection.SYBASE:
                ret = DatabaseConnection.SYBASE_STRING;
                break;
            case DatabaseConnection.ALTIBASE:
                ret = DatabaseConnection.ALTIBASE_STRING;
                break;
            case DatabaseConnection.CUBRID:
                ret = DatabaseConnection.CUBRID_STRING;
                break;
            case DatabaseConnection.DB2:
                ret = DatabaseConnection.DB2_STRING;
                break;
            case DatabaseConnection.INFOMIX:
                ret = DatabaseConnection.INFOMIX_STRING;
                break;
            case DatabaseConnection.MSSQLSERVER:
                ret = DatabaseConnection.MSSQLSERVER_STRING;
                break;
            case DatabaseConnection.POSTGRE:
                ret = DatabaseConnection.POSTGRE_STRING;
                break;
            case DatabaseConnection.TIBERO:
                ret = DatabaseConnection.TIBERO_STRING;
                break;
            case DatabaseConnection.MYSQL:
                ret = DatabaseConnection.MYSQL_STRING;
                break;
            default:
                ret = DatabaseConnection.ORACLE_STRING;
                break;                
        }
        return ret;        
    }

    public void setDBType(String aDBType)  {
        if (aDBType.trim().toUpperCase().equals(DatabaseConnection.ORACLE_STRING))
            mDBType = DatabaseConnection.ORACLE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.SYBASE_STRING))
            mDBType = DatabaseConnection.SYBASE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.MSSQLSERVER_STRING))
            mDBType = DatabaseConnection.MSSQLSERVER;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.MYSQL_STRING))
            mDBType = DatabaseConnection.MYSQL;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.TIBERO_STRING))
            mDBType = DatabaseConnection.TIBERO;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.DB2_STRING))
            mDBType = DatabaseConnection.DB2;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.INFOMIX_STRING))
            mDBType = DatabaseConnection.INFOMIX;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.CUBRID_STRING))
            mDBType = DatabaseConnection.CUBRID;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.POSTGRE_STRING))
            mDBType = DatabaseConnection.POSTGRE;
        else if (aDBType.trim().toUpperCase().equals(DatabaseConnection.ALTIBASE_STRING))
            mDBType = DatabaseConnection.ALTIBASE;
        else // default to ORACLE if unknown
            mDBType = DatabaseConnection.ORACLE;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUserName(String userName)  {
        mUsername = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password)  {
        mPassword = password;
    }

    // Same as equal except password is not checked
    public boolean equivalent(DBInfo aInfo) {
        if (aInfo == null)
            return false;

        if (mServerName.equals(aInfo.mServerName) &&
                mHostName.equals(aInfo.mHostName) &&
                mPort == aInfo.mPort &&
                mDBType == aInfo.mDBType &&
                mUsername.equals(aInfo.mUsername))
            return true;

        return false;
    }
    
    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append(CommonConst.HOST_STRING + ":").append(this.getHostName()).append(CommonConst.NEW_LINE_N);
        ret.append(CommonConst.SID_STRING + ":").append(this.getServerName()).append(CommonConst.NEW_LINE_N);
        ret.append(CommonConst.PORT_STRING + ":").append(this.getPort()).append(CommonConst.NEW_LINE_N);
        ret.append(CommonConst.USER_STRING + ":").append(this.getUsername()).append(CommonConst.NEW_LINE_N);
        ret.append(CommonConst.TERM_TYPE).append(":").append(this.getTermType()).append(CommonConst.NEW_LINE_N);        
        return ret.toString();
    }

    public boolean equals(DBInfo aInfo) {
        if (aInfo == null)
            return false;

        if (mServerName.equals(aInfo.mServerName) &&
                mHostName.equals(aInfo.mHostName) &&
                mPort == aInfo.mPort &&
                mDBType == aInfo.mDBType &&
                mUsername.equals(aInfo.mUsername) &&
                mPassword.equals(aInfo.mPassword))
            return true;

        return false;
    }
}

