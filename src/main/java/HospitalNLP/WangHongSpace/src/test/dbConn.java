package test;
import java.sql.*; 
public class dbConn
{
	Statement statement;
	public Connection dbConn;
	public dbConn()
	{
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动	  
		String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=InfoFetch";  //连接服务器和数据库sample
		String userName = "sa";  //默认用户名
		String userPwd = "12345wh";  //密码
		statement=null;
		try
		{
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			statement=dbConn.createStatement();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}	
}
