/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serversync;

import framework.DatabaseHelper;
import java.io.*;
import framework.Framework;
/**
 *
 * @author mridu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        //DatabaseHelper dh = new DatabaseHelper();
//        CommunityRep cr = new CommunityRep();
//        cr.community_rep_id = 4;
//        cr.cr_name = "ABCD";
//        cr.role = "CR";
        //cr.cr_email = "default";
        //cr.cr_location_id = "default";
        //cr.cr_phone_number = "default";
        //cr.num_contributed = 0;
        //cr.num_gcalls_made = 0;
        //cr.num_marked_gcn = 0;
        //cr.num_pub_approved = 0;
        //cr.num_moderated = 0;
        //cr.cr_photo = "default";
        //cr.doJob(cr);
        //dh.putObject(cr,"FirstTry");


        /*   File f = new File("/home/mridu/Desktop/Data/60fc9273ea5a4c0c_0_0_topic");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        StoryTcRelation stc = (StoryTcRelation)ois.readObject();
        ois.close();

        System.out.println("stc_id = " + stc.stc_id);
        System.out.println("story_id =" + stc.story_id);
        System.out.println("topic_id = "+ stc.topic_channel_id);

        File f1 = new File("/home/mridu/Desktop/Data/60fc9273ea5a4c0c_0_0_issue");
        ois = new ObjectInputStream(new FileInputStream(f1));
        IssueStoryRelation isr = (IssueStoryRelation)ois.readObject();
        ois.close();

        System.out.println("is_id = " + isr.is_id);
        System.out.println("story_id =" + isr.story_id);
        System.out.println("issue_id = "+ isr.issue_id);
         */

      //  Story s = new Story();
//        Story s1 = new Story();
//        Story s2 = new Story();
//        String buf = args[0];
//        s.story_id = Integer.parseInt(buf);
//        s.date 2013-03-07 08:01:11= args[1];
//        s.call_number = args[4];
//        buf = args[2];
//        s.ai_id = Integer.parseInt(buf);
//        buf = args[3];
//        s.detail_id = Integer.parseInt(buf);
//        s.top_story = 0;
//        s.status_tag1 = "Assigned";
//        s.audioPath = "/home/mridu/downloads";
        //THE TOPIC CHANNELS
//        TopicChannel[] tc = new TopicChannel[9];
//        for(int i =0;i<9;i++)
//        {
//            tc[i] = new TopicChannel();
//            tc[i].topic_channel_id = (i+1);
//        }
//        tc[0].topic = "संस्कृति और मनोरंजन";
//        tc[1].topic = "अपराध";
//        tc[2].topic = "कृषि";
//        tc[3].topic = "स्वास्थ्य";
//        tc[4].topic = "जीविका";
//        tc[5].topic = "पर्यावर्ण";
//        tc[6].topic = "शासन";
//        tc[7].topic = " शिक्षा";
//        tc[8].topic = "इनमें से कोई नई";


        //THE LOC_DISTRICT CHANNELS
//        Loc_district[] ld = new Loc_district[25];
//        for(int i =0;i<25;i++)
//        {
//            ld[i] = new Loc_district();
//            ld[i].id = (i+1);
//        }
//        ld[0].name = "बोकारो";
//        ld[1].name = "गिरीडीह";
//        ld[2].name = "चतरा";
//        ld[3].name = "इनमें से कोई नई";
//        ld[4].name = "धनबाद";
//        ld[5].name = "रांची";
//        ld[6].name = "लातेहार";
//        ld[7].name = "गढ़वा";
//        ld[8].name = "देवगढ़";
//        ld[9].name = "रामगढ़";
//        ld[10].name = "पश्चिमी सिंहभूम";
//        ld[11].name = "साहिबगंज";
//        ld[12].name = "पूर्वी सिंहभूम";
//        ld[13].name = "हजारीबाग";
//        ld[14].name = "सेरइकेल्ला-खारसावन";
//        ld[15].name = "दुमका";
//        ld[16].name = "पलामू";
//        ld[17].name = "लोहरदगा";
//        ld[18].name = "सींदेगा";
//        ld[19].name = "जामताड़ा";
//        ld[20].name = "गोड्डा";
//        ld[21].name = "कोडरमा";
//        ld[22].name = "पाकुर";
//        ld[23].name = "गुमला";
//        ld[24].name = "खूंटी";

        //Issue i0 = new Issue();
        //Issue i1 = new Issue();
        //Issue i2 = new Issue();
        //Issue i3 = new Issue();
