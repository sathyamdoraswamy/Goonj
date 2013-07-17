/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class TopicChannel {
	public int topic_channel_id;
	public String topic_description;
	public String topic;
	public String topic_photo;

         public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_topicchannel values";
            stmt = stmt + " (" + topic_channel_id;
            stmt = stmt + ", '" + topic + "'";
            stmt = stmt +  ", '" + topic_description + "'" ;
            stmt = stmt + ", '" + topic_photo + "')" ;
            return stmt;

        }

        public static void main (String args[]) throws Exception
        {
            TopicChannel tc = new TopicChannel();
            MySQLAccess m = new MySQLAccess();
            tc.topic_channel_id = 1;
            tc.topic = "Mridu Atray";
            tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }

}
