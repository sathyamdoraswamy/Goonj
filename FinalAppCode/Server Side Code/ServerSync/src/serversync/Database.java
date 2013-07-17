///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package serversync;
//
//import java.sql.Statement;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author sathyam
// */
//public class Database {
//    static Connection con;
//    public static void main(String args[])throws IOException
//    {
//        getConnection();
//        try {
//            executeQuery("insert into serverdb values(1,'s','d','sathyam','man',1,'Y',2);");
//            //executeAndTest("select * from serverdb;");
//        } catch (Exception ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        closeConnection();
//    }
//    public static void getConnection() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            con = DriverManager.getConnection("jdbc:sqlite:/home/mridu/frameworkdb");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//     public static void executeQuery(String query) throws Exception {
//        if (con != null) {
//            Statement st1 = con.createStatement();
//            st1.executeUpdate(query);
//        }
//    }
//
//    public static String executeAndTest(String query) {
//        String result = "null";
//        try {
//            if (con != null) {
//                System.out.println("Inside!!!");
//                PreparedStatement st1 = con.prepareStatement(query);
//                ResultSet rs1 = st1.executeQuery();
//                ResultSetMetaData rsmd = rs1.getMetaData();
//                int col_cnt = rsmd.getColumnCount();
//                String value = "";
//                while (rs1.next()) {
//                    for (int ik = 1; ik <= col_cnt; ik++) {
//                        value = rs1.getString(ik);
//                        System.out.print(value + " ");
//                    }
//                    result = value;
//                    System.out.println();
//                }
//            }
//        } catch (Exception e) {
//        }
//        return result;
//    }
//    public static void closeConnection() {
//        try {
//            con.close();
//        } catch (Exception e) {
//        }
//    }
//}
