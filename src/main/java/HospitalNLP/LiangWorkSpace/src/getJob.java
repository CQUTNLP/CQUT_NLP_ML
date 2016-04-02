package HospitalNLP.LiangWorkSpace.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class getJob {
      
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		 final String URL = "jdbc:mysql://localhost/breathing?user=root&password=123456&useUnicode=true&characterEncoding=gbk";
		 Connection conn = null; 
		 PreparedStatement pstmt = null; 
		 PreparedStatement pstmt1 = null; 
		 ResultSet rs = null; 
		 ResultSet rs1 = null; 
		try {
		 Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		 conn = DriverManager.getConnection(URL);
		 pstmt = conn.prepareStatement("select * from text ");
		 pstmt1 = conn.prepareStatement("select * from split ");
		// Statement statement = db.dbConn.createStatement();
		rs = pstmt.executeQuery();//锟斤拷锟斤拷锟�
		rs1 = pstmt1.executeQuery();//锟斤拷锟斤拷锟�
		mywordspliter s = new mywordspliter();
		String[] words = new String[5];
		ArrayList<String> split = new ArrayList<String>();
		while(rs1.next()){
			String str = rs1.getString("job");
			split.add(str);
		}
		
			while(rs.next()){
				int flag =0;
				String str = rs.getString("t0");
				int id = rs.getInt("id");
				s.split(str);
				 words=s.insplit.split("\\锟斤拷");
				 for(int i=0;i<words.length;i++){
		 
					for(int t=0;t<split.size();t++){
						String ss = split.get(t);
							int a = words[i].indexOf(ss);
							if(a>=0){
								String sql = "insert into  info(id,job)"+" values('"+id+"','"+words[i]+"') ";
								PreparedStatement stmt = conn.prepareStatement(sql);
								stmt.executeUpdate();
								System.out.println(words[i]);System.out.println(words[i]);System.out.println(words[i]);
								flag =1;
								break;
							}
						}
					if(flag != 0){
						break;
					}
					}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
