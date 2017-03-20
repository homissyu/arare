package com.jay.gui;

import com.jay.util.CommonConst;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * @author Jay
 * @date 2006. 6. 19.
 */
public class InformDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JLabel jLabel = null;

    private JButton jButton = null;

    private JTextArea jTextArea = null;
    
    private Frame mOwner = null;

    /**
     * @param owner
     */
    public InformDialog(Frame owner) {
        super(owner);
        initialize();
        this.mOwner = owner;
    }

    public void viewInformDialog(String args){
        this.setInform(args);
        this.setModal(true);
        this.setLocationRelativeTo(this.mOwner);
//        this.mOwner.setModalExclusionType(null);
        this.setVisible(true);        
        MessageBean.setLabel(CommonConst.INFORM + " : " + args);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(389, 193);
        this.setTitle("Exception Dialog");
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = GridBagConstraints.BOTH;
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.ipady = 52;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.weighty = 1.0;
            gridBagConstraints2.insets = new Insets(6, 15, 5, 16);
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new Insets(5, 152, 11, 153);
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.ipadx = 10;
            gridBagConstraints1.ipady = 2;
            gridBagConstraints1.gridx = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(15, 15, 6, 16);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 242;
            gridBagConstraints.gridx = 0;
            jLabel = new JLabel();
            jLabel.setText("Information");
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(jLabel, gridBagConstraints);
            jContentPane.add(getJButton(), gridBagConstraints1);
            jContentPane.add(getJTextArea(), gridBagConstraints2);
        }
        return jContentPane;
    }

    private void setInform(String args){
        String temp = args;
        this.jLabel.setText(args);
        getJTextArea().setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        getJTextArea().setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        getJTextArea().setText(temp);
    }

    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setText("Close");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    closeDialog();
                }
            });
            jButton.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER)
                        closeDialog();
                }
            });
        }
        return jButton;
    }
    
    
    /**
     * 
     */
    private void closeDialog(){
        this.setVisible(false);
    }

    /**
     * This method initializes jTextArea	
     * 	
     * @return javax.swing.JTextArea	
     */
    private JTextArea getJTextArea() {
        if (jTextArea == null) {
            jTextArea = new JTextArea();
            jTextArea.setBackground(SystemColor.text);
            jTextArea.setEditable(false);
            jTextArea.setEnabled(true);
            jTextArea.setLineWrap(true);
        }
        return jTextArea;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
