/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.util;

import com.jay.gui.ExceptionDialog;
import com.jay.gui.JayFrame;
import com.jay.gui.MessageBean;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import javax.swing.JProgressBar;
import javax.swing.text.Document;
import javax.swing.text.Segment;

/**
 *
 * @author user
 */
public class FileSaver extends Thread {
    private static ExceptionDialog ed = null;
    MessageBean mb;
    JayFrame jf;
    Document doc;
    File f;

    public FileSaver(File f, Document doc, JayFrame obj) {
        setPriority(4);
        this.f = f;
        this.doc = doc;
        jf = obj;
        this.mb = obj.getMessageBean();
    }

    @Override
    public void run() {
        try {
        // initialize the statusbar
            JProgressBar progress = new JProgressBar();
            progress.setMinimum(0);
            progress.setMaximum((int) doc.getLength());
            // start writing
            Writer out = new FileWriter(f);
            Segment text = new Segment();
            text.setPartialReturn(true);
            int charsLeft = doc.getLength();
            int offset = 0;
            while (charsLeft > 0) {
                doc.getText(offset, Math.min(4096, charsLeft), text);
                out.write(text.array, text.offset, text.count);
                charsLeft -= text.count;
                offset += text.count;
                progress.setValue(offset);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    ed.viewExceptionDialog(e);
                }
            }
            out.flush();
            out.close();
        }
        catch (Exception ex) {
            final String msg = ex.getMessage();
            ed.viewExceptionDialog(ex);
        }
    }
}
