package com.jay.util;

import javax.naming.Name;

public class CommonConst{
    public static final String CURRENT_DIR = ".";
    public static final String DEFAULT_RETRIEVE_CNT = "1000";
    public static final String SAVE_AS = "Save As ...";
    public static final String EXPORT = "Export";
    public static final int FIND = 0;
    public static final int REPLACE = 1;
    public static final int FIND_IN_RESULT = 2;
    public static final String WHITE_SPACE = " ";
    public static final String CONN_INFO_STR="ConnInnfo";
    public static final String CONN_STR="conn";
    public static final String ENTRY_STR="entry";
    public static final String LIB_DIR = "lib";
    public static final String DAT_DIR = "dat";
    public static final String CONNECT_INFO_SER_FILE="jayc.dat";
    public static final String CURR_CONNECT_INFO_SER_FILE="jay.curr.ser";
    public static final String NOT_MATCHED = "Not Matched";
    public static final String COMPLETE_REPLACEMENT = "Complete replacement";
    public static final String SQL_DELIMITER = ";";
    public static final int RUN_SCRIPT = 0;
    public static final int RUN_CURR_POSITON=1;
    public static final int RUN_FROM_POSITON=2;
    public static final String NEW_LINE_N = "\n";
    public static final String LINE_DELIMTER_N = "%n";
    public static final String LINE_DELIMTER_R = "%r";
    
    
    public static final String TAB = "\t";
    public static final String TAB_WHITE = "  ";
    public static final String TAB_STR = "tab";    
    public static final String ENCODING = "UTF-8";
    public static final String PASSWORD_ENC_STRING = "PWD_ENC";
    public static final String ASTERISK = "*";
    public static final String COLUMNNAMES = "ColumnNames"; 
    public static final String COLUMN_NAME = "ColumnName"; 
    public static final String COLUMN_TYPE = "Type"; 
    public static final String DATUM = "Datum"; 
    public static final String ROW_CNT = "RowCnt"; 
    
    public static final int DML_SELECT = 0;
    public static final int DML_OTHER = 1;
    public static final int DDL = 2; 
    public static final int DCL_COMMIT = 3; 
    public static final int DCL_ROLLBACK = 4;
    public static final String SQL_KEYWORD_SELECT = "SELECT";
    public static final String SQL_KEYWORD_INSERT = "INSERT";
    public static final String SQL_KEYWORD_DELETE = "DELETE";
    public static final String SQL_KEYWORD_UPDATE = "UPDATE";
    public static final String SQL_KEYWORD_GRANT = "GRANT";
    public static final String SQL_KEYWORD_DROP = "DROP";
    public static final String SQL_KEYWORD_TRUNCATE = "TRUNCATE";
    public static final String SQL_KEYWORD_CREATE = "CREATE";
    public static final String SQL_KEYWORD_ALTER = "ALTER";
    public static final String SQL_KEYWORD_COMMIT = "COMMIT";
    public static final String SQL_KEYWORD_ROLLBACK = "ROLLBACK";
    public static final String SQL_KEYWORD_SHOW = "SHOW";
    public static final String EXACTLY_MATCHED = "That exactly match the word to find only.";
    public static final String NONE_SELECTED_NODE = "None Selected Node";
    public static final String NO_DATA_SELECTED = "No Data Selected";
    public static final String SELECT_ROWS = "Please select at 100 line within for preview because of Java Heap Size";
    public static final String EXCEPTION_FILE_NAME = "Arare.ecp";
//    public static final String EXCEPTION_FILE_PATH = "%homepath%\\my Documents";
    public static final String EXCEPTION_FILE_PATH = "c:\\";
    public static final String ARARE_PNG_PATH = "arare.png";
    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String DESede = "DESede";
    public static final int ENCRYPT = 1;
    public static final int DECRYPT = 0;
    public static final int TEST = 2;
    public static final String T_DES = "TrippleDES";
    public static final String KEY_FILE = "jayk.dat";
//    public static String CLASSPATH = "";
    public static final String USER_DIR_PROP_KEY = "user.dir";
    public static final String JAVA_HOME_PROP_KEY = "java.home";
    public static final String SEPARATOR_PROP_KEY = "system.separator";
    public static final String TEST_SQL = "SELECT 1 FROM DUAL";
    public static final String SUCCESS = "Successfully connected";
    public static final String SUCCESS_SAVE = "Successfully saved";
    public static final String FAIL = "Fail to connect";
    public static final String LABEL_STRING = "Label";
    public static final String MSSQL_DBO = "dbo";
    public static int PREVIEW_RESULT = 1;
    public static int PREVIEW_SQL = 0;
    public static String SQL_ONLY= "SQL FIle or Text File Only";
    public static String TERM_TYPE = "TERM_TYPE";
    public static String SSH_TYPE = "SSH";
    public static String TELNET_TYPE = "TELNET";
    
