/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.util;

import java.io.File;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Jay
 */
public class JayCipher {
    
    private String key;
    private SecretKeySpec keySpec;
    private Cipher cipher;

    public JayCipher(){
        init();
    }
    
    public final void init(){
        try {
            chkKeyFile();
            SecretKey sk = (SecretKey)FileHandler.readSerFile(System.getProperty(CommonConst.USER_DIR_PROP_KEY)+File.separator+CommonConst.LIB_DIR+File.separator+CommonConst.KEY_FILE);
            byte[] raw = sk.getEncoded();
            keySpec = new SecretKeySpec(raw, CommonConst.AES);
            cipher=Cipher.getInstance(CommonConst.AES);            
        } catch (Exception ex) {
            Logger.getLogger(JayCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String encrypt(String str){
        try{
            byte[] encrypted=null;
            synchronized(cipher){
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                encrypted=cipher.doFinal(str.getBytes(CommonConst.ENCODING));
            }
            return new String(Base64.encodeBase64(encrypted));
        }catch(Exception e){
            throw new RuntimeException("encryption failure", e);
        }
    }

    public String decrypt(String str){
        try{
            byte[] encrypted=null;
            encrypted = Base64.decodeBase64(str.getBytes(CommonConst.ENCODING));
            byte[] decrypted=null;
            synchronized(cipher){
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                decrypted=cipher.doFinal(encrypted);
            }
            return new String(decrypted);
        }catch(Exception e){
            throw new RuntimeException("decryption failuer", e);
        }
    }

    private static SecretKey generateRandomSecretKey(String algorithm) throws Exception{
        KeyGenerator keyGen=KeyGenerator.getInstance(algorithm);
        keyGen.init(128);
        SecretKey key=keyGen.generateKey();
        return key;
    }

//    private static String bytesToString(byte[] bytes){
//        byte[] b2=new byte[bytes.length+1];
//        b2[0]=1;
//        System.arraycopy(bytes, 0, b2, 1, bytes.length);
//        return new BigInteger(b2).toString(Character.MAX_RADIX);
//    }
//
//    private static byte[] stringToBytes(String str){
//        byte[] bytes=new BigInteger(str, Character.MAX_RADIX).toByteArray();
//        return Arrays.copyOfRange(bytes, 1, bytes.length);
//    }
    
    private void chkKeyFile() throws Exception{
        File file = new File(System.getProperty(CommonConst.USER_DIR_PROP_KEY)+File.separator+CommonConst.LIB_DIR+File.separator+CommonConst.KEY_FILE);
      
        if(!file.exists()){
            Key aKey = generateRandomSecretKey(CommonConst.AES);
            FileHandler.writeSerFile(CommonConst.KEY_FILE, aKey, System.getProperty(CommonConst.USER_DIR_PROP_KEY)+File.separator+CommonConst.LIB_DIR);
        }
    }
}
