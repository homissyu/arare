/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditPanel.java
 *
 * Created on 2009. 1. 24, 오후 2:58:20
 */

package com.jay.gui;

import com.jay.util.CommonConst;
import com.jay.util.FileLoader;
import com.jay.util.JayException;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author jay
 */
public class EditBean extends javax.swing.JPanel {
    boolean bFlag = false;
    JayFrame jf = null;
    ScrollPopupDialog spd = null;
    DefaultListModel dlf = null;
    Timer mTimer = new Timer();
    TimerTask mTimerTask = null;
    /** Creates new form EditPanel */
    public EditBean() {
        initComponents();
        this.jEditorPane1.setDropTarget(new DropTarget(this.jEditorPane1, DnDConstants.ACTION_COPY,
        new DropTargetHandler()));
        Document doc = this.jEditorPane1.getDocument();
        if(doc instanceof PlainDocument) doc.putProperty(PlainDocument.tabSizeAttribute, 4);        
    }

    public void setJayFrame(JayFrame jf){
        this.jf = jf;
    }
    
    public void addUndoHandler(UndoableEditListener undoHandler){
        this.jEditorPane1.getDocument().addUndoableEditListener(undoHandler);
    }

    public void setText(String aText){
        this.jEditorPane1.setText(aText);
    }
    
    public void insertText(String aText) throws BadLocationException{
        this.jEditorPane1.getDocument().insertString(this.jEditorPane1.getCaretPosition(), aText, null);
    }

    public JEditorPane getEditorPane(){
        return this.jEditorPane1;
    }
   
    public void paste(){
        this.jEditorPane1.paste();
    }
    
    public void cut(){
        this.jEditorPane1.cut();
    }
    
    public void copy(){
        this.jEditorPane1.copy();
    }

    public void clear(){
        this.jEditorPane1.setText("");
    }

    public String getText() {
        return this.jEditorPane1.getText();
    }

    public JEditorPane getJEditorPane(){
        return this.jEditorPane1;
    }
    
    public Document getDocument() {
        return this.jEditorPane1.getDocument();
    }

    public void setDocument(Document doc) {
        this.jEditorPane1.setDocument(doc);
    }

