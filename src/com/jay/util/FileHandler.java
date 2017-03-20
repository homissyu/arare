package com.jay.util;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Jay
 * @date 2006. 6. 19.
 */

public class FileHandler {
    long lFileSize;
    static String sFileName;
    Hashtable hFileTable = null;
    private static final String sSubSystem = "FileHandler";

    public FileHandler(){
    }
    

    /**
     * @param sSourcePath
     * @param sTargetPath
     * @param saFileList
     * @throws IOException
     * @throws JayException
     */
    public void copyFile(String sSourcePath, String sTargetPath, String[] saFileList) throws IOException, JayException {
        for(int i=0;i<saFileList.length;i++) {
            File fInFile = new File(sSourcePath+File.separator+saFileList[i]);
            File fOutFile = new File(sTargetPath+File.separator+saFileList[i]);
            FileInputStream in = null;
            FileOutputStream out = null;
            byte[] buffer;
            int bytes_read;

            try {
                if(!fInFile.exists() || !fInFile.isFile())
                    throw new JayException("FileCopy:no such file or directory:"+"fInFile:"+sSourcePath+File.separator+saFileList[i]);
                if(!fInFile.canRead())
                    throw new JayException("FileCopy:source file is unreadable:"+"fInFile:"+sSourcePath+File.separator+saFileList[i]);
                in = new FileInputStream(fInFile);
                out = new FileOutputStream(fOutFile);

                buffer = new byte[(int)fInFile.length()];
                while(true) {
                    bytes_read = in.read(buffer);
                    if(bytes_read == -1)
                        break;
                }

                out.write(buffer);
                out.flush();
            }finally {
                if(in != null) in.close();
                if(out != null) out.close();
            }
        }        
    }
    
    /**
     * @param sSourcePath
     * @param sTargetPath
     * @throws IOException
     * @throws JayException
     */
    public void copyFile(String sSourcePath, String sTargetPath) throws IOException, JayException {
        File fInFile = new File(sSourcePath);
        File fOutFile = new File(sTargetPath);
        FileInputStream in = null;
        FileOutputStream out = null;
        byte[] buffer;
        int bytes_read;

        try {
            if(!fInFile.exists() || !fInFile.isFile())
                throw new JayException("FileCopy:no such file or directory:"+sSourcePath);
            if(!fInFile.canRead())
                throw new JayException("FileCopy:source file is unreadable:"+sSourcePath);
            in = new FileInputStream(fInFile);
            out = new FileOutputStream(fOutFile);

            buffer = new byte[(int)fInFile.length()];
            while(true) {
                bytes_read = in.read(buffer);
                if(bytes_read == -1)
                    break;
            }

            out.write(buffer);
            out.flush();
        }catch(Exception e){
            throw new JayException(e);
        }finally {
            if(in != null) in.close();
            if(out != null) out.close();
        }
    }

    /**
     * @param sFileName
     * @param content
     * @param sPath
     * @throws JayException
     */
    public void writeFile(String sFileName, String content, String sPath) throws JayException{
        String sFilePath = sPath + File.separator + sFileName;
        File file = null;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try{
            file = new File(sPath);
            if(!file.exists())
                file.mkdirs();
            file = new File(sFilePath);
            if(!file.exists())
                file.createNewFile(); 

            fos = new FileOutputStream(sFilePath);
            osw = new OutputStreamWriter(fos, CommonConst.ENCODING);
            bw = new BufferedWriter(osw);
            pw = new PrintWriter(bw);
            
            pw.print(content);

            pw.close();
            osw.close();
            bw.close();
            fos.close();
        }catch(IOException ex){
            throw new JayException(ex);
        }
    }
    
    public static void writeSerFile(String sFileName, Object obj, String sPath) throws JayException{
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File file = null;
        String sFilePath = sPath + File.separator + sFileName;
        try{
            file = new File(sPath);
            if(!file.exists())
                file.mkdirs();
            file = new File(sFilePath);
            if(!file.exists())
                file.createNewFile(); 
            
            fos = new FileOutputStream(sPath+File.separator+sFileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);

            oos.close();
            fos.close();
        }catch(IOException ex){
            throw new JayException(ex);
        }
    }
    
