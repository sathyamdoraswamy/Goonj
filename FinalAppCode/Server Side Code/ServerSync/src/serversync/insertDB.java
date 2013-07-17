
package serversync;

import java.io.FileWriter;
import java.io.IOException;
import com.mysql.jdbc.*;
import framework.DatabaseHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mridu
 */
public class insertDB {

  public static void main(String[] args) {
        // TODO code application logic here

        Story s = new Story();

        int size = args.length;
        System.out.println("Number of parameters we got = "+size);
        String buf = args[0];
        s.story_id = Integer.parseInt(buf);

        System.out.println(args[1]);
        //String arr = args[1].spilt("/");
        String str = args[1].replaceFirst("/", " ");
        System.out.println(str);
        s.date = str;
        //if(size==5)
            //s.call_number = args[4];
        //s.call_number = "9818844417";
        buf = args[2];
        s.ai_id = Integer.parseInt(buf);
        buf = args[3];
        s.detail_id = Integer.parseInt(buf);
        s.top_story = 0;
        s.status_tag2 = "Assigned";
        s.audioPath = "/home/mridu/audios/" + s.ai_id + "_" + s.detail_id + ".mp3";
        //s.audioPath = "/home/gramvaani/backup/media/fsmedia/recordings/" + s.ai_id + "/" + s.detail_id + ".mp3";
        s.time_fetched = str ;
        s.community_rep_id = Integer.parseInt(args[4]);
        String str1 = args[5].replaceFirst("/", " ");
        s.time_assigned = str1;
        if(size==7)
            s.call_number = args[6];
        else
            s.call_number = "EMPTY";

//        MySQLAccess m = new MySQLAccess();
//        String s1 = s.createInsertStmt();
//        System.out.println(s1);
//        //insert value into my goonjModDB
//        try
//        {
//            m.insertValue(s1);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }


        //Framework.getDatabaseHelper().putObject(s,"first");
        DatabaseHelper  dh = new DatabaseHelper("/home/mridu/moderationApp");
        dh.setUser("MRIDU");
        if(s.community_rep_id==1)
            dh.putObject(s, "first");
        else if(s.community_rep_id==2)
            dh.putObject(s, "second");
        else if(s.community_rep_id==3)
            dh.putObject(s,"third");
//
//                try {
//		//write converted json data to a file named "file.json"
//		FileWriter writer = new FileWriter("/home/mridu/Desktop/fromPy");
//		writer.write(" " +args[0]);
//		writer.write(" "+args[1]);
//		writer.write(" "+ args[2]);
//                writer.write(" Success Modified!!");
//		writer.close();
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	}


//        TopicChannel t1 = new TopicChannel();
//        TopicChannel t2 = new TopicChannel();
//        TopicChannel t3 = new TopicChannel();
//
//        Issue i1 = new Issue();
//        Issue i2 = new Issue();
//        Issue i3 = new Issue();
//
//        Loc_district l1 = new Loc_district();
//        Loc_district l2 = new Loc_district();
//        Loc_district l3 = new Loc_district();
//
////        Story s1 = new Story();
////        Story s2 = new Story();
////        Story s3 = new Story();
//                t1.topic_channel_id = 1;
//        t1.topic = "स्वास्थ्य";
//
//        t2.topic_channel_id = 2;
//        t2.topic = "शिक्षा";
//
//        t3.topic_channel_id = 3;
//        t3.topic = "कृषि";
//
//        i1.issue_id = 1;
//        i1.issue_name = "न्रेगा";
//
//        i2.issue_id = 2;
//        i2.issue_name = "वॉटर कन्सर्वेशन कॅंपेन";
//
//        i3.issue_id = 3;
//        i3.issue_name = "रूरल अर्बन माइग्रेशन कॅंपेन";
//
//        l1.id = 1;
//        l1.name = "बोकारो";
//        l2.id = 2;
//        l2.name = "राँची";
//
//        l3.id = 3;
//        l3.name = "ईस्ट सिंघहभूम";
//
//                s1.story_id = 12;
//        s1.ai_id = 12345;
//        s1.detail_id = 10;
//        s1.audioPath = "/home/mridu/Desktop/audio1.mp3";
//        s1.status_tag2 = "Assigned";
//        s1.time_assigned = "2013-01-07 08:01:11";
//        s1.time_fetched = "2013-01-07 08:01:11";
//        s1.date = "2013-03-07 08:01:11";
//        s1.top_story = 0;
//        s1.call_number = "9818844417";
//
//
//        s2.story_id = 123;
//        s2.ai_id = 123456;
//        s2.detail_id = 101;
//        s2.audioPath = "/home/mridu/Desktop/audio2.mp3";
//        s2.status_tag2 = "Assigned";
//        s2.time_assigned = "2013-03-07 08:01:11";
//        s2.time_fetched = "2013-01-07 08:01:11";
//        s2.date = "2013-03-07 08:01:11";
//        s2.top_story = 0;
//        s2.call_number = "9818962835";
//
//        s3.story_id = 124;
//        s3.ai_id = 123456;
//        s3.detail_id = 101;
//        s3.audioPath = "/home/mridu/Desktop/audio3.mp3";
//        s3.status_tag2 = "Assigned";
//        s3.time_assigned = "2014-03-07 08:01:11";
//        s3.time_fetched = "2013-01-07 08:01:11";
//        s3.date = "2013-03-07 08:01:11";
//        s3.top_story = 0;
//        s3.call_number = "981884417";
//
//
//        //Now code to insert into Sathyam sb
//      DatabaseHelper dh = Framework.getDatabaseHelper();
//      if(dh==null)
//          System.out.println("DH is NULL");
//      Framework.getDatabaseHelper().putObject(t1,"first");
//      Framework.getDatabaseHelper().putObject(t2,"second");
//      Framework.getDatabaseHelper().putObject(t3,"first");
//      Framework.getDatabaseHelper().putObject(i1,"first");
//      Framework.getDatabaseHelper().putObject(i2,"second");
//      Framework.getDatabaseHelper().putObject(i3,"third");
//      Framework.getDatabaseHelper().putObject(l1,"first");
//      Framework.getDatabaseHelper().putObject(l2,"second");
//      Framework.getDatabaseHelper().putObject(l3,"third");
//
//      Framework.getDatabaseHelper().putObject(s1,"first");
//      Framework.getDatabaseHelper().putObject(s2,"first");
//      Framework.getDatabaseHelper().putObject(s3,"first");


    }

}
