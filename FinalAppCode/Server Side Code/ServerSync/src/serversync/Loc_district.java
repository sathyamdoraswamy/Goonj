/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class Loc_district {
    public int id;
    public String name;
    public int state_id;

     public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_loc_district values";
            stmt = stmt + " (" + id;
            stmt = stmt + ", '" + name + "'";
            stmt = stmt + "," + state_id + ")";
            return stmt;

        }

        public static void main (String args[]) throws Exception
        {
            Loc_district tc = new Loc_district();
            MySQLAccess m = new MySQLAccess();
            tc.id = 1;
            tc.name = "Bokaro";
            tc.state_id = 1;
            //tc.topic_description = "All stories about MA";

            String s = tc.createInsertStmt();
            System.out.println(s);
            m.insertValue(s);
        }
}
