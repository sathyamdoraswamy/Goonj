///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package serversync;
//
//import java.beans.Statement;
//import java.lang.reflect.Field;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.util.ArrayList;
//
///**
// *
// * @author sathyam
// */
//public class Function {
//
//    static Connection con;
//
//    public Function()
//    {
//        getConnection();
//    }
//
//    public static void print(Temp o) {
//        System.out.println("a : " + o.a);
//        System.out.println("b : " + o.b);
//        System.out.println("t : " + o.t);
//        System.out.println("c : " + o.c);
//        System.out.println("d : " + o.d);
//    }
//
////    public static void main(String[] args) throws Exception {
////        getConnection();
////        Reflections r = new Reflections();
////        Temp obj = new Temp();
////        obj.a = 1;
////        obj.b = 3.14;
////        obj.t = System.currentTimeMillis();
////        obj.c = 'S';
////        obj.d = "HI!";
////        r.putObject(obj);
////        Temp o = (Temp) r.getObject("reflections.Temp");
////        print(o);
////        closeConnection();
////    }
//
//    private static void getConnection() {
//        String url = "jdbc:postgresql://localhost/frameworkdb";
//        String user = "postgres";
//        String password = "abc";
//        try {
//            Class.forName("org.postgresql.Driver");
//            con = DriverManager.getConnection(url, user, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void closeConnection() {
//        try {
//            con.close();
//        } catch (Exception e) {
//        }
//    }
//
//    private void executeQuery(String query) {
//        try {
//            if (con != null) {
//                System.out.println("Inside!!!");
//                PreparedStatement st1 = con.prepareStatement(query);
//                st1.executeUpdate();
//                //ResultSet rs1 = st1.executeQuery();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();;
//        }
//    }
//
//    private static ArrayList<Record> getRecords(String query) {
//        ArrayList<Record> records = null;
//        try {
//            if (con != null) {
//                System.out.println("Inside!!!");
//                PreparedStatement st1 = con.prepareStatement(query);
//                ResultSet rs1 = st1.executeQuery();
//                if (rs1 != null) {
//                    records = new ArrayList<Record>();
//                    while (rs1.next()) {
//                        Record r = new Record();
//                        r.key = rs1.getString(1);
//                        r.value = rs1.getString(2);
//                        r.user = rs1.getString(3);
//                        r.datatype = rs1.getString(4);
//                        r.timestamp = Long.parseLong(rs1.getString(5));
//                        records.add(r);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return records;
//    }
//
//    public void putObject(Object obj) {
//        try {
//            Class c = obj.getClass();
//            String className = c.getName();
//            Field[] fields = c.getDeclaredFields();
//            Record[] r = new Record[fields.length];
//            int i = 0;
//            for (Field f : fields) {
//                r[i] = new Record();
//                String fieldName = f.getName();
//                r[i].key = className + "." + fieldName;
//                r[i].value = "";
//                if (f.get(obj) != null) {
//                    r[i].value = f.get(obj).toString();
//                }
//                if (fieldName.contains("path")) {
//                    r[i].datatype = "file";
//                } else {
//                    r[i].datatype = "data";
//                }
//                i++;
//            }
//            for (i = 0; i < r.length; i++) {
//                String query = "insert into serverdb values('" + r[i].key + "','" + r[i].value + "','SATHYAM','" + r[i].datatype + "',1,'Y','ID');";
//                System.out.println(query);
//                executeQuery(query);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private String getValue(ArrayList<Record> ans,String name)
//    {
//        for(int i=0;i<ans.size();i++)
//        {
//            String key = ans.get(i).key;
//            if(name.equals(key.substring(key.lastIndexOf(".")+1)))
//                    return ans.get(i).value;
//        }
//        return null;
//    }
//    public Object getObject(String className) {
//        String query = "select * from serverdb where key like '" + className + ".%';";
//        ArrayList<Record> ans = getRecords(query);
//        if (ans != null) {
//            try {
//                Class c = Class.forName(className);
//                Field[] fields = c.getDeclaredFields();
//                Object obj = c.newInstance();
//                int i = 0;
//                for (Field f : fields) {
//                    String name = f.getName();
//                    String type = f.getType().toString();
//                    String value = getValue(ans,name);//ans.get(i).value;
//                    System.out.println(name + " " + type + " " + value);
//                    if (type.equals("int")) {
//                        f.set(obj, (Integer.parseInt(value)));
//                    }
//                    if (type.equals("float")) {
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
//                    i++;
//                }
//                return obj;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
/////*
//// * To change this template, choose Tools | Templates
//// * and open the template in the editor.
//// */
////package reflections;
////
////import java.beans.Statement;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.ObjectInputStream;
////import java.io.ObjectOutputStream;
////import java.lang.reflect.Field;
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////import java.sql.ResultSetMetaData;
////import java.util.ArrayList;
////
/////**
//// *
//// * @author sathyam
//// */
////    class Temp1
////	{
////		Integer a;
////		Double b;
////		long t;
////		char c;
////		String d;
////                byte[] bb;
////	}
////public class Function {
//////    static Connection con;
////     public static void getConnection() {
////        String url = "jdbc:postgresql://localhost/frameworkdb";
////        String user = "postgres";
////        String password = "abc";
////        try {
////            Class.forName("org.postgresql.Driver");
////            //con = DriverManager.getConnection(url, user, password);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    public static void closeConnection() {
////        try {
////           // con.close();
////        } catch (Exception e) {
////        }
////    }
////
////    public static void executeQuery(String query) throws Exception {
////        if (con != null) {
////            java.sql.Statement st1 =  con.createStatement();
////            st1.executeUpdate(query);
////        }
////    }
////
////    public static String executeAndTest(String query) {
////        String result = "";
////        try {
////            if (con != null) {
////                System.out.println("Inside!!!");
////                PreparedStatement st1 = con.prepareStatement(query);
////                ResultSet rs1 = st1.executeQuery();
////                ResultSetMetaData rsmd = rs1.getMetaData();
////                int col_cnt = rsmd.getColumnCount();
////                String value = "";
////                while (rs1.next()) {
////                    for (int ik = 1; ik <= col_cnt; ik++) {
////                        value = rs1.getString(ik);
////                        System.out.print(value + " ");
////                        result += value+" ";
////                    }
////                    result += "\n";
////                    System.out.println();
////                }
////            }
////        } catch (Exception e) {
////        }
////        return result;
////    }
////
////    public Function(){}
////    public void printMe()throws Exception
////    {
////        getConnection();
////        String query = "select * from serverdb where timestamp=1363181287640 and value='1'";
////        String ans = executeAndTest(query);
////        String[] a = ans.split(" ");
////        System.out.println(ans);
////        Class c = Class.forName("reflections.Temp");
////        Field[] fields = c.getDeclaredFields();
////        Class c1 = Class.forName("reflections.Temp");
////        Object obj = c1.newInstance();
////        ArrayList<Object> result = new ArrayList<Object>();
////        int i=0;
////        a[0]="1";
////        for(Field f:fields) {
////            System.out.println(a.length+" "+i+" "+f.getName()+" "+f.getGenericType()+" ");
////            Class t = f.getType();
////            Object o = Integer.valueOf(a[i]);
////            //t = t.getSuperclass();
////            String name = t.getCanonicalName();
////            f.set(obj,(Class.forName(name).cast(o)));
////            System.out.println(a.length+" "+i+" "+f.getName()+" "+f.getGenericType()+" ");
//////            i++;
//////            //Sender side
//////            //key value user datatype timestamp synched
//////            String className = c.getSimpleName();
//////            String fieldName = f.getName();
//////            String key = className + "." + fieldName;
//////            String value = "";
//////            if(f.get(this)!=null)
//////                value = f.get(this).toString();
//////            String user = "Sathyam";//or provided
//////            String datatype;
//////            if(fieldName.contains("path"))
//////                datatype = "file";
//////            else
//////                datatype = "data";
//////            long ts = System.currentTimeMillis();
//////            //put all into database
//////
//////
//////
//////            if(f.get(this)!=null)
//////            {
//////
//////            Object o = f.get(this);
//////            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("abc.txt"));
//////            //oos.write((byte[])o);
//////            oos.writeObject(o);
//////            oos.close();
//////            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("abc.txt"));
//////            byte[] b = new byte[100000];
//////            ois.read(b);
//////            System.out.println("TAG: "+new String(b));
//////            Object o1 = ois.readObject();
//////            System.out.println("TAG: "+o1);
//////            ois.close();
//////
//////            //Receiver side
//////            //take classNamepezz
//////            //select distinct timestamp from table where key like className.% and synched='N'(if required)
//////            //for each timestamp get the records, construct object and then add to arraylist of objects and finally return
//////            if(!value.equals(""))                //?????????????????????????????????????????????????????????????????????
//////                f.set(obj,(o));
//////            }
////
////                //f.set(r,f.get(this));
////
//////            String name = f.getName();
//////            String type = f.getGenericType().toString();
//////            String value = "EMPTY";
//////            if(type.equals("int"))
//////                value = (f.getGenericType)(f.get(this).toString());
//////            System.out.println(this.getClass()+" "+name+" "+type+" "+value);
////        }
//////        result.add(obj);
//////        Reflections r1 = (Reflections)obj;
//////        r1.print();
////        //r.print();
////    }
////
////}
