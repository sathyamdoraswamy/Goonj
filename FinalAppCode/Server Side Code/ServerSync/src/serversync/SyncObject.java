/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;


import framework.Events;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author mridu
 */
public class SyncObject implements Events {

    public synchronized void doJob(Object obj)
    {
        if(obj==null)
            return;
        String class_name = obj.getClass().toString();

        System.out.println("-------------------------------------------------------------");
        System.out.println("CLASS NAME FOUND: "+class_name);
        System.out.println("-------------------------------------------------------------");
        MySQLAccess m = new MySQLAccess();

        try
        {
        if(class_name.equals("class serversync.CommunityRep"))
        {
            CommunityRep cr = (CommunityRep) obj;
            String s = cr.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.Story"))
        {
            Story st = (Story) obj;
            String s = st.createUpdateStmt();
            System.out.println("-------------------------------------------------------------");
            System.out.println("UPDATE QUERY IS: "+ s);
            System.out.println("-------------------------------------------------------------");

            m.insertValue(s);


            System.out.println("ISSUE STORY PATH ="+st.issueStoryPath);
            System.out.println("TOPIC STORY PATH ="+ st.topicStoryPath);

            if(!st.issueStoryPath.equals(""))
            {
                File i = new File(st.issueStoryPath);

                try
                {
                    ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(i));
                    Object oi = ois1.readObject();
                    IssueStoryRelation isr = (IssueStoryRelation)oi;
                    System.out.println("is_id = " + isr.is_id);
                    System.out.println("story_id =" + isr.story_id);
                    System.out.println("issue_id = "+ isr.issue_id);
                    ois1.close();

                    s = isr.createInsertStmt();
                    System.out.println("-------------------------------------------------------------");
                    System.out.println("INSERT QUERY ISR: "+ s);
                    System.out.println("-------------------------------------------------------------");

                    m.insertValue(s);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(!st.topicStoryPath.equals(""))
            {
                File t = new File(st.topicStoryPath);
                try
                {
                    ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(t));
                    Object ot = ois2.readObject();
                    ois2.close();
                    StoryTcRelation stc = (StoryTcRelation)ot;
                    System.out.println("stc_id = " + stc.stc_id);
                    System.out.println("story_id =" + stc.story_id);
                    System.out.println("topic_id = "+ stc.topic_channel_id);
                    
                    s = stc.createInsertStmt();
                    System.out.println("-------------------------------------------------------------");
                    System.out.println("INSERT QUERY STC "+ s);
                    System.out.println("-------------------------------------------------------------");
                    
                    m.insertValue(s);

                   
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(class_name.equals("class serversync.Issue"))
        {
            Issue i = (Issue) obj;
            String s = i.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.TopicChannel"))
        {
            TopicChannel tc = (TopicChannel) obj;
            String s = tc.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.Loc_district"))
        {
            Loc_district dist = (Loc_district) obj;
            String s = dist.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.CommunityRep"))
        {
            Loc_village vill = (Loc_village) obj;
            String s = vill.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.IssueStoryRelation"))
        {
            IssueStoryRelation isr = (IssueStoryRelation) obj;
            String s = isr.createInsertStmt();
            m.insertValue(s);
        }
        if(class_name.equals("class serversync.StoryTcRelation"))
        {
            StoryTcRelation stc = (StoryTcRelation) obj;
            String s = stc.createInsertStmt();
            m.insertValue(s);
        }
       if(class_name.equals("class serversync.StoryCrepRelation"))
        {
            StoryCrepRelation scr = (StoryCrepRelation) obj;
            String s = scr.createInsertStmt();
            m.insertValue(s);
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
