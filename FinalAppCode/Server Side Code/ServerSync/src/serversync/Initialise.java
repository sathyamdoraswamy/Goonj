/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serversync;

import framework.DatabaseHelper;

/**
 *
 * @author mridu
 */
public class Initialise {

    public static void main(String[] args)
    {
        TopicChannel[] tc = new TopicChannel[9];
        for(int i =0;i<9;i++)
        {
            tc[i] = new TopicChannel();
            tc[i].topic_channel_id = (i+1);
        }
        tc[0].topic = "संस्कृति और मनोरंजन";
        tc[1].topic = "अपराध";
        tc[2].topic = "कृषि";
        tc[3].topic = "स्वास्थ्य";
        tc[4].topic = "जीविका";
        tc[5].topic = "पर्यावर्ण";
        tc[6].topic = "शासन";
        tc[7].topic = " शिक्षा";
        tc[8].topic = "इनमें से कोई नई";


        //THE LOC_DISTRICT CHANNELS
        Loc_district[] ld = new Loc_district[25];
        for(int i =0;i<25;i++)
        {
            ld[i] = new Loc_district();
            ld[i].id = (i+1);
        }
        ld[0].name = "बोकारो";
        ld[1].name = "गिरीडीह";
        ld[2].name = "चतरा";
        ld[3].name = "इनमें से कोई नई";
        ld[4].name = "धनबाद";
        ld[5].name = "रांची";
        ld[6].name = "लातेहार";
        ld[7].name = "गढ़वा";
        ld[8].name = "देवगढ़";
        ld[9].name = "रामगढ़";
        ld[10].name = "पश्चिमी सिंहभूम";
        ld[11].name = "साहिबगंज";
        ld[12].name = "पूर्वी सिंहभूम";
        ld[13].name = "हजारीबाग";
        ld[14].name = "सेरइकेल्ला-खारसावन";
        ld[15].name = "दुमका";
        ld[16].name = "पलामू";
        ld[17].name = "लोहरदगा";
        ld[18].name = "सींदेगा";
        ld[19].name = "जामताड़ा";
        ld[20].name = "गोड्डा";
        ld[21].name = "कोडरमा";
        ld[22].name = "पाकुर";
        ld[23].name = "गुमला";
        ld[24].name = "खूंटी";

        //Issue i0 = new Issue();
        //Issue i1 = new Issue();
        //Issue i2 = new Issue();
        //Issue i3 = new Issue();
        Issue[] is = new Issue[11];

        for(int i=0;i<is.length;i++)
        {
            is[i] = new Issue();
            is[i].issue_id = i;
        }

        is[0].issue_name = "इनमें से कोई नही";
        is[1].issue_name = "न्रेगा";
        is[2].issue_name = "शिक्षा अभियान";
        is[3].issue_name = "विस्थापन";
        is[4].issue_name = "खनन";
        is[5].issue_name = "पी.डी.एस";
        is[6].issue_name = "बुनियादी ढांचे";
        is[7].issue_name = "स्थानान्तरण";
        is[8].issue_name = "जल प्रदूषण";
        is[9].issue_name = "एस.एच.जी";
        is[10].issue_name = "भूमि";


        DatabaseHelper dh = new DatabaseHelper("/home/mridu/moderationApp");
        dh.setUser("MRIDU");

        dh.addNamespace("shambhu", "first", "rwx");
        dh.addNamespace("saraswati", "second", "rwx");
        dh.addNamespace("baijnath", "third", "rwx");
        dh.addNamespace("shambhu", "listValues", "rwx");
        dh.addNamespace("saraswati", "listValues", "rwx");
        dh.addNamespace("baijnath", "listValues", "rwx");

        for(int i=0;i<9;i++)
        {
            dh.putObject(tc[i], "listValues");
        }

        //INSERT THE LOCATIONS
        for(int i=0;i<25;i++)
        {
            dh.putObject(ld[i], "listValues");
        }

        //INSERT THE ISSUES
        for(int i=0;i<11;i++)
        {
            dh.putObject(is[i], "listValues");
        }
    }
}
