/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class Loc_village {
    int id;
    String village;
    int block_id;
    int district_id;
    int state_id;
    int country_id;

     public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_loc_village values";
            stmt = stmt + " (" + id;
            stmt = stmt + ", '" + village + "'";
            stmt = stmt + "," + block_id;
            stmt = stmt + "," + district_id;
            stmt = stmt + "," + state_id;
            stmt = stmt + "," + country_id + ")";
            return stmt;

        }

             public static void main (String args[]) throws Exception
        {
            Loc_village tc = new Loc_village();
            MySQLAccess m = new MySQLAccess();
            tc.id = 2;
            tc.village = "XYZ";
            tc.block_id = 1;
            tc.district_id = 1;
            tc.state_id = 1;
            tc.country_id = 1;
            
            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }

}
