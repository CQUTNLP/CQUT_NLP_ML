package EmotionClassfy;
import java.sql.*; 
public class dbConnect
{
	Statement statement;
	public Connection dbConn;
	public dbConnect()
	{
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //����jdbc���� 
		String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=emotionclassfy";  //�������ݿ������
		String userName = "sa";  //Sequel�û���
		String userPwd = "12345wh";  //Sequel����
		try
		{
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("connect sql server successful");
		}
		catch (Exception e) 
		{
			System.out.println("connect sql server fail");
			e.printStackTrace();
		}
		/*finally
		{
			try
			{
				statement.close();
				dbConn.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}*/
	}	

}
