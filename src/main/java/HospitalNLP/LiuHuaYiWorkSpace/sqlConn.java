import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class sqlConn {
	Statement statement;
	public Connection dbConn;
	public sqlConn()
	{
		String driverName = "com.mysql.jdbc.Driver";  //����JDBC����	  
		String dbURL = "jdbc:mysql://localhost:3306/hospital?characterEncoding=utf8";  //���ӷ����������ݿ�sample
		String userName = "root";  //Ĭ���û���
		String userPwd = "654321";  //����
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
		//�洢�����ݿ�ȡ��������
		int length;
		String[] inDiagnose = null;
		try {
			st1=dbConn.createStatement();
			sql="select* from info";
			rs=st1.executeQuery(sql);
			//��ȡ���ݳ���
			rs.last();
			length=rs.getRow();
			//�ص�ͷ��
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
