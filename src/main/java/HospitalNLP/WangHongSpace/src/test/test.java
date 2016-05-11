package test;
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

public class test {

	List<String> content;
	public static void fileLoder(String fileName)
	{
		File file=null;
		BufferedReader reader=null;
		List<String>content=new ArrayList<String>();
		file=new File(fileName);
		dbConn dbconn=new dbConn();
		Statement statement1,statement2,statement3;
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
				statement3 = dbconn.dbConn.createStatement();
				ResultSet rs;
				String sql1="select * from terms";
				rs = statement1.executeQuery(sql1);  //从数据库中读取全部的编码
				int pos=0;
				while(rs.next()){
					//System.out.println("c-----------"+c);
					String terms[]=c.split(rs.getString("term"));
					//System.out.println(rs.getString("term"));
//					String sql2="insert into text values ('"+terms[pos]+"'+)";
					//System.out.println("----------------"+terms[0]);
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
				sql1="insert into text1 values("+t+")";
				statement1.executeUpdate(sql1);
				String name = content.get(0).substring(38,41);
				String createdates[] = content.get(0).split(" ");
				String createdate = createdates[0];
				String case_ids[] = fileName.split("\\\\");
				String case_id = case_ids[2];
				String stage;
				String type;
				String body_codes="";
//				for(int i=2;i<7;i++)
//				{
					if(content.get(6).contains("转移"))
					{
						//String body_codes[] = content.get(i).split("转移 ,，");
						body_codes+=content.get(6).substring(content.get(6).indexOf("转移")-3, content.get(6).indexOf("转移")+3);
						body_codes+=",";
					}
//				}
				try{
					String info = content.get(6).substring(content.get(6).indexOf("（")+1,content.get(6).indexOf("）"));
					if(info.length()>4)
					{
						String[]infos = info.split("[，, ]");
						stage = infos[0];
						type = infos[1];
					}
					else
					{
						stage = "临床诊断";
						type = "无";
					}
					String sql2;
					sql2="insert into Result(NAME,CASE_ID,STAGE,TYPE,BODY_CODE,BODY_SIZE,CREATE_DATE) values('"+name+"','"+case_id+"','"+stage+"','"+type+"','"+body_codes+"','无','"+createdate+"')";
					statement3.executeUpdate(sql2);
				}
				catch(Exception e)
				{
					System.out.println(e);
					stage="无";
					type="无";
					String sql2;
					sql2="insert into Result(NAME,CASE_ID,STAGE,TYPE,BODY_CODE,BODY_SIZE,CREATE_DATE) values('"+name+"','"+case_id+"','"+stage+"','"+type+"','"+body_codes+"','无','"+createdate+"')";
					statement2.executeUpdate(sql2);
				}
			} catch (SQLException e) {
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
		System.out.println("该目录下对象个数："+tempList.length); 
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
		        //System.out.println("文     件："+tempList[i]);
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
