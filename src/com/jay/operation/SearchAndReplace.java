/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.operation;

import com.jay.gui.InformDialog;
import com.jay.gui.JayFrame;
import com.jay.gui.SRFrame;
import com.jay.util.CommonConst;
import javax.swing.JEditorPane;
import javax.swing.JTable;

/**
 *
 * @author ìµœê´‘í˜¸
 */
public class SearchAndReplace {
    com.jay.gui.SRFrame srf  = null;
    JEditorPane ep = null;
    JTable jt = null;
    JayFrame jf = null;
    boolean isCaseSensitive=false;
    boolean isPerWord = false;
    String content = null;
    InformDialog id = null;
    
    public SearchAndReplace(SRFrame aSrf, Object mOwner) {
        jf = (JayFrame)mOwner;
        init(aSrf);
    }
    
    public SearchAndReplace(SRFrame aSrf) {
        init(aSrf);        
    }
    
    private void init(SRFrame aSrf){
        this.srf = aSrf;
        this.ep = jf.getEditBean().getEditorPane();
//        this.content =(ep.getText()).replaceAll("\\n", "");
        id =new InformDialog(this.srf);
        id.setModal(true);        
        jt = jf.getCellBean().getJTable();
    }
    
    private void findOperationInCell(boolean isPerWord, boolean isCaseSensitive){
        this.isCaseSensitive = isCaseSensitive;
        this.isPerWord = isPerWord;
        String findStr="";
        String content = null;
        findStr = srf.getSearchWord();
        
        if(this.isPerWord){
            findStr=" "+findStr+" ";
        }
        
        if(!this.isCaseSensitive){
            content =(ep.getText()).replaceAll("\\n", "");
            content = content.toLowerCase();
            findStr = findStr.toLowerCase();
        }
        String aTemp = null;
        String findStrRegex = "";
        boolean ret = false;
        for(int iRow=0;iRow<jt.getRowCount();iRow++){
            for(int iCol=0;iCol<jt.getColumnCount();iCol++){
                aTemp = ((String)jt.getValueAt(iRow, iCol));
                if(!this.isCaseSensitive){
                    findStrRegex = ".*"+findStr+".*";
                    if(aTemp.matches(findStrRegex)){
//                        this.srf.setVisible(false);
                        jt.changeSelection(iRow,iCol,false,false);
                        jt.requestFocus();
                        ret = true;
                        break;
                    }
                }else{
                    findStrRegex = "(?!).*"+findStr+".*";
                    if(aTemp.matches(findStrRegex)){
//                        this.srf.setVisible(false);
                        jt.changeSelection(iRow,iCol,false,false);
                        jt.requestFocus();
                        ret = true;
                        break;
                    }
                }
            }
            if(ret)break;
        }
        if(!ret){
            id.viewInformDialog(CommonConst.NOT_MATCHED);
        }
    }
    
    public void findOperation(boolean isPerWord, boolean isCaseSensitive, int mSearchType){
        //System.out.println("mSearchType:"+mSearchType);
        boolean aTemp = false;
        switch(mSearchType){
            case CommonConst.FIND:
                aTemp = findOperation(isPerWord, isCaseSensitive);
                break;
            case CommonConst.REPLACE:
                break;
            case CommonConst.FIND_IN_RESULT:
                findOperationInCell(isPerWord, isCaseSensitive);
                break;
            default:
                aTemp = findOperation(isPerWord, isCaseSensitive);
                break;
        }
        aTemp = false;
    }

    private  boolean  findOperation(boolean isPerWord, boolean isCaseSensitive){
        boolean isSelected = false;
        String findStr="";
        String content = null;
        int  startIdx=0,  endIdx=0,  fromIdx=0;

        findStr = srf.getSearchWord();
        
        if(isPerWord){
            findStr=" "+findStr+" ";
        }
        
        if(!isCaseSensitive){
            content =(ep.getText()).replaceAll("\\n", "");
            content = content.toLowerCase();
            findStr = findStr.toLowerCase();
        }
        
        ep.requestFocus();
        fromIdx=ep.getCaretPosition();
        startIdx=content.indexOf(findStr,  fromIdx);
        endIdx=startIdx+findStr.length();
        
        if(startIdx==-1){
            ep.select(0,-1);
            fromIdx=0;
            startIdx=0;
            endIdx=0;
        }else{
            ep.select(startIdx,endIdx);
            isSelected = true;
        }
        if(startIdx==0 && endIdx==0){
            id.viewInformDialog(CommonConst.NOT_MATCHED);
            isSelected = false;
        }
        return isSelected;
    }
    
    //ë°”ê¾¸ê¸°  ìž‘ì—…
    public  void  replaceOperation(){
        if(findOperation(isPerWord, isCaseSensitive)){
            ep.replaceSelection(srf.getReplaceWord());
        }
    }
    
    //ëª¨ë‘  ë°”ê¾¸ê¸°ìž‘ì—…
    public  void  replaceAllOperation(){
        int i=0;
        do{
            replaceOperation();
            i++;
        }while (ep.getCaretPosition() >= i);

        id.viewInformDialog(CommonConst.COMPLETE_REPLACEMENT+"\n"+"Chnaged Count : "+(i-1));
    }    
}
        
        
