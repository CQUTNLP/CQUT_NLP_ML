package Kbqa_Dbqa;

import java.sql.*;

/**
 * Created by tianjingwei on 16/4/1.
 */
public class MysqlJdbc {

    // 驱动程序名
    public String driver = "com.mysql.jdbc.Driver";
    // URL指向要访问的数据库名test
    public  String url = "jdbc:mysql://127.0.0.1:3306/";
    public String database;
    private ResultSet rs=null;
    public ResultSet SetMysqlJdbc(String database,String sql){
        this.database=database;
        url+=database;
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            Connection conn = DriverManager.getConnection(url);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            // 要执行的SQL语句
//            String sql = "select * from test";

            // 结果集
            this.rs = statement.executeQuery(sql);
//            Integer documentid = null;
//            String property=null;
//            String value=null;
//
//            while(rs.next()) {
//
//                // 选择sname这列数据
//                documentid = rs.getInt("documentid");
//                property = rs.getString("property");
//                value = rs.getString("value");
//                // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
//                // 然后使用GB2312字符集解码指定的字节数组
////                name = new String(name.getBytes("ISO-8859-1"),"GB2312");
//                // 输出结果
////                System.out.println(rs.getString("sno") + "\t" + name);
//                System.out.println("documentid:"+documentid);
//                System.out.println("property:"+property);
//                System.out.println("value:"+value);
//            }

//            rs.close();
//            conn.close();

        } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  rs;
    }

    public static void main(String[] args){
//        String
    }

}
