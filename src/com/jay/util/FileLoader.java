/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.util;

import com.jay.gui.ExceptionDialog;
import com.jay.gui.JayFrame;
import com.jay.gui.MessageBean;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javax.swing.JProgressBar;
import javax.swing.text.Document;

/**
 *
 * @author user
 */
public class FileLoader extends Thread {
    
    private static ExceptionDialog ed = null;
    MessageBean mb;
    JayFrame jf;
    
    public FileLoader(File f, Document document, JayFrame obj) {
        setPriority(4);
        this.f = f;
        this.doc = document;
        jf = obj;
        this.mb = obj.getMessageBean();
    }

    @Override
    public void run() {
        try {
            // initialize the statusbar
            JProgressBar jProgressBar = mb.getJProgressBar();
            jProgressBar.setMinimum(0);
            jProgressBar.setMaximum((int) f.length());
            // try to start reading
            Reader in = new FileReader(f);
            char[] buff = new char[4096];
            int nch;
            while ((nch = in.read(buff, 0, buff.length)) != -1) {
                doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
                jProgressBar.setValue(jProgressBar.getValue() + nch);
            }
        }catch (Exception ex) {
            final String msg = ex.getMessage();
            ex.printStackTrace();
            this.jf.ed.viewExceptionDialog(ex);
        }
    }

    Document doc;
    File f;
}
