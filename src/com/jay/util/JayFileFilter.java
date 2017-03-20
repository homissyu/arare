/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jay
 */
public class JayFileFilter extends FileFilter{

    @Override
    public boolean accept(File f) {
        boolean ret = false;
        if(f != null){
            if(f.isDirectory()) ret = true;
            if(f.getAbsolutePath().endsWith(".sql")||
                    f.getAbsolutePath().endsWith(".txt")||
                    f.getAbsolutePath().endsWith(".csv")||
                    f.getAbsolutePath().endsWith(".qry")
                    ) ret = true;
        }
        return ret;
    }

    @Override
    public String getDescription() {
        String ret = null;
        ret = CommonConst.SQL_ONLY;
        return ret;
    }
    
}
