package com.jay.gui;

import com.jay.util.CommonConst;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.print.PrinterException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Jay
 * @date 2006. 6. 19.
 */
public class CellBean extends JPanel {

    private static final long serialVersionUID = 1L;
    private JScrollPane jScrollPane = null;
    private JTable jTable = null;
    JayFrame jf = null;
    /**
        * This is the default constructor
        */
    public CellBean() {
        super();
        initialize();
    }

    /**
        * @param tableModel
        */
    public void setModel(DefaultTableModel tableModel){
        this.clear();
        this.getJTable().setModel(tableModel);
        resizeColumnWidth(this.jTable);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable.getModel());
        jTable.setRowSorter(sorter);
//        this.getJTable().setAutoResizeMode(WIDTH);
//        this.getJTable().getTableHeader().setResizingAllowed(false);
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    /**
        * This method initializes this
        * 
        * @return void
        */
    private void initialize() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 0;
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(getJScrollPane(), gridBagConstraints);
//        this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
    }

    /**
        * This method initializes jScrollPane	
        * 	
        * @return javax.swing.JScrollPane	
        */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
        * This method initializes jTable	
        * 	
        * @return javax.swing.JTable	
        */
    public JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
            jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jTable.setCellSelectionEnabled(true);
            this.jTable.setShowVerticalLines(true);
            this.jTable.getTableHeader().setReorderingAllowed(false);
            
            jTable.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (jTable.getRowCount() > 0){
                        String aTemp = jTable.getValueAt(jTable.getSelectedRow(),jTable.getSelectedColumn()).toString();
                        if(aTemp == null) aTemp = CommonConst.WHITE_SPACE;
                        if(e.getClickCount() ==2 )
                            jf.insertEditBean(aTemp);
                    }
                }
            });
        }
        return jTable;
    }

    protected boolean printTable() throws PrinterException{
        Boolean bFlag = false;
        bFlag = this.getJTable().print();
        return bFlag;
    }

    void clear() {
        DefaultTableModel tableModel = new DefaultTableModel();
        if(this.getJTable().getRowCount() > 0)
            this.getJTable().setModel(tableModel);        
    }

    void setJayFrame(JayFrame aThis) {
        this.jf = aThis;
    }
}