package EmotionClassfy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @param args
 * �ñ�Ҷ˹�㷨����ж�����Ĳ���
 */
public class BayesTest {
	ArrayList<String> stopwords;
	myspliter ms;
	dbConnect db;
	
	public static void main(String[] args) throws Exception {
		BayesTest Test = new BayesTest();
		Test.Ntest();
		//Test.Ptest();
		//Test.AllTest();
	}
	public BayesTest() throws Exception
	{
		stopwords = new ArrayList<>();
		db = new dbConnect();
		ms = new myspliter();
		ms.init();
		String filename ="ͣ�ôʱ�1.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line = in.readLine();
		while(line!=null)
		{
			stopwords.add(line);
			line=in.readLine();
		}
	}
	public void Ntest() throws Exception
	{
		double p1 = 0;
		double n1 = 0;
		double p=1;
		double n=1;
		Statement statement = db.dbConn.createStatement();
		Statement statement1 = db.dbConn.createStatement();
		String sql1 = "select * from Ntest";
		String sql2;
		ResultSet rs = statement.executeQuery(sql1);
		while(rs.next())
		{
			ms.Participle(rs.getString(1));//�ִ�
			String words[] = ms.stringsplit.split(" ");//���ֺõĴ����δ�������
			for(String word : words)
			{
				if(!stopwords.contains(word))
				{
					sql2 = "select * from updatetable where ����='"+word+"'";
					ResultSet rs1 = statement1.executeQuery(sql2);
					if(rs1.next())
					{
						p=p*rs1.getFloat("��������");
						n=n*rs1.getFloat("��������");
					}
				}
			}
			p=p*0.774292;
			n=n*0.225707;
			if(p>n)
			{
				//System.out.println(rs.getString(1)+"\n�ǻ�����"+"����"+p);
				p1+=1;
			}
			else
			{
				//System.out.println(rs.getString(1)+"\n��������"+"����"+n);
				n1+=1;
			}
			p=1;
			n=1;
		}
		System.out.println("�������Ե���ȷ��Ϊ"+n1/(n1+p1));
	}
	public void Ptest() throws Exception
	{
		double p1 = 0;
		double n1 = 0;
		double p=1;
		double n=1;
		Statement statement = db.dbConn.createStatement();
		Statement statement1 = db.dbConn.createStatement();
		String sql1 = "select * from Ptest";
		String sql2;
		ResultSet rs = statement.executeQuery(sql1);
		while(rs.next())
		{
			ms.Participle(rs.getString(1));//�ִ�
			String words[] = ms.stringsplit.split(" ");//���ֺõĴ����δ�������
			for(String word : words)
			{
				if(!stopwords.contains(word))
				{
					sql2 = "select * from updatetable where ����='"+word+"'";
					ResultSet rs1 = statement1.executeQuery(sql2);
					if(rs1.next())
					{
						p=p*rs1.getFloat("��������");
						n=n*rs1.getFloat("��������");
					}
				}
			}
			p=p*0.774292;
			n=n*0.225707;
			if(p>n)
			{
				//System.out.println(rs.getString(1)+"\n�ǻ�����"+"����"+p);
				p1+=1;
			}
			else
			{
				//System.out.println(rs.getString(1)+"\n��������"+"����"+n);
				n1+=1;
			}
			p=1;
			n=1;
		}
		System.out.println("�������Ե���ȷ��Ϊ"+p1/(n1+p1));
	}
	public void AllTest() throws Exception
	{
		double p1 = 0;
		double n1 = 0;
		double p=1;
		double n=1;
		Statement statement = db.dbConn.createStatement();
		Statement statement1 = db.dbConn.createStatement();
		String sql1 = "select * from testing";
		String sql2;
		ResultSet rs = statement.executeQuery(sql1);
		while(rs.next())
		{
			ms.Participle(rs.getString(1));//�ִ�
			String words[] = ms.stringsplit.split(" ");//���ֺõĴ����δ�������
			for(String word : words)
			{
				if(!stopwords.contains(word))
				{
					sql2 = "select * from updatetable where ����='"+word+"'";
					ResultSet rs1 = statement1.executeQuery(sql2);
					if(rs1.next())
					{
						p=p*rs1.getFloat("��������");
						n=n*rs1.getFloat("��������");
					}
				}
			}
			p=p*0.774292;
			n=n*0.225707;
			if(p>n&&rs.getInt("flag")==1)
			{
				//System.out.println(rs.getString(1)+"\n�ǻ�����"+"����"+p);
				p1+=1;
			}
			if(n>p&&rs.getInt("flag")==0)
			{
				//System.out.println(rs.getString(1)+"\n��������"+"����"+n);
				n1+=1;
			}
			p=1;
			n=1;
		}
		System.out.println("����ȷ��Ϊ"+(n1+p1)/65+"������ȷ��"+"�ٻ���"+5/25);
	}
}