package EmotionClassfy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * @param args
 * �ñ�Ҷ˹�㷨����з����ѵ��
 */
public class BayesTrain {
	dbConnect dbconnect;//�������ݿ�
	myspliter ms;//����ִʰ�
	ArrayList<String>StopWords;
	ArrayList<String>PositiveDic;
	ArrayList<String>NegativeDic;

	public static void main(String[] args) throws Exception {
		BayesTrain bayesTrain = new BayesTrain();
		bayesTrain.Pretreatment();//Ԥ����
		bayesTrain.Ntrain();//�������ݼ�ѵ��
		bayesTrain.Ptrain();//�������ݼ�ѵ��
		bayesTrain.processing();//���ڴ���
	}
	public BayesTrain() throws Exception
	{
		dbconnect = new dbConnect();
		ms = new myspliter();
		StopWords = new ArrayList<>();
		PositiveDic = new ArrayList<>();
		NegativeDic = new ArrayList<>();
		ms.init();
		String filename = "ͣ�ôʱ�1.txt";//��ͣ�ôʱ���ص��ڴ�
		String filename1 ="������д���.txt";//���������жϴʱ���ص��ڴ�
		String filename2 ="������д���.txt";//���������жϴʱ���ص��ڴ�
		BufferedReader in1 = new BufferedReader(new FileReader(new File(filename)));
		BufferedReader in2 = new BufferedReader(new FileReader(new File(filename1)));
		BufferedReader in3 = new BufferedReader(new FileReader(new File(filename2)));
		String line = in1.readLine();
		while(line!=null)
		{
			StopWords.add(line);
			line = in1.readLine();
		}
		line = in2.readLine();
		while(line!=null)
		{
			PositiveDic.add(line);
			line = in2.readLine();
		}
		line = in3.readLine();
		while(line!=null)
		{
			NegativeDic.add(line);
			line = in3.readLine();
		}
	}
	public void Pretreatment() throws Exception
	{
		String sql1 = "if   object_id('updatetable') is not null" +"\n"+
				"drop table updatetable";//���ں���Ҫ�½�updatetable������Ҫɾ��
		String sql2 = "delete from Result";
		Statement s1 = dbconnect.dbConn.createStatement();
		Statement s2 = dbconnect.dbConn.createStatement();
		s1.executeUpdate(sql1);
		s2.execute(sql2);
	}
	public void Ntrain() throws Exception
	{
		String sql1 = "select * from nTrain";
		String sql2 = null;
		Statement s1 = dbconnect.dbConn.createStatement();
		Statement s2 = dbconnect.dbConn.createStatement();
		ResultSet rs = s1.executeQuery(sql1);
		while(rs.next())
		{
			ms.Participle(rs.getString(1));
			String words[] = ms.stringsplit.split(" ");
			for(String word : words)
			{
				if(!StopWords.contains(word))
				{
					if(NegativeDic.contains(word+" "))
					{
						sql2 = "insert into Result(����,��������,��������,��������,��������)values('"+word+"',0,1,0.0,0.0)";
						s2.executeUpdate(sql2);
					}
				}
			}
		}
		s1.close();
		s2.close();
	}
	public void Ptrain() throws Exception
	{
		String sql1 = "select top 600 * from pTrain";
		String sql2 = null;
		Statement s1 = dbconnect.dbConn.createStatement();
		Statement s2 = dbconnect.dbConn.createStatement();
		ResultSet rs = s1.executeQuery(sql1);
		while(rs.next())
		{
			ms.Participle(rs.getString(1));
			String words[] = ms.stringsplit.split(" ");
			for(String word : words)
			{
				if(!StopWords.contains(word))
				{
					if(PositiveDic.contains(word+" "))
					{
						sql2 = "insert into Result(����,��������,��������,��������,��������)values('"+word+"',1,0,0.0,0.0)";
						s2.executeUpdate(sql2);
					}
				}
			}
		}
		s1.close();
		s2.close();
	}
	public void processing() throws Exception
	{
		String sql1 = "select ����,sum(��������)as ��������,sum(��������)as ��������,sum(��������)as ��������,sum(��������) as ��������  into updatetable from Result group by ����";
		String sql2 = "update updatetable set ��������=��������+1,��������=��������+1";
		String sql3 = "declare @i int " +"\n"+
					  "set @i = (select sum(��������) from updatetable)" +"\n"+
					  "update updatetable set ��������=��������/@i" +"\n"+
					  "declare @j int" +"\n"+
					  "set @j = (select sum(��������) from updatetable)" +"\n"+
					  "update updatetable set ��������=��������/@j";
		Statement s1 = dbconnect.dbConn.createStatement();
		Statement s2 = dbconnect.dbConn.createStatement();
		Statement s3 = dbconnect.dbConn.createStatement();
		s1.executeUpdate(sql1);
		s2.executeUpdate(sql2);
		s3.execute(sql3);
	}
}
