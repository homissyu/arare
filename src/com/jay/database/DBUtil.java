package com.jay.database;

/**
 * @ï¿½ï¿½ï¿½Ï¸ï¿½ DBUtil.java
 * @author  Jay
 * @project JAYETL
 * @ï¿½Û¼ï¿½ï¿½ï¿½ï¿½ï¿½2005.11.02
 * 
 */

import com.jay.util.CommonConst;
import com.jay.util.CommonUtil;
import com.jay.util.JayException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class DBUtil {

private String SUBSYSTEM = "DBUtil";

    public Statement mActionStatement = null;

    public void cancelExecute() throws SQLException {
        mActionStatement.cancel();
    }

    public HashMap getTableAndViews(DatabaseConnection aDBconnection) throws JayException {
        HashMap ret = new HashMap();
        ResultSet rset = null;
        ResultSetMetaData rsmd = null;
        DatabaseMetaData dmd = null;
        ResultSet rs = null;
        ArrayList aTempList = null;
        Iterator it = null;
        
        try {
            Connection conn = aDBconnection.getJDBCConnection();
            if(conn== null){ 
                throw new JayException("Connection is null");
            }
            dmd = conn.getMetaData();
            
            if(dmd.getDatabaseProductName().equalsIgnoreCase(CommonConst.DATABASE_TYPE[2]))
                rs = dmd.getCatalogs();
            else
                rs = dmd.getSchemas();
                
            aTempList = CommonUtil.getArrayList(rs);

            String aTempSchema;
/**
 * to remove non-user's schemas
 * */
//            switch(aDBconnection.getDBType()){
//                case CommonConst.ORACLE_TYPE:
//                    aTempSchema = aDBconnection.getUsername();
//                    break;
//                case CommonConst.MSSQLSERVER_TYPE:
//                    aTempSchema = CommonConst.MSSQL_DBO;
//                    break;
//                default:
//                    aTempSchema = aDBconnection.getServerName();
//                    break;                   
//            }
            
            String aTempObjCategory = null;
            String aTempObjScheme = null;
            String aTempObjName = null;
            String aTempObjType = null;
            String aTempObjDesc = null;
            
            it = aTempList.iterator();
            
            while(it.hasNext()){
                ArrayList aRow = (ArrayList)it.next();
                aTempSchema = (String)aRow.get(0);
//                System.out.println(aTempSchema);
//                rs = dmd.getTablePrivileges(null, aTempSchema, "%");
//                while(rs.next()){
//                    System.out.println(
//                            rs.getString(1)+":"+
//                            rs.getString(2)+":"+
//                            rs.getString(3)+":"+
//                            rs.getString(4)+":"+
//                            rs.getString(5)+":"+
//                            rs.getString(6)+":"+
//                            rs.getString(7)
//                            );
//                }
                
                
                rset = dmd.getTables(null,aTempSchema,"%",null);
                
                rsmd = rset.getMetaData();
                
//                for(int i=0;i<rsmd.getColumnCount();i++){
//                    System.out.println(rsmd.getColumnName(i+1));
//                }
                
                HashMap aCols = new HashMap();
                while(rset.next()) {
                    if(aTempSchema.equals(aTempObjCategory = rset.getString(rsmd.getColumnLabel(1)))){;
                        aTempObjScheme = rset.getString(rsmd.getColumnLabel(2));
                        aTempObjName = rset.getString(rsmd.getColumnLabel(3));
                        aTempObjType = rset.getString(rsmd.getColumnLabel(4));
                        aTempObjDesc = rset.getString(rsmd.getColumnLabel(5));
                        HashMap aObjMap = new HashMap();
                        aObjMap.put(CommonConst.CATALOG_INFO[3], aTempObjCategory);
                        aObjMap.put(CommonConst.CATALOG_INFO[4], aTempObjScheme);
                        aObjMap.put(CommonConst.CATALOG_INFO[0], aTempObjName);
                        aObjMap.put(CommonConst.CATALOG_INFO[2], aTempObjDesc);

                        if(aCols.containsKey(aTempObjType)){
                            ((ArrayList)aCols.get(aTempObjType)).add(aObjMap);
                        }else{
                            ArrayList innerColsList = new ArrayList();
                            innerColsList.add(aObjMap);
                            aCols.put(aTempObjType, innerColsList);
                        }
                    }
                }
//                System.out.println(aCols);
                if(aCols.size() == 0) aTempSchema = aTempSchema + CommonConst.NO_PERMISSION;
                ret.put(aTempSchema, aCols);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JayException(ex);
        }
//        System.out.println(ret);
        return ret;
    }
	
    /**
     * @param sQry
     * @param aDBConnection
     * @return
     * @throws SQLException
     */
    private PreparedStatement setPreparedStatement(String sQry, DatabaseConnection aDBConnection) throws SQLException{
        PreparedStatement pstmt = (aDBConnection.getJDBCConnection()).prepareStatement(sQry);
        mActionStatement = pstmt;
        return pstmt;
    }
	
    /**
     * @param sQry
     * @param sArg
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    private PreparedStatement setPreparedStatement(String sQry, String [] sArg, DatabaseConnection aDBConnection) throws Exception{
        PreparedStatement pstmt = (aDBConnection.getJDBCConnection()).prepareStatement(sQry,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        for(int i=0;i<sArg.length;i++){
            pstmt.setString(i+1,sArg[i]);
        }
        return pstmt;
    }
	
    /**
     * @param sQry
     * @param vArg
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    private PreparedStatement setPreparedStatement(String sQry, Vector vArg, DatabaseConnection aDBConnection) throws Exception{
        PreparedStatement pstmt = (aDBConnection.getJDBCConnection()).prepareStatement(sQry,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        for(int i=0;i<vArg.size();i++){
            pstmt.setString(i+1,(String)vArg.get(i));
        }
        return pstmt;
    }
	
    /**
     * @param sQry
     * @param aArg
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    private PreparedStatement setPreparedStatement(String sQry, ArrayList aArg, DatabaseConnection aDBConnection) throws Exception{
        PreparedStatement pstmt = (aDBConnection.getJDBCConnection()).prepareStatement(sQry,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        for(int i=0;i<aArg.size();i++){
            pstmt.setString(i+1,(String)aArg.get(i));
        }
        return pstmt;
    }
	
    /**
     * @param sQry
     * @param sArg
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    private PreparedStatement setPreparedStatement(String sQry, String sArg, DatabaseConnection aDBConnection) throws Exception{
        PreparedStatement pstmt = (aDBConnection.getJDBCConnection()).prepareStatement(sQry,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        pstmt.setString(1,sArg);
        return pstmt;
    }
	
    /**
     * @param sQry
     * @param colIdx
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public String getCommonData(String sQry, int colIdx, DatabaseConnection aDBConnection) throws Exception{
        String ret = null;
        HashMap cols = null;
        String key = null;
        
        ArrayList result = new ArrayList(); 
        result = commonSelect(sQry, aDBConnection);
        Iterator rowIt = result.iterator();
        
        while(rowIt.hasNext()){
            cols = (HashMap)rowIt.next();
            Iterator colsIt = cols.keySet().iterator();
            for(int i=1;i<=cols.keySet().size();i++){
                if(i == colIdx){
                    key = (String)colsIt.next();
                    ret = (String)cols.get(key);
                }
            }
        }
        
        result = null;
        return ret;
    }

    /**
     * @param sQry
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelectList(String sQry, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery4List(setPreparedStatement(sQry, aDBConnection));        
        return ret;
    }
    
    public HashMap commonSelectMap(String sQry, DatabaseConnection aDBConnection) throws Exception{
        HashMap ret = new HashMap();
        ret = executeQuery4Map(setPreparedStatement(sQry, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect4List(String sQry, String [] sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery4List(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect4List(String sQry, Vector sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery4List(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect4List(String sQry, ArrayList sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery4List(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     *<B> For APOLLO </B>
     *
     * @param sQry
     * @param aDBConnection
     * @param bSync
     * @return
     * @throws Exception
     */
    public ArrayList commonSelectList(String sQry, DatabaseConnection aDBConnection, boolean bSync) throws Exception{
        ArrayList ret = new ArrayList();		
        if(bSync){
                ret = executeQuery4ListSync(setPreparedStatement(sQry, aDBConnection));
        }else{
                ret = executeQuery4List(setPreparedStatement(sQry, aDBConnection));
        }
        return ret;
    }
	
    /**
     * @param sQry
     * @param aDBConnection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList commonSelect(String sQry, DatabaseConnection aDBConnection) throws SQLException, IOException{
        ArrayList ret = new ArrayList();
        ret = executeQuery(setPreparedStatement(sQry, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect(String sQry, String [] sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect(String sQry, Vector sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public ArrayList commonSelect(String sQry, ArrayList sVal, DatabaseConnection aDBConnection) throws Exception{
        ArrayList ret = new ArrayList();
        ret = executeQuery(setPreparedStatement(sQry, sVal, aDBConnection));
        return ret;
    }
	
    /**
     * @param sQry
     * @param mData
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonInsert(String sQry, ArrayList mData, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, aDBConnection), mData);
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param mData
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonInsertMap(String sQry, ArrayList mData, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdateMap(setPreparedStatement(sQry, aDBConnection), mData);
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param mData
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int[] commonInsertBatch(String sQry, ArrayList mData, DatabaseConnection aDBConnection) throws Exception{
        int[] iCnt = executeBatch(setPreparedStatement(sQry, aDBConnection), mData);
        return iCnt;
    }
	
    /**
     *<B> For APOLLO </B>
     * @param sQry
     * @param mData
     * @param aDBConnection
     * @param aItemCode
     * @return
     * @throws Exception
     */
    public int commonInsert(String sQry, ArrayList mData, DatabaseConnection aDBConnection, String aItemCode) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, aDBConnection), mData, aItemCode);
        return iCnt;
    }	
	
    /**
     *<B> For APOLLO </B>
     * @param sSiteCode
     * @param sQry
     * @param mData
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonInsert(String sSiteCode, String sQry, ArrayList mData, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(sSiteCode,setPreparedStatement(sQry, aDBConnection), mData);
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonUpdate(String sQry, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, aDBConnection));
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonUpdate(String sQry, String sVal, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, sVal, aDBConnection));
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonUpdate(String sQry, String [] sVal, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, sVal, aDBConnection));
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonUpdate(String sQry, Vector sVal, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, sVal, aDBConnection));
        return iCnt;
    }
	
    /**
     * @param sQry
     * @param sVal
     * @param aDBConnection
     * @return
     * @throws Exception
     */
    public int commonUpdate(String sQry, ArrayList sVal, DatabaseConnection aDBConnection) throws Exception{
        int iCnt = executeUpdate(setPreparedStatement(sQry, sVal, aDBConnection));
        return iCnt;
    }
	
    /**
     * @param pstmt
     * @return
     * @throws SQLException
     * @throws IOException
     */
    private ArrayList executeQuery(PreparedStatement pstmt) throws SQLException, IOException{
        ArrayList ret = new ArrayList();
        
        ResultSet rset = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        
        int colCnt = rsmd.getColumnCount();
        int rowNum = 0;
        
        while(rset.next()){
            rowNum++;
            HashMap cols = new HashMap();
            for(int i=1;i<=colCnt;i++){
                cols.put(rsmd.getColumnName(i),rset.getString(rsmd.getColumnName(i)));
            }
            ret.add(cols);
        }
        pstmt.close();
        pstmt = null;
        return ret;        
    }
    
    private HashMap executeQuery4Map(PreparedStatement pstmt) throws SQLException, JayException{
        HashMap ret = new HashMap();
        Vector colNames = new Vector();
        ArrayList rows = new ArrayList();
        int rowCnt = 0;
        
        ret.put(CommonConst.COLUMNNAMES, colNames);
        ret.put(CommonConst.DATUM, rows);
        
        ResultSet rset = null;
        ResultSetMetaData rsmd = null;
        pstmt.execute();
        rset = pstmt.getResultSet();
        rsmd = rset.getMetaData();

        int colCnt = rsmd.getColumnCount();


        for(int i=1;i<=colCnt;i++){
            colNames.addElement(rsmd.getColumnName(i));
        }

        while(rset.next()){
            rowCnt++;
            ArrayList row = new ArrayList();
            for(int i=1;i<=colCnt;i++){
                row.add(i-1, rset.getString(rsmd.getColumnName(i)));
            }
            rows.add(row);
        }
        
        ret.put(CommonConst.ROW_CNT, rowCnt);
            
        try {
            rset.close();
            pstmt.close();
            pstmt = null;
        } catch (SQLException ex) {
            throw new JayException(ex);
        }
        return ret; 
        
    }
	
    /**
     * @param pstmt
     * @return
     * @throws SQLException
     * @throws IOException
     */
    private ArrayList executeQuery4List(PreparedStatement pstmt) throws SQLException, JayException{
        ArrayList ret = new ArrayList();
        ResultSet rset = null;
        ResultSetMetaData rsmd = null;
        pstmt.execute();
        rset = pstmt.getResultSet();
        rsmd = rset.getMetaData();

        int colCnt = rsmd.getColumnCount();

        while(rset.next()){
            ArrayList cols = new ArrayList();
            for(int i=1;i<=colCnt;i++){
                cols.add(i-1, rset.getString(rsmd.getColumnName(i)));
            }
            ret.add(cols);
        }

        try {
            rset.close();
            pstmt.close();
            pstmt = null;
        } catch (SQLException ex) {
            throw new JayException(ex);
        }
        return ret;        
    }
	
    /**
     *<B> For APOLLO </B>
     * @param pstmt
     * @return
     * @throws SQLException
     * @throws IOException
     */
    private ArrayList executeQuery4ListSync(PreparedStatement pstmt) throws SQLException, IOException{
        ArrayList ret = new ArrayList();
        
        ResultSet rset = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        
        int colCnt = rsmd.getColumnCount();
        int rowNum = 0;
        
        while(rset.next()){
            rowNum++;
            ArrayList cols = new ArrayList();
            for(int i=1;i<colCnt;i++){
                cols.add(i-1, rset.getString(rsmd.getColumnName(i)));
            }
            ret.add(cols);
    
            pstmt.close();
            pstmt = null;      
        }
        return ret;  
    }
	
    /**
     * @param pstmt
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private int executeUpdate(PreparedStatement pstmt) throws ClassNotFoundException, SQLException, IOException, JayException{
        int iCnt = pstmt.executeUpdate();
        pstmt.close();
        pstmt = null;
        return iCnt;
    }
	
    /**
     * @param pstmt
     * @param mData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private int executeUpdate(PreparedStatement pstmt, ArrayList mData) throws ClassNotFoundException, SQLException, IOException, JayException{
        int iCnt = 0;
        int iColCnt = 0;
        Iterator rowIt = mData.iterator();
        String temp = null;
        while(rowIt.hasNext()){
            ArrayList rows = (ArrayList)rowIt.next();
            iColCnt = rows.size();
            for(int i=0;i<iColCnt;i++){
            	temp = (String)rows.get(i);
            	pstmt.setCharacterStream(i+1, temp==null?null:new StringReader(temp), temp==null?0:(temp.getBytes()).length);
            }
            try{
            	iCnt += pstmt.executeUpdate();
            }catch(SQLException e){   
                Iterator it = rows.iterator(); 
            	while(it.hasNext()){
                    System.err.println("Exception occured rows : column : "+(String)it.next());
            	}
                rows = null;
                throw new JayException(e);
            }
        }
        pstmt.close();
        pstmt = null;
        mData.clear();
        return iCnt;
    }

    /**
     * @param pstmt
     * @param mData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private int executeUpdateMap(PreparedStatement pstmt, ArrayList mData) throws ClassNotFoundException, SQLException, IOException, JayException{
        int iCnt = 0;
        int iColCnt = 0;
        Iterator rowIt = mData.iterator();
        String tempValue = null;
        String tempCol = null;
        Iterator colIt = null;
        while(rowIt.hasNext()){
            HashMap rows = (HashMap)rowIt.next();
            iColCnt = rows.size();
            colIt.remove();
            colIt = rows.keySet().iterator();
            
            while(colIt.hasNext()){
            	iColCnt = 0;
            	tempCol = (String)colIt.next();
            	tempValue = (String)rows.get(tempCol);
            	pstmt.setCharacterStream(iColCnt+1, tempValue==null?null:new StringReader(tempValue), tempValue==null?0:(tempValue.getBytes()).length);
            	iColCnt++;
            }
            
            try{
            	iCnt += pstmt.executeUpdate();
            }catch(SQLException e){   
            	colIt.remove();
            	colIt = rows.keySet().iterator(); 
            	while(colIt.hasNext()){
                    tempCol = (String)colIt.next();
                    tempValue = (String)rows.get(tempCol);
                    System.err.println("Exception occured rows : column : "+tempCol);
            	}
            	rows = null;
                throw new JayException(e);
            }
        }
        pstmt.close();
        pstmt = null;
        mData.clear();
        return iCnt;
    }
	
    /**
     * @param pstmt
     * @param mData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private int[] executeBatch(PreparedStatement pstmt, ArrayList mData) throws ClassNotFoundException, SQLException, IOException{
        int[] iCnt = null;
        int iColCnt = 0;
        Iterator rowIt = mData.iterator();
        String temp = null;
        while(rowIt.hasNext()){
            ArrayList rows = (ArrayList)rowIt.next();
            iColCnt = rows.size();
            for(int i=0;i<iColCnt;i++){
            	temp = (String)rows.get(i);
            	pstmt.setCharacterStream(i+1, temp==null?null:new StringReader(temp), temp==null?0:(temp.getBytes()).length);
            	pstmt.addBatch();
            }            
            iCnt = pstmt.executeBatch();
        }
        pstmt.close();
        pstmt = null;
        mData.clear();
        return iCnt;
    }
	
    /**
     *<B> For APOLLO </B>
     * @param pstmt
     * @param mData
     * @param aItemCode
     * @return
     * @throws SQLException
     */
    private int executeUpdate(PreparedStatement pstmt, ArrayList mData, String aItemCode) throws SQLException, JayException{
        int iCnt = 0;
        int iColCnt = 0;
        Iterator rowIt = mData.iterator();
        String temp = null;
        
        while(rowIt.hasNext()){
        	ArrayList rows = (ArrayList)rowIt.next();
            iColCnt = rows.size();
            for(int i=0;i<iColCnt;i++){
            	temp = (String)rows.get(i);
            	pstmt.setCharacterStream(i+1, temp==null?null:new StringReader(temp), temp==null?0:(temp.getBytes()).length);
            }
            try{
            	iCnt += pstmt.executeUpdate();
            }catch(SQLException e){
            	Iterator it = rows.iterator(); 
            	while(it.hasNext()){
                    System.err.println("Exception occured rows : column : "+(String)it.next());
            	}
            	rows = null;
                throw new JayException(e);
            }
        }
        pstmt.close();
        pstmt = null;
        mData.clear();
        return iCnt;
    }
	
    /**
     *<B> For APOLLO </B>
     * @param sSite_code
     * @param pstmt
     * @param mData
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private int executeUpdate(String sSite_code, PreparedStatement pstmt, ArrayList mData) throws ClassNotFoundException, SQLException, IOException{
        int iCnt = 0;
        int iColCnt = 0;
        String colValue = null;
        Iterator rowIt = mData.iterator();
        while(rowIt.hasNext()){
            ArrayList rows = (ArrayList)rowIt.next();
            iColCnt = rows.size();
            colValue = null;
            pstmt.setString(1,sSite_code);
            for(int i=1;i<iColCnt;i++){
            	colValue = (String)rows.get(i);
            	pstmt.setString(i+1,colValue);
            }
            iCnt += pstmt.executeUpdate();
        }
        pstmt.close();
        pstmt = null;
        return iCnt;
    }
	
    /**
     * @param aDBConnection
     * @throws Exception
     */
    public void commit(DatabaseConnection aDBConnection) throws Exception{
        aDBConnection.commit();
    }

    /**
     * Rollback
     * @param aDBConnection
     * @throws Exception
     */
    public void rollback(DatabaseConnection aDBConnection) throws Exception{
        aDBConnection.rollback();
    }
	
    /**
     * Column Type Return
     * @param table
     * @param column
     * @param aDBConnection
     * @return Column Type
     * @throws SQLException
     * @throws Exception
     */
    public String getColumnType(String table, String column, DatabaseConnection aDBConnection) throws SQLException, Exception{
        String ret = null;
        String sQry = "SELECT " + column + " FROM " + table;
        PreparedStatement pstmt = setPreparedStatement(sQry, aDBConnection);
        pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        ret = rsmd.getColumnTypeName(1);
        pstmt.close();
        return ret;
    }	
	
    /**
     * ColumnNames Vector return
     * @param sQry
     * @param aDBConnection
     * @return ColumnNames Vector
     * @throws SQLException
     */
    public Vector getColumnNames(String sQry, DatabaseConnection aDBConnection) throws SQLException{
        Vector ret = new Vector();
        PreparedStatement pstmt = setPreparedStatement(sQry, aDBConnection);
        pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        int iCnt = rsmd.getColumnCount();
        for(int i=1;i<=iCnt;i++){
                ret.addElement(rsmd.getColumnName(i));
        }
        pstmt.close();
        return ret;        
    }

    public HashMap getTableInfo(String selectedNodeName, DatabaseConnection aDBConnection) {
        HashMap ret = new HashMap();
        Vector colNames = new Vector();
        ArrayList rows = new ArrayList();
        int rowCnt = 0;
        try{
            ret.put(CommonConst.COLUMNNAMES, colNames);
            ret.put(CommonConst.DATUM, rows);
            
            int colCnt = CommonConst.COLUMN_INFO.length;
            
            for(int i=0;i<colCnt;i++){
                colNames.addElement(CommonConst.COLUMN_INFO[i]);
            }
            
            DatabaseMetaData dmd = (aDBConnection.getJDBCConnection()).getMetaData();
            
            ResultSet rs;
            selectedNodeName = selectedNodeName.substring(selectedNodeName.indexOf(".")+1);
            rs = dmd.getColumns(null, null, selectedNodeName, null);

            while(rs.next()){
                rowCnt++;
                ArrayList row = new ArrayList();
                
                for(int i=0;i<colCnt;i++){
                    row.add(i, rs.getString(CommonConst.COLUMN_INFO[i]));
                }
                rows.add(row);
            }
            ret.put(CommonConst.ROW_CNT, rowCnt);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}