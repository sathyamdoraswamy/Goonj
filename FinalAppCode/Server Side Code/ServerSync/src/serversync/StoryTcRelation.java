/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class StoryTcRelation implements java.io.Serializable{
    public int stc_id;
    public int story_id;
    public int topic_channel_id;

     public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_storytcrelation (story_id, topic_channel_id) values";
            //stmt = stmt + " (" + stc_id;
            stmt = stmt +  "(" + story_id ;
            stmt = stmt + "," + topic_channel_id + ")";
            return stmt;

        }


             public static void main (String args[]) throws Exception
        {
            StoryTcRelation tc = new StoryTcRelation();
            MySQLAccess m = new MySQLAccess();
            tc.stc_id = 1;
            tc.story_id = 1;
            tc.topic_channel_id = 1;


            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }

}