    public static final String GEN_USER_PROMPT = "$";
    public static final String GEN_ROOT_PROMPT = "#";
    public static final String KFCC_USER_PROMPT = "]";
    public static final String FROM_STR = "from";
    public static final String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
    public static final String SERVICE_PROVIDER = "ldap://203.233.91.35:389";
    public static final long SHOW_OBJ_INFO_DURATION = 500L;
    public static final String  XML_STRING = "xml";
    public static final String CSV_STRING = "csv";
    public static final String XLS_STRING = "xls";

    public static String getEntryDn(int iFlag){
        String ENTRYDN = null;
        switch(iFlag){
            case 0:
                ENTRYDN = "ou=regular,ou=people,o=samsung";
                break;
            case 1:
                ENTRYDN = "ou=totalsc,ou=people,o=samsung";
                break;
            case 2:
                ENTRYDN = "ou=org,ou=reg,o=sec";
                break;
            default:
                ENTRYDN = "ou=regular,ou=people,o=samsung";
                break;
        }            
        return ENTRYDN;
    }
    
    public static final String TIME_ZONE = "TimeZone";
    public static final String TIME_ZONE_DEFAULT = "GMT";
    public static final String TIME_ZONE_KR = "GMT+9";
    
    public CommonConst(){
    }

    //메세지 관련
    public final static String MSG_SAVE="Do you want save?";
    public final static String MSG_SAVE_CMP="Successfully saved.";
    public final static String MSG_SAVE_FAIL="It as Not saved.";
    public final static String MSG_REFL="Do you want to apply it now?";
    public final static String MSG_REFL_CMP="Successfully applied to the system.";
    public final static String MSG_REFL_FAIL="Failed to apply it to the system.";
    public final static String MSG_DEL="Do you want to delete?";
    public final static String MSG_DEL_CMP="Successfully deleted the data.";
    public final static String MSG_DEL_FAIL="Failed to delete it.";
    public final static String MSG_MODF="Do you want update?";
    public final static String MSG_MODF_CMP="Successfully updated the data.";
    public final static String MSG_MODF_FAIL="Faild to update it.";
    public final static String MSG_INS="Do you want to create?";
    public final static String MSG_INS_CMP="Successfully created.";
    public final static String MSG_INS_FAIL="Failed to create it.";
    public final static String MSG_ASSIGN="Do you want to assign it?";
    public final static String MSG_ASSIGN_CMP="Successfully assigned it.";
    public final static String MSG_ASSIGN_FAIL="Failed to assign it.";
    public final static String MSG_HOST_DEL="Do you want to delete the host?";
    public final static String MSG_HOST_DEL_CMP="Successfully deleted the host.";
    public final static String MSG_HOST_DEL_FAIL="Failed to delete the host";
    public final static String MSG_JGROUP_DEL_CANT = "Unable to delete the group:One or more related task is running."
                                                                                                    +"\\n Please delete the related task first, and try again.";
    public final static String MSG_JOB_DEL_CANT = "Unable to delete the task:One or more related system is running."
                                                                                                    +"\\n Please delete the related system first, and try again.";
    public final static String MSG_SAVE_ID_FAIL = "Failed to save the data:Duplicate user ID.";

    public final static String MSG_LOGIN_FAIL1 = "Sorry, your login attempt cannot be completed.";
    public final static String MSG_LOGIN_FAIL2 = "Unregistered user ID or password entered. Sorry, your login attempt cannot be completed";
    public final static String MSG_LOGIN_FAIL3 = "No user menu group was assigned. Contact the administrator.";

    public final static String MSG_CMP = "Successfully completed.";
    
    public static final String APP_NAME = "Arare";
    
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String TIME_FORMAT = "HHmm";
    public static final String SEC_FORMAT = "HHmmss";
    public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
    
    public static final String DATETIME_STRING = "^^YYYYMMDDHH24MISS^^";
    public static final String DATE_STRING = "^^YYYYMMDD^^";
    
    public static final String SITE = "SITE";
    public static final String TYPE = "DB_TYPE";
    public static final String HOST = "IP"; 
    public static final String PORT = "PORT"; 
    public static final String USER = "USER_NAME"; 
    public static final String PASSWORD = "PASSWORD"; 
    public static final String SID = "SID"; 
    public static final String SOURCE_QRY = "SOURCE_QRY";
    
    public static final String JAVA_SPEC_VERSION = "java.specification.version";
    public static final String JAVA6 = "1.6";

    public static final String CONNECTED = " Connected Database";
    public static final String GOT_DATA = " Successfully Done";

    public static final String EXCEPTION = " Exception occured";
    public static final String INFORM = " Infromation ";

    public static final String DEVELOPPING = "It is developping";
    public static final String DISCONNECTED = "This Client is disconnected by User";

    public static final String ORACLE = "ORACLE";
    public static final String TIBERO = "TIBERO";
    public static final String QUBRID = "CUBRID";


//    public static final String CONNECT_INFO_FILE = "jay.dat";

    public static final String SID_STRING = "DBName";

    public static final String HOST_STRING = "Server";

