package cn.jpush;

/**
 * Created by wmky_kk on 2017-06-01.
 */
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.AbstractSemanticAnalyzerHook;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.session.SessionState;
public class  HiveAdmin extends AbstractSemanticAnalyzerHook {
//    private static String admin = "youck";
    private static String[] admin = {"youck","root","xujun","huangyq"};
    @Override
    public ASTNode preAnalyze(HiveSemanticAnalyzerHookContext context, ASTNode ast)throws SemanticException {
        switch(ast.getToken().getType()) {
            //  shows how a statement can have multiple case labels
            case HiveParser.TOK_CREATEDATABASE:
            case HiveParser.TOK_DROPDATABASE:
            case HiveParser.TOK_CREATEROLE:
            case HiveParser.TOK_DROPROLE:
            case HiveParser.TOK_GRANT:
            case HiveParser.TOK_REVOKE:
            case HiveParser.TOK_GRANT_ROLE:
            case HiveParser.TOK_REVOKE_ROLE:
                String userName = null;
                if(SessionState.get() != null
                        &&SessionState.get().getAuthenticator()!= null){
                    userName=SessionState.get().getAuthenticator().getUserName();
                }
                    if (!(admin[0].equalsIgnoreCase(userName)||admin[1].equalsIgnoreCase(userName)||
                            admin[2].equalsIgnoreCase(userName)||admin[3].equalsIgnoreCase(userName))) {
                        throw new SemanticException(userName + " can't use ADMIN options,except " + "\"youck\",\"root\",\"xujun\",\"huangyq\"" + ".");
                    }
                break;
            default:
                break;
        }
        return ast;
    }

/*    public static void main(String[] args) throws SemanticException {
        String[] admin = { "admin","root" };
        String userName = "root";
        for (String tmp : admin) {
//            System.out.println(tmp);
            if (!tmp.equalsIgnoreCase(userName)) {
//                System.out.print(userName+ " can't use ADMIN options, except " + admin[0] + "," + admin[1] + ".");
                throw new SemanticException(userName
                        + " can't use ADMIN options, except " + admin[0] + ","
                        + admin[1] + ".");
            }
        }
    }*/
}