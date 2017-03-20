/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jay.util;

import com.jay.gui.InformDialog;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author Jay
 */
public class CommonUtil {
    Dimension dm = null;
    
    public CommonUtil(){
        Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public static String toString(Object s) {
            return (s == null) ? "" : s.toString();
    }

    /**
        *******************************************************************
        * �ش� String�� byte��ŭ �߶� ����Ÿ ����
        *******************************************************************
        * @param   String      data      	���ڿ�.
        * @param   int         offset      ���� 'ġ ó=���� �ڸ���� 0
        * @param   int         length      �ڸ� byte().length
        * @param   String      charset     charset
        * @return  String      result      length��ŭ �ڸ� ���ڿ�
        */
    public static String makeString(String data, int offset, int length, String charset) throws Exception{
        byte [] b = null;
        byte [] ret = null;
        String result = "";

        try{
            if (charset == null) {
                    charset = CommonConst.ENCODING;
            }
            b = data.getBytes();
            ret = new byte[length];

            if( b.length < length) {
                int space = length - b.length;
                System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
                result = new String(b);
                result = result + lpad(" ", space," ");
                return result;
            } else if( b.length <= offset ) { // �����ͱ��̺��� �ڸ��t� offset�� Ŀ��� ���� �����͸� �����ŭ�� ���8�� ä���.
                return lpad(" ", length," ");
            } else {
                System.arraycopy( b,
                offset,
                ret,
                0,
                length);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        result = new String(ret);

        return result;
    }


    /**
    *******************************************************************
    * �6�Ŭ lpad�� ���� �մϴ�.
    *******************************************************************
    * @param   String      strsrc      ���ڿ�.
    * @param   int         ilen        ���̹���Ʈ
    * @param   String      rpcstr      ����.
    * @return  String      result      ���.
    */
    public static String lpad(String srcstr, int ilen, String rpcstr) {

        String strblank = "";
        int ibyte = 0;

        try {

            ibyte = lenB(srcstr);
            //ibyte = len(srcstr);

            if (ibyte < ilen)
                    for (int i=ibyte; i<ilen; i++)
                            strblank = strblank + rpcstr;

            srcstr = strblank + srcstr;

            return srcstr;

        } catch (Exception err) {

            return srcstr;
        }

    }

    public static int lenB(String srcstr) {
        byte[] bytesrc;
        int ibyte = 0;

        try {
            //bytesrc = strsrc.getBytes();			//
            bytesrc = srcstr.getBytes("KSC5601");	//
            ibyte = bytesrc.length;

            return ibyte;
        } catch (Exception err) {
            return ibyte;
        }
    }

    public static String replace(String s, String old, String replacement) {
        int i = s.indexOf(old);
        StringBuilder r = new StringBuilder();
        if (i == -1) return s;
        r.append(s.substring(0,i)).append(replacement);
        if (i + old.length() < s.length())
           r.append(replace(s.substring(i + old.length(), s.length()), old, replacement));
        return r.toString();
    }

    public static String getCurrentTime(String aFormat){
    	String currTime = null;
        Calendar ca = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        currTime = new SimpleDateFormat(aFormat).format(ca.getTime());
        ca = null;
        return currTime;
    }

    public static ArrayList getArrayList(ResultSet aRset) throws SQLException{
        ArrayList ret = new ArrayList();
        ResultSetMetaData rsmd = aRset.getMetaData();
        int iColCnt = rsmd.getColumnCount();
        while(aRset.next()){
            ArrayList aRow = new ArrayList();
            for(int i=1;i<=iColCnt;i++){
                aRow.add(aRset.getString(i));
            }
            ret.add(aRow);
        }
        return ret;
    }
    
    public static String makeUniqueID(){
        StringBuilder sUniqueID = new StringBuilder();
    	Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strMillSec = sdf.format(dt);
        sUniqueID.append(strMillSec);
        int iTempIdx = 0;
        while(true){
            iTempIdx = (int)(Math.random() * 100);
            if(iTempIdx<CommonConst.sAlphabetChar.length){
                sUniqueID.append(CommonConst.sAlphabetChar[iTempIdx]);
            }
            if(sUniqueID.length()==24) break;
        }
        sUniqueID.append(((int)(Math.random() * 100)));
        return sUniqueID.toString();
    }
    
    private File setFileChooser(JFileChooser jf, String sFileType, Object mOwner){
        File ret=null;
        jf.setToolTipText("Save "+sFileType+" file as");
        jf.setApproveButtonText("Save");
        jf.showSaveDialog((Component)mOwner);
        jf.setDialogTitle("Save "+sFileType+" file as...");
        jf.setDialogType(JFileChooser.FILES_AND_DIRECTORIES);
        ret = jf.getSelectedFile();
        if(ret!=null && !(ret.getName().toUpperCase().endsWith(sFileType))) {
            ret = new File(ret.getAbsolutePath()+CommonConst.CURRENT_DIR+sFileType);
        }
        return ret;
    }
    
    public boolean export2File(HashMap oDatum, String sFileType, Object mOwner, String aTitle) throws JayException{
        boolean ret = false;
        FileWriter fileWriter = null;
        try {
            JFileChooser jf = new JFileChooser();
            File aFile = setFileChooser(jf, sFileType, mOwner);
            if(aFile!=null){
                if(aFile.exists()) {
                    if(JOptionPane.showConfirmDialog((Component)mOwner,aFile.getName()+" already exists."+System.getProperty("line.separator")+
                    "Do you want to replace it ?","Export",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==1) {
                        aFile = setFileChooser(jf, sFileType, mOwner);
                    }
                }

                Iterator it = oDatum.keySet().iterator();
                String sKey = null;
                String innerKey, innerValue = null;
                HashMap innerMap = null;
                Iterator itt = null;
                fileWriter = new FileWriter(aFile);
                StringBuilder buffer = new StringBuilder();
                if(CommonConst.XML_STRING.equalsIgnoreCase(sFileType)){
                    buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    buffer.append("<ROWSET>");
                    while(it.hasNext()){
                        buffer.append("<Row>");
                        sKey = (String)it.next();
                        innerMap = (HashMap)oDatum.get(sKey);
                        itt = innerMap.keySet().iterator();
                        while(itt.hasNext()){
                            innerKey = (String)itt.next();
                            innerValue = innerMap.get(innerKey).toString();                    
                            if(innerValue != null) {
                                buffer.append("<").append(innerKey).append(">");
                                buffer.append(innerValue);
                                buffer.append("</").append(innerKey).append(">");
                            } else {
                                buffer.append("<").append(innerKey).append(">");
                                buffer.append("</").append(innerKey).append(">");
                            }
                        }
                        buffer.append("</Row>");
                        buffer.append(System.getProperty("line.separator"));
                    }
                    buffer.append("</ROWSET>");
                }else if(CommonConst.CSV_STRING.equalsIgnoreCase(sFileType)){
                    sKey = (String)it.next();
                    innerMap = (HashMap)oDatum.get(sKey);
                    itt = innerMap.keySet().iterator();
                    while(itt.hasNext()){
                        innerKey = (String)itt.next();
                        buffer.append(innerKey);
                        buffer.append(",");    	
                    }
                    buffer.delete(buffer.length()-1, buffer.length());    	
                    buffer.append(System.getProperty("line.separator"));

                    it = oDatum.keySet().iterator();
                    while(it.hasNext()){
                        sKey = (String)it.next();
                        innerMap = (HashMap)oDatum.get(sKey);
                        itt = innerMap.keySet().iterator();
                        while(itt.hasNext()){
                            innerKey = (String)itt.next();
                            innerValue = innerMap.get(innerKey).toString();                    
                            if(innerValue != null) {
                                buffer.append(innerValue);
                                buffer.append(",");    
                            } else {
                                buffer.append(",");    
                            }
                        }
                        buffer.delete(buffer.length()-1, buffer.length());    
                        buffer.append(System.getProperty("line.separator"));
                    }
                }else if(CommonConst.XLS_STRING.equalsIgnoreCase(sFileType)){
                    WritableWorkbook workbook = Workbook.createWorkbook(aFile);
                    WritableSheet sheet = workbook.createSheet(aTitle, 0);
                    int iRowCnt=0;
                    while(it.hasNext()){
                        sKey = (String)it.next();
                        innerMap = (HashMap)oDatum.get(sKey);
                        itt = innerMap.keySet().iterator();
                        int iColCnt=0;
                        while(itt.hasNext()){
                            innerKey = (String)itt.next();
                            innerValue = innerMap.get(innerKey).toString();
                            if(iRowCnt==0){
                                sheet.addCell(new jxl.write.Label(iColCnt,0,innerKey));
                                sheet.addCell(new jxl.write.Label(iColCnt,1,innerValue));  
                            }else{
                                if(innerValue != null) {
                                    sheet.addCell(new jxl.write.Label(iColCnt,iRowCnt,innerValue));                                    
                                }else
                                    sheet.addCell(new jxl.write.Label(iColCnt,iRowCnt,"")); 
                            }
                            iColCnt++;
                        }
                        if(iRowCnt==0) iRowCnt=1;
                        iRowCnt++;
                    }
                    workbook.write();
                    workbook.close();
                }
                fileWriter.write(buffer.toString());
                fileWriter.flush();
                fileWriter.close(); 
                ret = true;
            }            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JayException(ex);
        } finally {
            try {
                if(fileWriter != null)fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    
    public HashMap makeJTable2HashMap(JTable object){
        HashMap ret = new HashMap();
        int[] rows = object.getSelectedRows();
     
        if(rows.length==0) {
            if(JOptionPane.showConfirmDialog((Component)(object.getRootPane()),"No rows selected. Export the whole table ?","Export",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
                rows = new int[object.getRowCount()];
                for(int i=0;i<object.getRowCount();i++) {
                    rows[i]=i;
                }
            } else {
                // Export Aborted
                rows=null;
            }
        }
        
        if(rows!=null && rows.length > 0) {
            for(int i=0;i<rows.length;i++) {
                HashMap aColsMap = new HashMap();
                for(int j=0;j<object.getColumnCount();j++) {
                    // Write the content
                    aColsMap.put(object.getModel().getColumnName(j), object.getValueAt(rows[i],j).toString());
                 }
                ret.put(String.valueOf(i), aColsMap);
            }
        }
        return ret;
    }
    
    public boolean export2XML(JTable jTable, Object mOwner) throws JayException{
        return export2File(makeJTable2HashMap(jTable),CommonConst.XML_STRING, mOwner, null);
    }
    
    public boolean export2CSV(JTable jTable, Object mOwner) throws JayException{
        return export2File(makeJTable2HashMap(jTable),CommonConst.CSV_STRING, mOwner, null);
    }
    
    public boolean export2SpreadSheet(JTable jTable, String aTitle, Object mOwner) throws JayException{
        return export2File(makeJTable2HashMap(jTable),CommonConst.XLS_STRING, mOwner, aTitle);
    }
    
    
    
    public static void main(String[] args){
        CommonUtil cu = new CommonUtil();
        cu.printSystemInfo();
    }
    
    public void printSystemInfo(){
        Properties prop = System.getProperties();
        Iterator it = prop.keySet().iterator();
        String aTempKey = null;
        System.out.println("System properties");
        while(it.hasNext()){
            aTempKey = (String)it.next();
            System.out.println("Key:"+aTempKey+"==>"+prop.get(aTempKey));

        }
        Map env = System.getenv();
        it = env.keySet().iterator();
        System.out.println("System env");
        while(it.hasNext()){
            aTempKey = (String)it.next();
            System.out.println("Key:"+aTempKey+"==>"+env.get(aTempKey));
        }
    }
    
    public static void getSystemProps(){
        Properties pt = System.getProperties();
        Iterator it = pt.keySet().iterator();
        String tempKey = null;
        while(it.hasNext()){
            tempKey = (String)it.next();
            System.out.println("propKey:"+tempKey+":"+System.getProperty(tempKey));
        }
    }
    
    public static void getSystemEnvs(){
        String tempKey = null;
        Map envMap = System.getenv();
        Iterator it = envMap.keySet().iterator();
        while(it.hasNext()){
            tempKey = (String)it.next();
            System.out.println("envKey:"+tempKey+":"+System.getenv(tempKey));
        }
    }
}
