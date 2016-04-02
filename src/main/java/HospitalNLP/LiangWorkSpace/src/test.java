package HospitalNLP.LiangWorkSpace.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dbConn;


public class test {

	List<String> content;
	public static void fileLoder(String fileName)
	{
		File file=null;
		BufferedReader reader=null;
		List<String>content=new ArrayList<String>();
		file=new File(fileName);
		dbConn dbconn=new dbConn();		
		Statement statement1,statement2;
		try
		{
			reader=new BufferedReader(new FileReader(file));
			String line;
			String c="";
			while((line=reader.readLine())!=null)
			{
				c=c+line;				
			}
//			c=c.replace("\"", "\'");;
			try {
				statement1 = dbconn.dbConn.createStatement();
				statement2 = dbconn.dbConn.createStatement();
				ResultSet rs;
				String sql1="select * from terms";
				rs = statement1.executeQuery(sql1);  //�����ݿ��ж�ȡȫ���ı���
				int pos=0;
				while(rs.next()){
					System.out.println("c-----------"+c);
					String terms[]=c.split(rs.getString("term"));
					System.out.println(rs.getString("term"));
//					String sql2="insert into text values ('"+terms[pos]+"'+)";
					System.out.println("----------------"+terms[0]);
					content.add(terms[0]); 
					c=c.replace(terms[0],"");
					c=c.replaceAll("\\'"+rs.getString("term")+"'", "");
//					System.out.println(c);
					pos++;
				}
				content.add(c); 
				pos=0;
				String t="";
				while(pos<content.size()){
					System.out.println("+++++++++++++++"+content.get(pos));
					t+="'"+content.get(pos)+"'";
					t+=",";
					pos++;					
				}				
				t=t.substring(0,t.length()-1);	
				sql1="insert into text values("+t+")";
				
				statement1.executeUpdate(sql1); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e){}
		finally
		{
			try
			{
				if(reader!=null)
				{
					reader.close();
				}
			}
			catch(IOException e){}
		}	
	}
	public static void fileCounter(){
		String path="src/text/";
		File file=new File(path);
		File[] tempList = file.listFiles();
		System.out.println("��Ŀ¼�¶��������"+tempList.length); 
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
		        System.out.println("��     ����"+tempList[i]);
				String s=tempList[i].toString();
				System.out.println(s);
				fileLoder(s);
			}
					   if (tempList[i].isDirectory()) {
		    continue;
		   }
		  }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fileCounter();

	
	}
}
