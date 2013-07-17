/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class IssueStoryRelation implements java.io.Serializable {
    public int is_id;
    public int story_id;
    public int issue_id;

     public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_issuestoryrelation (story_id,issue_id) values";

            //stmt = stmt + "(" + is_id;
            stmt = stmt +  "(" + story_id ;
            stmt = stmt + "," + issue_id + ")";

            return stmt;

        }

             public static void main (String args[]) throws Exception
        {
            IssueStoryRelation tc = new IssueStoryRelation();
            MySQLAccess m = new MySQLAccess();
            tc.is_id = 1;
            tc.story_id = 1;
            tc.issue_id = 1;

            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }
}
