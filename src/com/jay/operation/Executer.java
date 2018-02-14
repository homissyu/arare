package com.jay.operation;

import com.jay.database.DBInfo;
import com.jay.database.DBUtil;
import com.jay.database.DatabaseConnection;
import com.jay.util.CommonConst;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 * @author Jay
 * @date 2006. 6. 19.
 */
public class Executer {
    private static String SUBSYSTEM = "Executer";
    
    private DatabaseConnection mTargetDBConnection = null;
    public DBInfo mTargetDBInfo = null;
    public DBUtil mDBUtil = new DBUtil();
    
    public HashMap mExeTimeMap = new HashMap();
    private DefaultTableModel tableModel;
    
    private DefaultComboBoxModel comboBoxModel;
	
    // Constructor
    public Executer() {
    }
    
    private DefaultTableModel getTableModel(Vector aDatum, Vector aColNames){
        tableModel = new DefaultTableModel(aDatum, aColNames){
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
    	return tableModel;
    }
    
    /**
     * @param sQry
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public DefaultTableModel getTableModel(String sQry) throws SQLException, Exception{
        Vector aDatum = new Vector();
        Vector aColNames = new Vector();
        
        aDatum = this.toVector((ArrayList)this.getDatumMap(sQry).get(CommonConst.DATUM));
        aColNames = (Vector)this.getDatumMap(sQry).get(CommonConst.COLUMNNAMES);
        
    	return getTableModel(aDatum, aColNames);
    }
    
    public DefaultTableModel getTableModel(HashMap aMap) throws SQLException, Exception{
        Vector aDatum = new Vector();
        Vector aColNames = new Vector();
        aDatum = this.toVector((ArrayList)aMap.get(CommonConst.DATUM));
        aColNames = (Vector)aMap.get(CommonConst.COLUMNNAMES);
        
    	return getTableModel(aDatum, aColNames);
    }
    
    /**
     * @param aMap
     */
    public void setTargetDBInfo(HashMap aMap){
    	String aSid = (String)aMap.get(CommonConst.SID_STRING);
    	String aHost = (String)aMap.get(CommonConst.HOST_STRING);
        int aPort = Integer.parseInt((String)aMap.get(CommonConst.PORT_STRING));
    	String aDBType = (String)aMap.get(CommonConst.DB_TYPE_STRING);
    	String aUser = (String)aMap.get(CommonConst.USER_STRING);
    	String aPassword = (String)aMap.get(CommonConst.PASSWORD_STRING);
    	String aTermType = (String)aMap.get(CommonConst.TERM_TYPE);
    	try {
            mTargetDBInfo = new DBInfo();
            mTargetDBInfo.setDBType(aDBType);
            mTargetDBInfo.setPassword(aPassword);
            mTargetDBInfo.setPort(aPort);
            mTargetDBInfo.setServerName(aSid);
            mTargetDBInfo.setTermType(aTermType);
            mTargetDBInfo.setUserName(aUser);
            mTargetDBInfo.setmHostName(aHost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Connect Target DB
    /**
     * @return
     * @throws Exception
     */
    public boolean connectToTargetDB() throws Exception{
        boolean ret = false;
    	mTargetDBConnection = new DatabaseConnection(mTargetDBInfo);
    	mTargetDBConnection.connect(mTargetDBConnection.getMasterUsername(), mTargetDBConnection.getMasterPassword());
    	ret = true;
        return ret;
    }

    // Return TargetDBConnection
    /**
     * @return
     */
    public DatabaseConnection getTargetDBConnection() {
        return mTargetDBConnection;
    }

    public void rollback() throws Exception {
        this.mDBUtil.rollback(this.mTargetDBConnection);
    }

    public TreeMap getCatalog() throws Exception {
       return this.mDBUtil.getTableAndViews(this.mTargetDBConnection);
    }
    
    public String getSQLKeyword() throws SQLException{
        return this.mTargetDBConnection.getJDBCConnection().getMetaData().getSQLKeywords();                
    }
    
    public int getSQLStateType() throws SQLException{
        return this.mTargetDBConnection.getJDBCConnection().getMetaData().getSQLStateType();
    }

    void releaseTargetDB() {
        try {
            this.mTargetDBConnection.getJDBCConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param arrList
     * @return
     */
    private Vector toVector(ArrayList arrList){
    	Vector data = new Vector();
    	
    	Iterator dataIt = arrList.iterator();
    	ArrayList currentRow = null;
    	Iterator rowIt = null;
    	String colValue = null;
    	while(dataIt.hasNext()){
            currentRow = (ArrayList)dataIt.next();
            rowIt = currentRow.iterator();
            Vector row = new Vector();
            while(rowIt.hasNext()){    			
                    colValue = (String)rowIt.next();
                    row.addElement(colValue);
            }
            data.addElement(row);
    	}
    	return data;
    }

//    /**
//     * @param arrList
//     * @return
//     */
//    private Vector toVector(ArrayList arrList, int aColNum){
//    	Vector data = new Vector();
//
//    	Iterator dataIt = arrList.iterator();
//    	ArrayList currentRow = null;
//    	Iterator rowIt = null;
//    	String colValue = null;
//    	while(dataIt.hasNext()){
//            currentRow = (ArrayList)dataIt.next();
//            Vector row = new Vector();
//            colValue = (String)currentRow.get(aColNum);
//            row.addElement(colValue);
//            data.addElement(row);
//    	}
//    	return data;
//    }

//    /**
//     * @param sQry
//     * @return
//     * @throws Exception
//     */
//    public Vector getDatum(String sQry) throws Exception{
//    	Vector ret = new Vector();
//	ArrayList sourceList = mDBUtil.commonSelectList(sQry, mTargetDBConnection);
//	ret = toVector(sourceList);
//	return ret;
//    }
    
    public HashMap getDatumMap(String sQry) throws Exception{
    	HashMap ret = mDBUtil.commonSelectMap(sQry, mTargetDBConnection);
	return ret;
    }
    
    
//    /**
//     * @param sQry
//     * @return
//     * @throws SQLException
//     */
//    private Vector getColumnNames(String sQry) throws SQLException {
//    	Vector ret = new Vector();
//	ret = mDBUtil.getColumnNames(sQry, mTargetDBConnection);
//	return ret;
//    }
//
//	/**
//	 * @return
//	 * @throws Exception
//	 */
//    public Vector getTableNames() throws Exception {
//	Vector ret = new Vector();
//	ret = getDatum(QueryManager.getTableNames());
//	return ret;
//    }

    /**
     * @param aClass
     * @return
     */
    public DefaultComboBoxModel getConstComboBoxModel(Object aClass){
        Object[] aFieldArr = aClass.getClass().getDeclaredFields();
        String[] aTempArr = new String[aFieldArr.length];
        String aFieldStr = null;
        for(int i=0; i<aFieldArr.length;i++){
            aFieldStr = ((Field)aFieldArr[i]).toString();
            aTempArr[i] = aFieldStr.substring(aFieldStr.lastIndexOf('.')+1);
        }
        comboBoxModel = new DefaultComboBoxModel(aTempArr);
        return comboBoxModel;
    }

    public int setDatum(String sQry) throws Exception {
        int ret = 0;
        ret = this.mDBUtil.commonUpdate(sQry, this.mTargetDBConnection);
        return ret;
    }

    public void commit() throws Exception{
        this.mDBUtil.commit(this.mTargetDBConnection);
    }

    public void cancelExecute() throws SQLException{
        this.mDBUtil.cancelExecute();
    }

    public HashMap getTableInfo(String selectedNodeName) {
        return this.mDBUtil.getTableInfo(selectedNodeName, this.mTargetDBConnection);
    }
}
