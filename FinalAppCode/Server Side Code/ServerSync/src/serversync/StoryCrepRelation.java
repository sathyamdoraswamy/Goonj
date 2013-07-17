/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class StoryCrepRelation {
	int scr_id;
	int story_id;
	int crep_id;

        public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_storycreprelation values (";
            stmt = stmt + "" + scr_id;
            stmt = stmt +  "," + story_id ;
            stmt = stmt + "," + crep_id + ")";
            return stmt;

        }

}
