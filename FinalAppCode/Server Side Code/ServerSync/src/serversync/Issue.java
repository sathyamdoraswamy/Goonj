/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class Issue {
    public int issue_id;
    public String issue_name;
    public String issue_description;
    public String audio_media;
    public String photo_media;
    public String video_media;
    public String status;

     public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_issue values";
            stmt = stmt + " (" + issue_id;
            stmt = stmt + ", '" + issue_name + "'";
            stmt = stmt +  ", '" + issue_description + "'" ;
            stmt = stmt + ", '" + audio_media + "'" ;
            stmt = stmt + ", '" + photo_media + "'" ;
            stmt = stmt + ", '" + video_media + "'" ;
            stmt = stmt + ", '" + status + "')" ;
            return stmt;

        }


             public static void main (String args[]) throws Exception
        {
            Issue tc = new Issue();
            MySQLAccess m = new MySQLAccess();
            tc.issue_id = 1;
            tc.issue_name = "NREGA";
            tc.issue_description = "Profiling of a work over time";
            tc.status = "open";
            //tc.state_id = 1;
            //tc.country_id = 1;

            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }


}
