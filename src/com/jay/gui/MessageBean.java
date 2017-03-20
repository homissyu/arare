package com.jay.gui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

/**
 * @author Jay
 * @date 2006. 6. 19.
 */
public class MessageBean extends JPanel {

    private static final long serialVersionUID = 1L;
    private JToolBar jToolBar = null;
    private JPanel jPanel = null;
    private JProgressBar jProgressBar = null;
    private static JLabel jLabel = null;
    /**
    * This is the default constructor
    */
    public MessageBean() {
        super();
        initialize();
    }

//    protected JProgressBar getjProgressbar() {
//        return this.jProgressBar;
//    }

    /**
    * This method initializes this
    * 
    * @return void
    */
    private void initialize() {
        this.setSize(300, 112);
        this.setLayout(new BorderLayout());
        this.add(getJToolBar(), BorderLayout.CENTER);
    }

    /**
    * This method initializes jToolBar	
    * 	
    * @return javax.swing.JToolBar	
    */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getJPanel());
        }
        return jToolBar;
    }

    /**
    * This method initializes jPanel	
    * 	
    * @return javax.swing.JPanel	
    */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel = new JLabel();
            jLabel.setText("");
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJProgressBar(), BorderLayout.EAST);
            jPanel.add(jLabel, BorderLayout.WEST);
        }
        return jPanel;
    }

    /**
    * This method initializes jProgressBar	
    * 	
    * @return javax.swing.JProgressBar	
    */
    public JProgressBar getJProgressBar() {
        if (jProgressBar == null) {
            jProgressBar = new JProgressBar();
        }
        return jProgressBar;
    }
	
    /**
    * @param arg
    */
    public static void setLabel(String arg) {
        jLabel.setText(arg);		
    }
}  //  @jve:decl-index=0:visual-constraint="29,72"
