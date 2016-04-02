package HospitalNLP.LiangWorkSpace.src.model;
import java.sql.*; 
public class dbConn
{
	Statement statement;
	public Connection dbConn;
	public dbConn()
	{
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //����JDBC����	  
		String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=InfoFetch";  //���ӷ����������ݿ�sample
		String userName = "sa";  //Ĭ���û���
		String userPwd = "123456";  //����
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
