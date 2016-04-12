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
 * 用贝叶斯算法做情感分类的训练
 */
public class BayesTrain {
	dbConnect dbconnect;//连接数据库
	myspliter ms;//定义分词包
	ArrayList<String>StopWords;
	ArrayList<String>PositiveDic;
	ArrayList<String>NegativeDic;

	public static void main(String[] args) throws Exception {
		BayesTrain bayesTrain = new BayesTrain();
		bayesTrain.Pretreatment();//预处理
		bayesTrain.Ntrain();//消极数据集训练
		bayesTrain.Ptrain();//积极数据集训练
		bayesTrain.processing();//后期处理
	}
	public BayesTrain() throws Exception
	{
		dbconnect = new dbConnect();
		ms = new myspliter();
		StopWords = new ArrayList<>();
		PositiveDic = new ArrayList<>();
		NegativeDic = new ArrayList<>();
		ms.init();
		String filename = "停用词表1.txt";//将停用词表加载到内存
		String filename1 ="正面情感词语.txt";//将积极的判断词表加载到内存
		String filename2 ="负面情感词语.txt";//将消极的判断词表加载到内存
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
				"drop table updatetable";//由于后面要新建updatetable表，所以要删除
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
						sql2 = "insert into Result(词语,积极个数,消极个数,积极概率,消极概率)values('"+word+"',0,1,0.0,0.0)";
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
						sql2 = "insert into Result(词语,积极个数,消极个数,积极概率,消极概率)values('"+word+"',1,0,0.0,0.0)";
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
		String sql1 = "select 词语,sum(积极个数)as 积极个数,sum(消极个数)as 消极个数,sum(积极概率)as 积极概率,sum(消极概率) as 消极概率  into updatetable from Result group by 词语";
		String sql2 = "update updatetable set 积极个数=积极个数+1,消极个数=消极个数+1";
		String sql3 = "declare @i int " +"\n"+
					  "set @i = (select sum(积极个数) from updatetable)" +"\n"+
					  "update updatetable set 积极概率=积极个数/@i" +"\n"+
					  "declare @j int" +"\n"+
					  "set @j = (select sum(消极个数) from updatetable)" +"\n"+
					  "update updatetable set 消极概率=消极个数/@j";
		Statement s1 = dbconnect.dbConn.createStatement();
		Statement s2 = dbconnect.dbConn.createStatement();
		Statement s3 = dbconnect.dbConn.createStatement();
		s1.executeUpdate(sql1);
		s2.executeUpdate(sql2);
		s3.execute(sql3);
	}
}
