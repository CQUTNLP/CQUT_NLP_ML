package HospitalNLP.LiangWorkSpace.src;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SplitWord {

	public static void main(String[] args ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		 final String URL = "jdbc:mysql://localhost/breathing?user=root&password=123456&useUnicode=true&characterEncoding=gbk";
		 Connection conn = null; 
		 PreparedStatement pstmt = null; 
		 PreparedStatement pstmt1 = null; 
		 ResultSet rs = null; 
		try {
		 Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		 conn = DriverManager.getConnection(URL);
		 pstmt = conn.prepareStatement("select t0 from text ");
		// Statement statement = db.dbConn.createStatement();
		rs = pstmt.executeQuery();//结果集
		mywordspliter s = new mywordspliter();
		while(rs.next()){

			String str = rs.getString("t0");
			s.split(str);
			String[] words=s.insplit.split("\\，");
			
			mywordspliter s1 = new mywordspliter();
			s1.split(words[4]);
			String[] ss=s1.insplit.split("\\ ");
			int i=0;
	//  建立split表
			while(i<ss.length){
				System.out.println("..........."+ss[i]);
				pstmt1=conn.prepareStatement("insert into splitJob(job) values (?)");
				String sid=ss[i];
				pstmt1.setString(1, sid);
				 pstmt1.executeUpdate();
				i++;
			}
		}
	}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