    public static final String PORT_STRING = "Port";

    public static final String DB_TYPE_STRING = "Database";

    public static final String USER_STRING = "User";

    public static final String PASSWORD_STRING = "Password";
    
    public static final String TEST_STRING = "Test";

    public static final String [] CONNECT_CONFIG_COLUMN = {"Key",LABEL_STRING, DB_TYPE_STRING,HOST_STRING,PORT_STRING,SID_STRING,USER_STRING,PASSWORD_STRING, TERM_TYPE};

    public static final String [] DATABASE_TYPE = {"ORACLE","MS-SQL","MYSQL","DB2","SYBASE","TIBERO","ALTIBASE","CUBIRD","INFOMIX","POSTGRE"};
    
    public static final int ORACLE_TYPE = 1;
    public static final int SYBASE_TYPE = 2;
    public static final int MSSQLSERVER_TYPE = 3;
    public static final int MYSQL_TYPE = 4;
    public static final int TIBERO_TYPE = 5;
    public static final int DB2_TYPE = 6;
    public static final int ALTIBASE_TYPE = 7;
    public static final int CUBRID_TYPE = 8;
    public static final int INFOMIX_TYPE = 9;
    public static final int POSTGRE_TYPE = 10;
    
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String SYBASE_DRIVER = "com.sybase.jdbc.SybDriver";
    public static final String MSSQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String TIBERO_DRIVER = "com.tmax.tibero.jdbc.TbDriver";
    public static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
    public static final String ALTIBASE_DRIVER = "Altibase.jdbc.driver.AltibaseDriver";
    public static final String CUBRID_DRIVER = "cubrid.jdbc.driver.CUBRIDDriver";
    public static final String INFOMIX_DRIVER = "com.informix.jdbc.IfxDriver";
    public static final String POSTGRE_DRIVER = "org.postgresql.Driver";
    
    public static final String [] CATALOG_TYPE = {"TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM" };

    public static final String [] CATALOG_INFO = {"NAME","TYPE","DESCRIPTION","CATEGORY","SCHEME"};
    
    public static final char[] sAlphabetChar = {'A','a','B','b','C','c','D','d','E','e','F','f',
                                                'G','g','H','h','I','i','J','j','K','k','L','l',
                                                'M','m','N','n','O','o','P','p','Q','q','R','r',
                                                'S','s','T','t','U','u','V','v','W','w','X','x',
                                                'Y','y','Z','z'};
    
    public static final String NO_PERMISSION = " (No permission)";
    /**
     * 
TABLE_CAT String => table catalog (may be null)
TABLE_SCHEM String => table schema (may be null)
TABLE_NAME String => table name
COLUMN_NAME String => column name
DATA_TYPE int => SQL type from java.sql.Types
TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
COLUMN_SIZE int => column size.
BUFFER_LENGTH is not used.
DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
NUM_PREC_RADIX int => Radix (typically either 10 or 2)
NULLABLE int => is NULL allowed.
columnNoNulls - might not allow NULL values
columnNullable - definitely allows NULL values
columnNullableUnknown - nullability unknown
REMARKS String => comment describing column (may be null)
COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
SQL_DATA_TYPE int => unused
SQL_DATETIME_SUB int => unused
CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
ORDINAL_POSITION int => index of column in table (starting at 1)
IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
YES --- if the parameter can include NULLs
NO --- if the parameter cannot include NULLs
empty string --- if the nullability for the parameter is unknown
SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF)
SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
YES --- if the column is auto incremented
NO --- if the column is not auto incremented
empty string --- if it cannot be determined whether the column is auto incremented parameter is unknown
     */
//    public static final String [] COLUMN_INFO = {"TABLE_CAT","TABLE_SCHEM","TABLE_NAME","COLUMN_NAME","DATA_TYPE","TYPE_NAME","COLUMN_SIZE","BUFFER_LENGTH","DECIMAL_DIGITS","NUM_PREC_RADIX","NULLABLE","REMARKS","COLUMN_DEF","SQL_DATA_TYPE","SQL_DATETIME_SUB","CHAR_OCTET_LENGTH","ORDINAL_POSITION","IS_NULLABLE","SCOPE_CATLOG","SCOPE_SCHEMA","SCOPE_TABLE","SOURCE_DATA_TYPE","IS_AUTOINCREMENT"};
    public static final String [] COLUMN_INFO = {"COLUMN_NAME","TYPE_NAME","COLUMN_SIZE","REMARKS","IS_NULLABLE"};
    
    public static final String [] SVR_TREE_IMG = {
        "/com/jay/images/treeIcons/Server.png",
        "/com/jay/images/treeIcons/CloseDBMS.png",
        "/com/jay/images/treeIcons/OpenDBMS.png",
        "/com/jay/images/treeIcons/Table.png",
        "/com/jay/images/treeIcons/View.png",
        "/com/jay/images/treeIcons/User.png",
        "/com/jay/images/treeIcons/UnauthorizedDB.png"
    };
    
}