/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author sathyam
 */
import java.lang.reflect.Field;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sathyam
 */
public class DatabaseHelper {

    Connection con;
    String user = "SATHYAM";
    String databasePath = "/home/mridu/moderationApp";
    
    public void setDatabasePath(String value) {
        databasePath = value;
    }

    public DatabaseHelper(String value) {
        databasePath = value;
        getConnection();
    }

    protected void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("jdbc:sqlite:"+databasePath+"/frameworkdb");
            con = DriverManager.getConnection("jdbc:sqlite:"+databasePath+"/frameworkdb");
            try {
                Statement stat = con.createStatement();
                stat.executeUpdate("CREATE TABLE server(groupid TEXT,key text,value text,user text,datatype text,timestamp number,deviceid text, primary key(groupid,key,timestamp));");
                stat.executeUpdate("CREATE TABLE serverbackup(groupid TEXT,key text,value text,user text,datatype text,timestamp number,deviceid text);");
                stat.executeUpdate("CREATE TABLE namespaces(user TEXT,namespace TEXT,permission TEXT,primary key(user,namespace));");
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void executeQuery(String query) throws Exception {
        if (con != null) {
            boolean flag = true;
            while (flag) {
                try {
                    Statement st1 = con.createStatement();
                    st1.executeUpdate(query);
                    flag = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected ArrayList<String[]> executeQueryForResult(String query) {
        ArrayList<String[]> result = null;
        boolean flag = true;
        while (flag) {
            try {
                if (con != null) {

                    //System.out.println("Inside!!!");
                    PreparedStatement st1 = con.prepareStatement(query);
                    ResultSet rs1 = st1.executeQuery();
                    if (rs1.next()) {
                        result = new ArrayList<String[]>();
                        ResultSetMetaData rsmd = rs1.getMetaData();
                        int col_cnt = rsmd.getColumnCount();
                        do {
                            String[] row = new String[col_cnt];
                            for (int ik = 1; ik <= col_cnt; ik++) {
                                row[ik - 1] = rs1.getString(ik);
                                System.out.print(row[ik - 1] + " ");
                            }
                            result.add(row);
                            System.out.println();
                        } while (rs1.next());
                    } else {
                        result = null;
                    }
                }
                flag = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void addNamespace(String user, String namespace, String permission) {
        try {
            String query = "insert into namespaces values('" + user + "','" + namespace + "','" + permission + "');";
            //System.out.println("Query :" + query);
            executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeNamespace(String user, String namespace, String permission) {
        try {
            String query = "delete from namespaces where user='" + user + "' and namespace='" + namespace + "' and permission='" + permission + "';";
            //System.out.println("Query :" + query);
            executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ArrayList<String[]> selectRecords(String gid) {
        String query = "select rowid,* from server where groupid='" + gid + "';";
        ArrayList<String[]> result = executeQueryForResult(query);

        return result;
    }

    protected ArrayList<String[]> getNamespaces() {
        String query = "select namespace from namespaces;";
        ArrayList<String[]> namespaces = new ArrayList<String[]>();
        namespaces = executeQueryForResult(query);
        return namespaces;
    }

    public ArrayList<String[]> getNamespaceRecords(String user) {
        //System.out.println("USER : "+user);
        String query = "select * from namespaces where user='" + user + "';";
        ArrayList<String[]> result = executeQueryForResult(query);
        return result;
    }

    protected ArrayList<String[]> selectNewRecords(String user, long rowid, String deviceId) {
        String query = "select namespace from namespaces where user='" + user + "';";
        ArrayList<String[]> namespaces = executeQueryForResult(query);
        ArrayList<String[]> result = null;
        for (String s[] : namespaces) {
            query = "select rowid,* from server where rowid>" + rowid + " and key like '%" + s[0] + "%' and deviceid<>'" + deviceId + "' order by timestamp, datatype desc, groupid;";
            ArrayList<String[]> records = executeQueryForResult(query);
            if (records != null && result == null) {
                result = new ArrayList<String[]>();
            }
            result.addAll(records);
        }
        return result;
    }

    protected ArrayList<String[]> selectNewRecords(ArrayList<String[]> namespaces, Long[] ids, String deviceId) {
//        String query = "select namespace from namespaces where user='"+user+"';";
//        ArrayList<String[]> namespaces = executeQueryForResult(query);
        if (namespaces == null || ids == null) {
            return null;
        }
        ArrayList<String[]> result = null;
        String query = "";
        for (int i = 0; i < namespaces.size(); i++) {
            if (namespaces.get(i)[2].indexOf("r") != -1) {
                query = "select rowid,* from server where rowid>" + ids[i] + " and key like '%" + namespaces.get(i)[1] + "%' and deviceid<>'" + deviceId + "' order by timestamp, datatype desc, groupid;";
                ArrayList<String[]> records = executeQueryForResult(query);
                if (records != null) {
                    if (result == null) {
                        result = new ArrayList<String[]>();
                    }
                    result.addAll(records);
                }
            }
        }
        return result;
    }
//    protected static ArrayList<String[]> selectNewRecords(long rowid) {
//        String query = "select rowid,* from server where rowid>"+rowid+";";
//        ArrayList<String[]> result = executeQueryForResult(query);
//        return result;
//    }

//    public static void setSynched(ArrayList<Record> r) {
//        for (int i = 0; i < r.size(); i++) {
//            try {
//                executeQuery("UPDATE server SET synched='Y' WHERE groupid = " + r.get(i).groupid + " AND key = '" + r.get(i).key + "' AND value = '" + r.get(i).value + "' AND timestamp = " + r.get(i).timestamp + " AND synched ='N' ");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    protected void closeConnection() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putObject(Object obj, String namespace) {
        boolean flag = true;
        while (flag) {
            try {
                Class c = obj.getClass();
                String className = c.getName();
                Field[] fields = c.getDeclaredFields();
                Record[] r = new Record[fields.length];
                int i = 0;
                for (Field f : fields) {
                    r[i] = new Record();
                    String fieldName = f.getName();
                    r[i].key = namespace + "." + className + "." + fieldName;
                    r[i].value = "";
                    if (f.get(obj) != null) {
                        r[i].value = f.get(obj).toString();
                        if (fieldName.contains("Path")) {
                            r[i].datatype = "file";
                        } else {
                            r[i].datatype = "data";
                        }
                    } else {
                        r[i].datatype = "";
                        r[i].value = "";
                    }
                    i++;
                }
                putToRepository(r);
                flag = false;
                // Events e = (Events) obj;
                //e.doJob(getObject("framework.DatabaseHelper"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Record> getRecords(String className) {
        ArrayList<Record> records = null;
        String query = "select * from server where key like '%" + className + "%' order by groupid;";
        ArrayList<String[]> result = executeQueryForResult(query);
        if (result != null) {
            records = new ArrayList<Record>();
            int l = result.size();
            int i = 0;
            do {
                Record r = new Record();
                r.groupid = result.get(i)[0];
                r.key = result.get(i)[1];
                r.value = result.get(i)[2];
                r.user = result.get(i)[3];
                r.datatype = result.get(i)[4];
                r.timestamp = Long.parseLong(result.get(i)[5]);
                records.add(r);
                i++;
            } while (i < l);
        }
        return records;
    }

    private ArrayList<Record> getRecords(String className, String groupid) {
        ArrayList<Record> records = null;
        String query = "select * from server where groupid='" + groupid + "' and key like '%" + className + "%';";
        ArrayList<String[]> result = executeQueryForResult(query);
        if (result != null) {
            records = new ArrayList<Record>();
            int l = result.size();
            int i = 0;
            do {
                Record r = new Record();
                r.groupid = result.get(i)[0];
                r.key = result.get(i)[1];
                r.value = result.get(i)[2];
                r.user = result.get(i)[3];
                r.datatype = result.get(i)[4];
                r.timestamp = Long.parseLong(result.get(i)[5]);
                records.add(r);
                i++;
            } while (i < l);
        }
        return records;
    }

    private String getValue(ArrayList<Record> ans, String name) {
        for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i).key + " " + ans.get(i).value);
            String key = ans.get(i).key;
            if (name.equals(key.substring(key.lastIndexOf(".") + 1))) {
                return ans.get(i).value;
            }
        }
        return null;
    }

//    public Object getObject(String className) {
//        ArrayList<Record> records = getRecords(className);
//        if (records != null) {
//            try {
//                Class c = Class.forName(className);
//                Field[] fields = c.getDeclaredFields();
//                Object obj = c.newInstance();
//                for (Field f : fields) {
//                    String name = f.getName();
//                    String type = f.getType().toString();
//                    String value = getValue(records,name);//records.get(i).value;
//                    System.out.println(name + " " + type + " " + value);
//                    if (type.equals("int")) {
//                        f.set(obj, (Integer.parseInt(value)));
//                    }
//                    if (type.equals("long")) {
//                        f.set(obj, Long.parseLong(value));
//                    }
//                    if (type.equals("float")) {
//                        f.set(obj, (Float.parseFloat(value)));
//                    }
//                    if (type.equals("double")) {
//                        f.set(obj, (Double.parseDouble(value)));
//                    }
//                    if (type.equals("class java.lang.String")) {
//                        f.set(obj, (value));
//                    }
//                    if (type.equals("char")) {
//                        f.set(obj, value.charAt(0));
//                    }
//                }
//                return obj;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
    public Object getObject(String className, ArrayList<Record> records) {
        if (records != null) {
            System.out.println("-------------------------------------------" + className);
            try {
                Class c = Class.forName(className);
                Field[] fields = c.getDeclaredFields();
                Object obj = c.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    String type = f.getType().toString();
                    String value = getValue(records, name);//records.get(i).value;
                    System.out.println(name + " " + type + " " + value);
                    if (type.equals("int")) {
                        f.set(obj, (Integer.parseInt(value)));
                    }
                    if (type.equals("long")) {
                        f.set(obj, Long.parseLong(value));
                    }
                    if (type.equals("float")) {
                        f.set(obj, (Float.parseFloat(value)));
                    }
                    if (type.equals("double")) {
                        f.set(obj, (Double.parseDouble(value)));
                    }
                    if (type.equals("class java.lang.String")) {
                        f.set(obj, (value));
                    }
                    if (type.equals("char")) {
                        f.set(obj, value.charAt(0));
                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object getObject(String className, String groupid) {
        ArrayList<Record> records = getRecords(className, groupid);
        if (records != null) {
            System.out.println("-------------------------------------------" + className);
            try {
                Class c = Class.forName(className);
                Field[] fields = c.getDeclaredFields();
                Object obj = c.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    String type = f.getType().toString();
                    String value = getValue(records, name);//records.get(i).value;
                    System.out.println(name + " " + type + " " + value);
                    if (type.equals("int")) {
                        f.set(obj, (Integer.parseInt(value)));
                    }
                    if (type.equals("long")) {
                        f.set(obj, Long.parseLong(value));
                    }
                    if (type.equals("float")) {
                        f.set(obj, (Float.parseFloat(value)));
                    }
                    if (type.equals("double")) {
                        f.set(obj, (Double.parseDouble(value)));
                    }
                    if (type.equals("class java.lang.String")) {
                        f.set(obj, (value));
                    }
                    if (type.equals("char")) {
                        f.set(obj, value.charAt(0));
                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Set> getObjects(String className) {
        ArrayList<Record> records = getRecords(className);
        if (records != null) {
            String key = records.get(0).key;
            String namespace = key.substring(0, key.indexOf("."));
            System.out.println("NAMESPACE IS:" + namespace);
            ArrayList<Set> objects = new ArrayList<Set>();
            int l = records.size();
            Record p = records.get(0);
            Record c = records.get(0);
            int i = 0;
            while (i < l) {
                ArrayList<Record> r = new ArrayList<Record>();
                Set o = new Set();
                while (p.groupid.equals(c.groupid)) {
                    r.add(c);
                    p = c;
                    i++;
                    if (i == l) {
                        break;
                    }
                    c = records.get(i);
                }
                for (int f = 0; f < r.size(); f++) {
                    printRecord(r.get(f));
                }
                o.groupid = p.groupid;
                o.namespace = namespace;
                o.obj = getObject(className, r);
                objects.add(o);
                p = c;
            }
            return objects;
        }
        System.out.println("RETURNING NULL AS RECORDS IN NULL");
        return null;
    }

    public void printRecord(Record r) {
        System.out.println("PRINTING RECORDS" + r.datatype + " " + r.groupid + " " + r.key);
    }
//    public ArrayList<Object> getObjects(String className)
//    {
//        ArrayList<Record> records = getRecords(className);
//        if(records!=null)
//        {
//            ArrayList<Object> objects = new ArrayList<Object>();
//            int l = records.size();
//            Record p = records.get(0);
//            Record c = records.get(0);
//            int i=0;
//            while(i<l)
//            {
//                ArrayList<Record> r = new ArrayList<Record>();
//                while(p.groupid==c.groupid)
//                {
//                    r.add(c);
//                    p = c;
//                    i++;
//                    if(i==l)
//                        break;
//                    c = records.get(i);
//                }
//                objects.add(getObject(className, r));
//                p = c;
//            }
//            return objects;
//        }
//        return null;
//    }

//    private int getID()
//    {
//            String s = Long.toString(System.currentTimeMillis());
//            //System.out.println(s.substring(4));
//            int r = Integer.parseInt(s.substring(4));
//            //System.out.println(r);
//            return r;
//    }
    public void setUser(String value) {
        user = value;
    }

    public void putToRepository(Record[] records) {
        String groupid = UUID.randomUUID().toString();//getID();
        //String user = "SATHYAM";
        boolean flag = true;
        long timestamp = System.currentTimeMillis();
        if (con != null) {
            while (flag) {

                try {
                    con.setAutoCommit(false);
                    for (int i = 0; i < records.length; i++) {
                        String query = "insert into server values('" + groupid + "','" + records[i].key + "','" + records[i].value + "','" + user + "','" + records[i].datatype + "'," + timestamp + ",'SERVER');";
                        System.out.println(query);
                        executeQuery(query);
                        //Thread.sleep(1000);
                        //insert(groupid,records[i].key, records[i].value, user, records[i].datatype, timestamp, "N");
                    }
                    con.commit();
                    flag = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    if (con != null) {
                        try {
                            System.err.print("Transaction is being rolled back");
                            con.rollback();
                            flag = true;
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                } finally {
                    try {
                        con.setAutoCommit(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            System.out.println("Connection broken!!!");
        }
    }

    public void putToRepository(String gid, String key, String value, String user, String datatype, long timestamp, String deviceid) {
        String query = "insert into server values('" + gid + "','" + key + "','" + value + "','" + user + "','" + datatype + "," + timestamp + ",'" + deviceid + "')";
        try {
            executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*    private ArrayList<ServerRecord> getConflictingRecords(String key) {
ArrayList<ServerRecord> records = null;
String query = "select * from serverbackup where key = '" + key + "' order by deviceid,timestamp;";
ArrayList<String[]> result = executeQueryForResult(query);
if (result!=null) {
records = new ArrayList<ServerRecord>();
int l = result.size();
int i=0;
do {
ServerRecord r = new ServerRecord();
r.groupid = result.get(i)[0];
r.key = result.get(i)[1];
r.value = result.get(i)[2];
r.user = result.get(i)[3];
r.datatype = result.get(i)[4];
r.timestamp = Long.parseLong(result.get(i)[5]);
r.deviceId = result.get(i)[6];
records.add(r);
i++;
} while (i<l);
}
return records;
}
// modify this function based on primary key
private ServerRecord getCurrentRecord(String key)
{
ServerRecord r = null;
String query = "select * from server where key = '" + key + "';";
ArrayList<String[]> result = executeQueryForResult(query);
if (result!=null) {
r = new ServerRecord();
r.groupid = result.get(0)[0];
r.key = result.get(0)[1];
r.value = result.get(0)[2];
r.user = result.get(0)[3];
r.datatype = result.get(0)[4];
r.timestamp = Long.parseLong(result.get(0)[5]);
r.deviceId = result.get(0)[6];
}
return r;
}

public ArrayList<ConflictingRecordSet> getConflictingRecordSet()
{
ArrayList<ConflictingRecordSet> cr = null;
String query = "select distinct key from serverbackup;";
ArrayList<String[]> result = executeQueryForResult(query);
if(result!=null) {
cr = new ArrayList<ConflictingRecordSet>();
int l = result.size();
int i = 0;
do {
ConflictingRecordSet r = new ConflictingRecordSet();
r.currentRecord = new ServerRecord();
r.conflictingRecords = new ArrayList<ServerRecord>();
String key = result.get(i)[0];
r.currentRecord = getCurrentRecord(key);
r.conflictingRecords = getConflictingRecords(key);
cr.add(r);
} while(i<l);
}
return cr;
}

public void updateRecord(ServerRecord r)
{
long timestamp = System.currentTimeMillis();
boolean flag = true;
while(flag)
{
try {
con.setAutoCommit(false);
String query = "delete from server  where groupid='" + r.groupid + "' and key='" + r.key + "'";
System.out.println("Query :" + query);
executeQuery(query);
query = "insert into server values('" + r.groupid + "','" + r.key + "','" + r.value + "','" + r.user + "','" + r.datatype + "'," + r.timestamp + ",'" + r.deviceId + "')";
System.out.println("Query :" + query);
executeQuery(query);
query = "update server set timstamp=" + timestamp + " where groupid='"+r.groupid+"'";
System.out.println("Query :" + query);
executeQuery(query);
query = "delete from serverbackup where groupid='" + r.groupid + "'";
System.out.println("Query :" + query);
executeQuery(query);
con.commit();
flag = false;
} catch (Exception e) {
if (con!= null) {
try {
System.err.print("Transaction is being rolled back");
con.rollback();
Thread.sleep(2000);
} catch(Exception e1) {
e1.printStackTrace();
}
}
e.printStackTrace();
}
}
}
} */

/*
public static void getConnection() {
String url = "jdbc:postgresql://localhost/frameworkdb";
String user = "postgres";
String password = "abc";
try {
Class.forName("org.postgresql.Driver");
con = DriverManager.getConnection(url, user, password);
} catch (Exception e) {
e.printStackTrace();
}
}

public static void closeConnection() {
try {
con.close();
} catch (Exception e) {
}
}

public static void executeQuery(String query) throws Exception {
if (con != null) {
Statement st1 = con.createStatement();
st1.executeUpdate(query);
}
}

public static String executeQueryForResult(String query) {
String result = "null";
try {
if (con != null) {
System.out.println("Inside!!!");
PreparedStatement st1 = con.prepareStatement(query);
ResultSet rs1 = st1.executeQuery();
ResultSetMetaData rsmd = rs1.getMetaData();
int col_cnt = rsmd.getColumnCount();
String value = "";
while (rs1.next()) {
for (int ik = 1; ik <= col_cnt; ik++) {
value = rs1.getString(ik);
System.out.print(value + " ");
}
result = value;
System.out.println();
}
}
} catch (Exception e) {
}
return result;
}*/
