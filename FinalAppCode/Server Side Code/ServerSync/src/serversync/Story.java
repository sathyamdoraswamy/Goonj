/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class Story{

    //ToDo : name all fields which are paths as somthing that contains path
	public int story_id;
	public int location_id;
	public int detail_id;
	public int ai_id;
	public String story_type;
	public String tags;
	public String title;
	public String transcript;
	public String date; //in a particular format
	public String audioPath;
	public String videoPath;
	public int views_count;
	public int rating;
	public String status_tag1;
	public String status_tag2;
	public String call_number;//this can be 64-bit int too
	public int top_story;
	public int related_stories;
	public int gcall_duration;
	public String time_assigned;
	public String time_fetched;

        public String issueStoryPath;
	public String topicStoryPath;
        public int community_rep_id;

        public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_story values";
            stmt = stmt + " (" + location_id;
            stmt = stmt + ", '" + story_type + "'";
            stmt = stmt + "," + story_id;
            stmt = stmt + "," + detail_id;
            stmt = stmt + "," + ai_id;
            
            stmt = stmt +  ", '" + tags + "'" ;
            stmt = stmt + ", '" + title + "'" ;
            stmt = stmt + ", '" + transcript + "'" ;
            
            stmt = stmt + ", '" + audioPath + "'" ;
            stmt = stmt + ", '" + videoPath + "'" ;
            stmt = stmt + "," + views_count ;
            stmt = stmt + ", '" + date + "'" ;
            stmt = stmt + "," + rating;
            stmt = stmt + "," + top_story;
            stmt = stmt + "," + related_stories;
            stmt = stmt + ", '" + status_tag1 + "'" ;
            stmt = stmt + ", '" + status_tag2 + "'" ;
            stmt = stmt + ", '" + call_number + "'" ;
            
            stmt = stmt + "," + gcall_duration;
            stmt = stmt + ", '" + time_assigned + "'" ;
            stmt = stmt + ", '" + time_fetched + "')";
            return stmt;

        }

        public String createUpdateStmt()
        {
            String stmt;
            if(location_id==0)
                stmt = "update goonjModDB.goonjMod_story set gcall_duration=" + gcall_duration + " , status_tag1='"+status_tag1+"', status_tag2='"+status_tag2+"' where story_id="+story_id;
            else
                stmt = "update goonjModDB.goonjMod_story set gcall_duration=" + gcall_duration + ", location_id=" + location_id + " , status_tag1='"+status_tag1+"', status_tag2='"+status_tag2+"' where story_id="+story_id;
            return stmt;
        }


             public static void main (String args[]) throws Exception
        {
            Story tc = new Story();
            MySQLAccess m = new MySQLAccess();
            tc.story_id = 2;
            tc.location_id = 1;
            tc.story_type = "EVENT";
            tc.title = "health,education";
            tc.views_count = 1;
            tc.rating = 1;
            tc.top_story = 0;
            tc.date = "2013-02-02 12:12:12";
            tc.status_tag1 = "PL";
            tc.status_tag2 = "ASSIGNED";

            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }


}
