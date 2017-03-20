/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.ldap;

import com.jay.util.CommonConst;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author Jay
 */
public class LdapSearch {
//    private static final String sSubSystem = "LdapSearch";
//    Logger log = Logger.getLogger();
    private String sLdapnfo = null;

    private static final String FALSE_FLAG = "F";
    private static final String NOTHING_FLAG = "N";

    private static final String DELIMETER = "^";

    public LdapSearch(){
    }

/**
* @param sId
* @param iFlag : 0 정규직, 1 협력직
* @return
*/
    private HashMap search(String sSingleId, int iFlag, String sServiceProvider, String sUid, String sPwd){
        HashMap ret = null;
        Hashtable env = new Hashtable(5, 0.75f);
        env.put(Context.INITIAL_CONTEXT_FACTORY, CommonConst.INITCTX);
        env.put(Context.PROVIDER_URL, sServiceProvider);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, sUid);
//        env.put(Context.SECURITY_CREDENTIALS, sPwd);
        try {
            DirContext ctx = new InitialDirContext(env);
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration results = ctx.search("o=yessign,c=kr","99816232005021500000112",constraints);
            while(results != null && results.hasMore()){
                SearchResult sr = (SearchResult)results.next();
                Attributes attrs = sr.getAttributes();
                ret = new HashMap();
                for(NamingEnumeration ne = attrs.getAll(); ne.hasMoreElements();) {
                    Attribute attr = (Attribute)ne.next();
                    ret.put(attr.getID(),attr.toString());	
                }
            }
            System.out.println(ret.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

/**
* getLdapInfo()
* LDAP에서 결제자 정보를 가지고 옴.
*
* @param sRegID 검색할 ID(ID)
* @return String
* @throws LdapInfoException
* @throws IOException
* @throws Throwable
*/
    public String getLdapInfo(String sRegID) throws IOException {
        String ret = null;
        if(sRegID == null || sRegID.equals("null")) return FALSE_FLAG;
        this.search(null, 0, CommonConst.SERVICE_PROVIDER, ret, ret);
        return ret;
    }

    public static void main(String[] args) throws IOException{
        LdapSearch ls = new LdapSearch();
        
        String ret = ls.getLdapInfo("aaa");
        if(ret == null)
            System.out.println("tempMap is null");
        else
            System.out.println(ret.toString());
    }
}