    public static Object readSerFile(String sFileName) throws JayException{
        FileInputStream fileIn = null;
        Object ret = null;
        File file = null;
        try {
            file = new File(sFileName);
            if(file.exists()){
                fileIn = new FileInputStream(sFileName);
                ObjectInputStream ois = new ObjectInputStream(fileIn);
                ret = ois.readObject();
            }
        } catch (Exception ex) {
            throw new JayException(ex);
        } finally {
            try {
                if(fileIn!=null)fileIn.close();
            } catch (IOException ex) {
                throw new JayException(ex);
            }
        }
        return ret;
    }
    
    /**
     * @return
     */
    public long getFileSize(){
        return lFileSize;
    }
    
    /**
     * @return
     */
    public String getFileName(){
        return sFileName;
    }

    /**
     * @param sFileName
     * @throws Exception
     */
    public static void remove(String sFileName) throws Exception{
        File fFile = new File(sFileName);
        fFile.delete();
    }

    /**
     * @param sFilePath
     * @param sFileName
     * @throws Exception
     */
    public void remove(String sFilePath, String sFileName) throws Exception{
        File fFile = new File(sFilePath+File.separator+sFileName);
        fFile.delete();
    }
    
    /**
     * @param sFilePath
     * @param vecFileList
     * @throws IOException
     * @throws JayException
     */
    public void removeFiles(String sFilePath, Vector vecFileList) throws IOException, JayException {
        for(int i=0;i<vecFileList.size();i++) {
            File fFile = new File(sFilePath+File.separator+vecFileList.elementAt(i).toString());
            fFile.delete();
        }   
        File fileDir = new File(sFilePath);
        if(fileDir.list().length == 0)
            fileDir.delete();
    }    
    
    /**
     * @param sSourcePath
     * @param sTargetPath
     * @param saFileList
     * @param saFileUploadList
     * @throws IOException
     * @throws JayException
     */
    public void copyFile(String sSourcePath, String sTargetPath, String[] saFileList, String[] saFileUploadList) throws IOException, JayException {
        for(int i=0;i<saFileList.length;i++) {
            File fInFile = new File(sSourcePath+File.separator+saFileUploadList[i]);
            File fOutFile = new File(sTargetPath+File.separator+saFileList[i]);
            FileInputStream in = null;
            FileOutputStream out = null;
            byte[] buffer;
            int bytes_read;
            try {
                if(!fInFile.exists() || !fInFile.isFile())
                    throw new JayException("FileCopy:no such file or directory:"+sSourcePath);
                if(!fInFile.canRead())
                    throw new JayException("FileCopy:source file is unreadable:"+sSourcePath);
                in = new FileInputStream(fInFile);
                out = new FileOutputStream(fOutFile);

                buffer = new byte[(int)fInFile.length()];
                while(true) {
                    bytes_read = in.read(buffer);
                    if(bytes_read == -1)
                        break;
                }

                out.write(buffer);
                out.flush();
            }finally {
                if(in != null) in.close();
                if(out != null) out.close();
            }
        }
    }
    
    public static Properties readProperyXML(String aFileName) throws JayException{
    	Properties prop = new Properties();
    	try {
            prop.loadFromXML(new FileInputStream(aFileName));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new JayException(e);
        } 
    	return prop;
    }

    /**
     * @param aMap
     * @param aFileName
     * @throws Exception
     */
    public static void makePropertyXML(HashMap aMap, String aFileName) throws Exception {
        remove(aFileName);
        Properties prop = new Properties();
        Iterator it = aMap.keySet().iterator();
        String aKey;
        FileOutputStream fOut = new FileOutputStream(aFileName);
        while(it.hasNext()){
            aKey = (String)it.next();
            prop.setProperty(Base64Encrypt.encode(aKey),Base64Encrypt.encode((String)aMap.get(aKey)));
        }
        prop.storeToXML(fOut,(String)aMap.get(CommonConst.SID_STRING),"UTF-8");
        fOut.close();
    }
    
    public void readFile(String sPath, long iOffSet, int iLength) throws FileNotFoundException, IOException{
        iOffSet = 0L;
        String sMode = "r";
        RandomAccessFile raf = new RandomAccessFile(sPath, sMode);
        raf.seek(iOffSet);
    }
}