    public void printText(){
        try {
            this.jEditorPane1.print();
        } catch (PrinterException ex) {
            Logger.getLogger(EditBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEditable(Boolean aFlag){
        this.jEditorPane1.setEditable(aFlag);
    }

    private HashMap getChildObj(String aMomObj) {
        return this.jf.getChildObj(aMomObj);
    }

    private void stopShowObjectFinder() {
        bFlag = true;
    }

    private void setObjectFinderVisible(boolean b) {
        spd.setVisible(b);
//        spp.setVisible(b);
    }

    private class DropTargetHandler implements DropTargetListener {
        @Override
        public void drop(DropTargetDropEvent event) {
            Transferable transferable = event.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    List fileList = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    Iterator iterator = fileList.iterator();
                    Thread loader = null;
                    JEditorPane jp = getJEditorPane();
                    clear();
                    Document doc = jp.getDocument();
                    if (iterator.hasNext()) {
                        File file = (File) iterator.next();
                        loader = new FileLoader(file, doc, jf);
                        loader.start();
                    }
                    event.dropComplete(true);
                } catch (UnsupportedFlavorException flavorException) {
                    flavorException.printStackTrace();
                    event.dropComplete(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    event.dropComplete(false);
                }
            } else {
                event.rejectDrop();
            }
        }

        @Override
        public void dragEnter(DropTargetDragEvent event) {
            if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                event.acceptDrag(DnDConstants.ACTION_COPY);
            else {
                event.rejectDrag();
            }
        }

        @Override
        public void dragExit(DropTargetEvent event) {
        }

        @Override
        public void dragOver(DropTargetDragEvent event) {
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent event) {
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lines = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        lines.setEditable(false);
        lines.setBackground(new java.awt.Color(224, 223, 223));
        lines.setColumns(3);
        lines.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lines.setRows(3);
        lines.setText("1");
        lines.setMinimumSize(new java.awt.Dimension(6, 25));

        setMinimumSize(new java.awt.Dimension(24, 200));
        setPreferredSize(new java.awt.Dimension(30, 30));
        setLayout(new java.awt.BorderLayout());

        jEditorPane1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jEditorPane1.setDragEnabled(true);
        jEditorPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jEditorPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jEditorPane1MousePressed(evt);
            }
        });
        jEditorPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jEditorPane1CaretUpdate(evt);
            }
        });
        jEditorPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jEditorPane1MouseDragged(evt);
            }
        });
        jEditorPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jEditorPane1FocusGained(evt);
            }
        });
        jEditorPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyReleased(evt);
            }
        });
        jEditorPane1.getDocument().addDocumentListener(new DocumentListener(){
            public String getText(){
                int caretPosition = jEditorPane1.getDocument().getLength();
                Element root = jEditorPane1.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

        });
        jScrollPane1.setViewportView(jEditorPane1);
        jScrollPane1.setRowHeaderView(lines);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void setScrollPopup(){
        spd = new ScrollPopupDialog(this.jf, true);
//        spp = new ScrollPopupPanel();
        dlf = new DefaultListModel();
        dlf.removeAllElements();
        Point point = jEditorPane1.getCaret().getMagicCaretPosition();
        Point pos = this.getLocationOnScreen();
        if(point != null)spd.setLocation((int)point.getX()+(int)pos.getX()+55, (int)point.getY()+(int)pos.getY()+20);        
//        spp.setLocation((int)point.getX()+(int)pos.getX()+50, (int)point.getY()+(int)pos.getY());
    }
    
    private void showObjInfo(int aKeyCode) throws JayException{
        try{
            String aTemp = null;
            switch(aKeyCode){
                case 46:
                    setScrollPopup();
                    aTemp = this.jEditorPane1.getText();
                    String aDelimeter = CommonConst.WHITE_SPACE;
                    int aTempIdx = 0;
                    if((aTempIdx = aTemp.lastIndexOf('\t'))>0)
                        aDelimeter = CommonConst.TAB;
                    String[] aMomObjArr;
                    aMomObjArr = (aTemp.split(aDelimeter));
                    HashMap aChildObjMap = null;
                    if((aChildObjMap = getChildObj(StringUtils.trim(aMomObjArr[aMomObjArr.length-1]+1))) != null){
                        Iterator it = aChildObjMap.keySet().iterator();
                        String aKey;
        //                String aData;
                        while(it.hasNext()){
                            aKey = (String)it.next();
                            aKey = aKey.toUpperCase();
        //                    aData = (String)aChildObjMap.get(aKey);
                            dlf.addElement(aKey);
                            (spd.getJList()).setModel(dlf);
                        }
//                        spd.setVisible(true);
                        setObjectFinderVisible(true);
                    }
                    break;
                case 32:
                    setScrollPopup();
                    ArrayList aTempList = null;
                    aTemp = this.jEditorPane1.getText();
                    aTemp = StringUtils.trim(aTemp.substring(aTemp.length()-5));
                    if(CommonConst.FROM_STR.equalsIgnoreCase(aTemp)){
                        aTempList = jf.getTableAndView();
                        Iterator it = aTempList.iterator();
                        String aKey;
                        while(it.hasNext()){
                            aKey = (String)it.next();
                            aKey = aKey.toUpperCase();
                            dlf.addElement(aKey);
                            (spd.getJList()).setModel(dlf);
//                            (spp.getJList()).setModel(dlf);
                        }
                        setObjectFinderVisible(true);
//                        spd.setVisible(true);
                    }
                    break;
                default:
                    break;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw new JayException(ex);
        }
        
    }
    
    private void jEditorPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyReleased
        try{
            final int aKeyCode = evt.getKeyCode();
            mTimerTask = new TimerTask(){
                @Override
                public void run() {
                    try {
                        showObjInfo(aKeyCode);
                    } catch (JayException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            this.mTimer.schedule(mTimerTask, CommonConst.SHOW_OBJ_INFO_DURATION);     
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jEditorPane1KeyReleased
    
    private void jEditorPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEditorPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jEditorPane1MouseClicked

    private void jEditorPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEditorPane1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jEditorPane1MousePressed

    private void jEditorPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEditorPane1MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jEditorPane1MouseDragged

    private void jEditorPane1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jEditorPane1CaretUpdate
        // TODO add your handling code here:
//        stopShowObjectFinder();
//        System.out.println("123");
    }//GEN-LAST:event_jEditorPane1CaretUpdate

    private void jEditorPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jEditorPane1FocusGained
        // TODO add your handling code here: 
        
    }//GEN-LAST:event_jEditorPane1FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea lines;
    // End of variables declaration//GEN-END:variables
    
}