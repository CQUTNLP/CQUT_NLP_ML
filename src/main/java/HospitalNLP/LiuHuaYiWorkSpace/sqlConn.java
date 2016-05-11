import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class sqlConn {
	Statement statement;
	public Connection dbConn;
	public sqlConn()
	{
		String driverName = "com.mysql.jdbc.Driver";  //加载JDBC驱动	  
		String dbURL = "jdbc:mysql://localhost:3306/hospital?characterEncoding=utf8";  //连接服务器和数据库sample
		String userName = "root";  //默认用户名
		String userPwd = "654321";  //密码
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
	public String[] getData(String field){
		String sql=null;
		Statement st1 = null;
		ResultSet rs = null;
		//存储从数据库取出的数据
		int length;
		String[] inDiagnose = null;
		try {
			st1=dbConn.createStatement();
			sql="select* from info";
			rs=st1.executeQuery(sql);
			//获取数据长度
			rs.last();
			length=rs.getRow();
			//回到头部
			rs.beforeFirst();
			inDiagnose=new String[length];
			int i=0;
			while(rs.next()){
				inDiagnose[i]=rs.getString(field);
				i++;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			return inDiagnose;
		}
	}
}
