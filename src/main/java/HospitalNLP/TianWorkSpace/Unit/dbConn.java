package HospitalNLP.TianWorkSpace.Unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by tianjingwei on 16/4/1.
 */
public class dbConn {
    Statement statement;
    public Connection dbConn;
    public dbConn()
    {
        String driverName = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://127.0.0.1:3306/test";
        String userName = "sa";
        String userPwd = "123456";
        statement=null;
        try
        {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL);
            statement=dbConn.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
