package com.jay.gui;

import com.jay.util.CommonConst;
import com.jay.util.JayException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Jay
 * @date 2006. 6. 19.
 */
public class TreeBean extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTree jTree = null;
    private JScrollPane jScrollPane = null;

    private DefaultMutableTreeNode dmtn = null;
    private DefaultTreeModel dtm = null;

    JayFrame jayFrame = null;
    ExceptionDialog ed = null;
    private String mDBType = null;
    
//    private JMenuItem jMenuItem;
    private JPopupMenu jPopupMenu = new JPopupMenu();
    /**
        * This is the default constructor
        */
    public TreeBean() {
            super();
            initialize();
    }

    TreeBean(JayFrame aThis) {
        super();
        initialize();
        jayFrame = aThis;
        ed = new ExceptionDialog(this.jayFrame);
    }

    protected void setFrame(JayFrame aThis){
        this.jayFrame = aThis;
    }
    
    public void setModel(DefaultTreeModel dtm){
        this.getJTree().setModel(dtm);
    }
    
    /**
        * @param data
        */
    public void setModel(Vector data, String aDBType){
        mDBType = aDBType;
        dmtn = new DefaultMutableTreeNode(mDBType);
        for(int i=0;i<data.size();i++){
            DefaultMutableTreeNode dmtn_sub = new DefaultMutableTreeNode((String)((Vector)data.get(i)).get(0));
            dmtn.add(dmtn_sub);
        }		
        dtm = new DefaultTreeModel(dmtn);
        this.getJTree().setModel(dtm);        
    }

    /**
    * @param data
    */
    public boolean setModel(HashMap aObjMap, String aDBType){
//        System.out.println(aObjMap);
        boolean ret = false;
        mDBType = aDBType;
        dmtn = new DefaultMutableTreeNode(mDBType);	
        DefaultMutableTreeNode aSchemaName = null ;
        DefaultMutableTreeNode aObjType = null;

        Iterator it = aObjMap.keySet().iterator();
        String aTempSchemeName = null;
        HashMap aInnerColsMap = null;
        while(it.hasNext()){
            aTempSchemeName = (String)it.next();
            aSchemaName = new DefaultMutableTreeNode(aTempSchemeName);
            HashMap aObjInfo = (HashMap)aObjMap.get(aTempSchemeName);
            Iterator itt = aObjInfo.keySet().iterator();
            while(itt.hasNext()){
                String aTempObjType = (String)itt.next();
                aObjType = new DefaultMutableTreeNode(aTempObjType);
                ArrayList aInnerCols = (ArrayList)aObjInfo.get(aTempObjType);
                Iterator ittt = aInnerCols.iterator();
                while(ittt.hasNext()){
                    aInnerColsMap = (HashMap)ittt.next();
//                    System.out.println(aInnerColsMap);
                    DefaultMutableTreeNode aTreeNode = new DefaultMutableTreeNode(aInnerColsMap.get(CommonConst.CATALOG_INFO[0]));
                    aObjType.add(aTreeNode);
                }
                aSchemaName.add(aObjType);
            }
            dmtn.add(aSchemaName);
        }
        dtm = new DefaultTreeModel(dmtn);
        this.getJTree().setModel(dtm);
        if(dtm!=null) ret = true;
        return ret;
    }

    /**
    * This method initializes this
    * 
    * @return void
    */
    private void initialize() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 133;
        gridBagConstraints.ipady = -162;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 4);
        this.setSize(223, 211);
        this.setLayout(new GridBagLayout());
        this.add(getJScrollPane(), gridBagConstraints);
        dmtn = new DefaultMutableTreeNode("Object");
        dtm = new DefaultTreeModel(dmtn);
        this.getJTree().setModel(dtm);
    }

    /**
    * @return
    */
    private JMenuItem getJMenuItem(int iType){
        JMenuItem jMenuItem = new JMenuItem();
        jMenuItem.setSize(120, 18);
        jMenuItem.setBackground(Color.white);
//        System.out.println(iType);
        switch(iType){
            case 0:
                jMenuItem.setText("SELECT * FROM $OBJECT");
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        setTextBean("SELECT * FROM "+getSelectedNodeName());
                    }
                });
                break;
            case 1:
                jMenuItem.setText("SELECT COUNT(*) FROM $OBJECT");
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        setTextBean("SELECT COUNT(*) FROM "+getSelectedNodeName());
                    }
                });  
                break;
             case 2:
                jMenuItem.setText("SELECT * FROM $OBJECT until 100 rows");
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        setTextBean(getTop10Prefix(getSelectedNodeName()));
                    }
                });  
                break;
            case 91:
                jMenuItem.setText("Direct connect to "+getSelectedNodeName());
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                    }
                });   
                break;
            default:
                break;
        }
        return jMenuItem;
    }
    
    private String getTop10Prefix(String aObject) {
        String ret = null;
        ret = this.jayFrame.getTop10SQL(aObject);
        return ret;
    }
    /**
    * @param text
    */
    private void setTextBean(String text){
        jayFrame.setTextBean(text);
    }
	
    /**
        * This method initializes jTree	
        * 	
        * @return javax.swing.JTree	
        */
    public JTree getJTree() {
        if (jTree == null) {
            jTree = new JTree();
            jTree.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    
                    if(getSelectedNodeName() != null){
                        if(jayFrame!=null && (e.getModifiers() & 4) != 0 ){
                            jPopupMenu.removeAll();
                            if((jTree.getModel()).isLeaf(jTree.getSelectionPath().getLastPathComponent()) &&
                                    (jTree.getAnchorSelectionPath()).getPathCount() > 2
                                    ){
                                jPopupMenu.add(getJMenuItem(2)); 
                                jPopupMenu.add(getJMenuItem(1));
                                jPopupMenu.add(getJMenuItem(0));
                            }else{
//                                if((jTree.getSelectionPath()).getPathCount() == 1)
//                                    jPopupMenu.add(getJMenuItem(91));                                 
                            }
                            jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                        }else{
                            if((jTree.getModel()).getChildCount(jTree.getSelectionPath().getLastPathComponent())==0 &&
                                    (jTree.getAnchorSelectionPath()).getPathCount() > 2
                                    )
                                jayFrame.getObjectInfo(getSelectedNodeName());
                        }
                    }                    
                }
            });
            jTree.setCellRenderer(new MyRenderer());
        }
        return jTree;
    }
	
    /**
        * @return
        */
    public String getSelectedNodeName(){
        String ret = null;
        try{
            TreePath tp = getJTree().getSelectionModel().getSelectionPath();            
            if(tp !=null){
                if(tp.getPathCount() > 2)
                    ret = tp.getPathComponent(1)+"."+(tp.getLastPathComponent()).toString();
                else
                    ret = (tp.getLastPathComponent()).toString();
            }
        }catch(java.lang.NullPointerException e){
            ed.viewExceptionDialog(new JayException(CommonConst.NONE_SELECTED_NODE));
        }
        return ret;
    }

    /**
        * This method initializes jScrollPane	
        * 	
        * @return javax.swing.JScrollPane	
        */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTree());
        }
        return jScrollPane;
    }

    void clear() {
        if(this.getJTree().getModel() != null)
            this.getJTree().setModel(null);
    }
        
    class MyRenderer extends DefaultTreeCellRenderer {
 
        Icon[] iconPack = {new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[0])),                            
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[1])),
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[2])),
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[3])),
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[4])),
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[5])),
                            new ImageIcon(getClass().getResource(CommonConst.SVR_TREE_IMG[6]))
        };

        public MyRenderer() {
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,boolean expanded, boolean leaf,int row,boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            TreeNode aThis = (TreeNode)value;
            TreeNode aParent = aThis.getParent();
//    System.out.println(mDBType);
            if(null==aParent) setIcon(iconPack[0]);
            else{
                if(aThis.getChildCount() > 0){
                    if(!expanded) setIcon(iconPack[1]);
                    else setIcon(iconPack[2]);
                }else {
                    if("VIEW".equals(aParent.toString())) setIcon(iconPack[4]);
                    else if("TABLE".equals(aParent.toString()))setIcon(iconPack[3]);
                    else if(mDBType.startsWith(CommonConst.ORACLE)) setIcon(iconPack[5]);
                    else setIcon(iconPack[6]);
                }  
            }
            return this;
        }

    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