//        Issue[] is = new Issue[11];
//
//        for(int i=0;i<is.length;i++)
//        {
//            is[i] = new Issue();
//            is[i].issue_id = i;
//        }
//
//        is[0].issue_name = "इनमें से कोई नही";
//        is[1].issue_name = "न्रेगा";
//        is[2].issue_name = "शिक्षा अभियान";
//        is[3].issue_name = "विस्थापन";
//        is[4].issue_name = "खनन";
//        is[5].issue_name = "पी.डी.एस";
//        is[6].issue_name = "बुनियादी ढांचे";
//        is[7].issue_name = "स्थानान्तरण";
//        is[8].issue_name = "जल प्रदूषण";
//        is[9].issue_name = "एस.एच.जी";
//        is[10].issue_name = "भूमि";
//
//        i0.issue_id = 0;
//        i0.issue_name = "इनमें से कोई नहीं";
//
//        i1.issue_id = 1;
//        i1.issue_name = "न्रेगा";
//
//        i2.issue_id = 2;
//        i2.issue_name = "वॉटर कन्सर्वेशन कॅंपेन";
//
//        i3.issue_id = 3;
//        i3.issue_name = "रूरल अर्बन माइग्रेशन कॅंपेन";


//        Loc_district l1 = new Loc_district();
//        Loc_district l2 = new Loc_district();
//        Loc_district l3 = new Loc_district();
//        TopicChannel t1 = new TopicChannel();
//        TopicChannel t2 = new TopicChannel();
//        TopicChannel t3 = new TopicChannel();

//        s1.story_id = 12;
//        s1.ai_id = 12345;
//        s1.detail_id = 10;
//        s1.audioPath = "/home/gramvaani/backup/media/fsmedia/recordings/10/34869.mp3";
//        s1.status_tag2 = "Assigned";
//        s1.time_assigned = "2013-01-07 08:01:11";
//        s1.time_fetched = "2013-01-07 08:01:11";
//        s1.date = "2013-03-07 08:01:11";
//        s1.top_story = 0;
//
//        s2.story_id = 123;
//        s2.ai_id = 123456;
//        s2.detail_id = 101;
//        s2.audioPath = "/home/gramvaani/backup/media/fsmedia/recordings/10/43837.mp3";
//        s2.status_tag2 = "Assigned";
//        s2.time_assigned = "2013-03-07 08:01:11";
//        s2.time_fetched = "2013-01-07 08:01:11";
//        s2.date = "2013-03-07 08:01:11";
//        s2.top_story = 0;

//        t1.topic_channel_id = 1;
//        t1.topic = "स्वास्थ्य";
//
//        t2.topic_channel_id = 2;
//        t2.topic = "शिक्षा";
//
//        t3.topic_channel_id = 3;
//        t3.topic = "कृषि";
//        l1.id = 5;
//        l1.name = "बोकारो";
//        l2.id = 6;
//        l2.name = "राँची";
//
//        l3.id = 7;
//        l3.name = "ईस्ट सिंघहभूम";
//
//        DatabaseHelper dh = new DatabaseHelper("/home/mridu/moderationApp");
//        dh.putObject(s1, "first");
//        dh.putObject(s2, "first");

//        dh.setUser("MRIDU");
        
        //dh.setDatabasePath("/home/mridu/moderationApp");

//        dh.addNamespace("shambhu", "first", "rwx");
//        dh.addNamespace("saraswati", "second", "rwx");
//        dh.addNamespace("baijnath", "third", "rwx");
//        dh.addNamespace("shambhu", "listValues", "rwx");
//        dh.addNamespace("saraswati", "listValues", "rwx");
//        dh.addNamespace("baijnath", "listValues", "rwx");

//        dh.putObject(t1, "listValues");
//        dh.putObject(t2, "listValues");
//        dh.putObject(t3, "listValues");
//        dh.putObject(l1, "listValues");
//        dh.putObject(l2, "listValues");
//        dh.putObject(l3, "listValues");


        //INSERT THE TOPIC CHANNELS
//        for(int i=0;i<9;i++)
//        {
//            dh.putObject(tc[i], "listValues");
//        }
//
//        //INSERT THE LOCATIONS
//        for(int i=0;i<25;i++)
//        {
//            dh.putObject(ld[i], "listValues");
//        }
//
//        //INSERT THE ISSUES
//        for(int i=0;i<11;i++)
//        {
//            dh.putObject(is[i], "listValues");
//        }
//        dh.putObject(i0,"listValues");
//        dh.putObject(i1, "listValues");
//        dh.putObject(i2, "listValues");
//        dh.putObject(i3, "listValues");


//// new start
        SyncObject so = new SyncObject();

        Framework fw = new Framework(8010,"/home/mridu/moderationApp","/home/mridu/moderationApp/Data");
        fw.setCallbackObject(so);
        //fw.setPort(8010);
        //fw.setStoragePath("/home/mridu/moderationApp/Data");
        fw.run();
    }
}
