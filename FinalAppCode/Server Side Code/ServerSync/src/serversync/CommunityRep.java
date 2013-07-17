/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

/**
 *
 * @author mridu
 */
public class CommunityRep {
	int community_rep_id;
	String cr_name;
	String cr_photo;
	String cr_phone_number;
	String role;
	String cr_email;
	String cr_location_id;
        int num_moderated;
	int num_gcalls_made;
	int num_pub_approved;
	int num_marked_gcn;
	int num_contributed;

        public String createInsertStmt()
        {
            String stmt = "insert into goonjModDB.goonjMod_communityrep (community_rep_id, cr_name, cr_photo, cr_phone_number, role, cr_email, num_moderated, num_gcalls_made, num_pub_approved, num_marked_gcn, num_contributed, cr_location_id) values (";
            stmt = stmt + "" + community_rep_id;
            stmt = stmt + ", '" + cr_name + "'";
            stmt = stmt +  ", '" + cr_photo + "'" ;
            stmt = stmt + ", '" + cr_phone_number + "'" ;
            stmt = stmt + ", '" + role + "'" ;
            stmt = stmt + ", '" + cr_email + "'" ;
            stmt = stmt + "," + num_moderated ;
            stmt = stmt + "," + num_gcalls_made;
            stmt = stmt + "," + num_pub_approved;
            stmt = stmt + "," + num_marked_gcn;
            stmt = stmt + "," + num_contributed;
            stmt = stmt + "," + cr_location_id + ")";
            return stmt;

        }


}
