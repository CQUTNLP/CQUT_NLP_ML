package HospitalNLP.TianWorkSpace.Unit;

import java.sql.*;

/**
 * Created by tianjingwei on 16/4/1.
 */
public class MysqlJdbcTest {


    public static void main(String[] args){

        // 驱动程序名

        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名test
        String url = "jdbc:mysql://127.0.0.1:3306/test";

        // MySQL配置时的用户名
//        String user = "root";

        // MySQL配置时的密码
//        String password = "root";

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
            String sql = "select * from test";

            // 结果集
            ResultSet rs = statement.executeQuery(sql);

//            System.out.println("-----------------");
//            System.out.println("执行结果如下所示:");
//            System.out.println("-----------------");
//            System.out.println(" 学号" + "\t" + " 姓名");
//            System.out.println("-----------------");

            Integer documentid = null;
            String property=null;
            String value=null;

            while(rs.next()) {

                // 选择sname这列数据
                documentid = rs.getInt("documentid");
                property = rs.getString("property");
                value = rs.getString("value");
                // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                // 然后使用GB2312字符集解码指定的字节数组
//                name = new String(name.getBytes("ISO-8859-1"),"GB2312");

                // 输出结果
//                System.out.println(rs.getString("sno") + "\t" + name);
                System.out.println("documentid:"+documentid);
                System.out.println("property:"+property);
                System.out.println("value:"+value);
            }

            rs.close();
            conn.close();

        } catch(ClassNotFoundException e) {


            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();


        } catch(SQLException e) {


            e.printStackTrace();


        } catch(Exception e) {


            e.printStackTrace();


        }
    }

}
