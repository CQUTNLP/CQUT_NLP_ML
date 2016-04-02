package HospitalNLP.LiangWorkSpace.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


	public class getSex {

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
			rs = pstmt.executeQuery();//�����
			mywordspliter s = new mywordspliter();
			String[] words = new String[5];
			ArrayList<String> split = new ArrayList<String>();
			
				while(rs.next()){
					String str = rs.getString("t0");
					int id = rs.getInt("id");
					s.split(str);
					 words=s.insplit.split("\\��");
					 for(int i=0;i<words.length;i++){
						 if(words[i].equals(" �� ") ||words[i].equals(" Ů ")){
						String sql = "insert into  sex(id,sex)"+" values('"+id+"','"+words[i]+"') ";
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.executeUpdate();
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

