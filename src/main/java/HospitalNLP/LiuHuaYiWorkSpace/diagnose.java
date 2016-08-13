import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
public class diagnose {
	//�ж��Ƿ�ȷ��
	public void dognoseOrNot(){
		//����
		sqlConn conn=new sqlConn();
		String outDiagnose[]=null;
		String sql=null;
		Statement st1 = null;
		try {
			st1 = conn.dbConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outDiagnose=conn.getData("outDiagnose");
		for(int i=0;i<outDiagnose.length;i++){
			//System.out.println(inDiagnose[i]);
			java.util.regex.Pattern p=java.util.regex.Pattern.compile("��.{0,8}��");
			Matcher matcher=p.matcher(outDiagnose[i]);
			if(matcher.find()){
				//System.out.println(matcher.group(0).trim());
				sql="update result set diagnoseOrNot='yes' where id="+(i+1);
			}else{
				sql="update result set diagnoseOrNot='no' where id="+(i+1);
			}
//			if (outDiagnose[i].contains("��")&&outDiagnose[i].contains("��")){
//				sql="update result set diagnoseOrNot='yes' where id="+(i+1);	
//			}else{
//				sql="update result set diagnoseOrNot='no' where id="+(i+1);
//			}
			try {
				st1.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//�������ͱ���
	public void diagnoseType(){
		sqlConn conn=new sqlConn();
		String outDiagnose[]=null;
		String sql=null;
		Statement st1 = null;
		TestMain tm=new TestMain();
		try {
			st1 = conn.dbConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outDiagnose=conn.getData("outDiagnose");
		for(int i=0;i<outDiagnose.length;i++){
			String[] result=tm.separate(outDiagnose[i]);
			String illness="";
			for(int j=0;j<result.length;j++)
			{	
				//System.out.println(result[j]);
				if(result[j].contains("��")){
					if(result[j].length()>1){
						illness+=result[j];
						break;
					}else if(result[j-1].contains("��")||result[j-1].contains("��")){
						illness+=result[j-1]+result[j];
						break;
					}
				}
			}
			sql="update result set illnessType='"+illness+"' where id="+(i+1);
			if(illness.length()>0)
				try {
					st1.execute(sql);
//					System.out.println(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	//ȷ��ʱ��
	public void diagnoseDate(){

		sqlConn conn=new sqlConn();
		String outDiagnose[]=null;
		String sql=null;
		Statement st1 = null;
		//��¼��Ժ��ȷ��ʱ��
		int tempYear=0;
		int tempMonth=0;
		int year=0;
		int month=0;
		TestMain tm=new TestMain();
		try {
			st1 = conn.dbConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outDiagnose=conn.getData("patientInfo");
		for(int i=0;i<outDiagnose.length;i++){
			String[] result=tm.separate(outDiagnose[i]);
			tempMonth=0;
			tempYear=0;
			for(int j=0;j<result.length;j++)
			{	
				
				if(result[j].equals("��Ժ")){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						year=sdf.parse(result[j-1]).getYear();
						month=sdf.parse(result[j-1]).getMonth();
						year+=1900;
						month+=1;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(result[j].contains("��")){
					String m="";
					char[] c=result[j].toCharArray();
					for(int x=0;x<c.length-1;x++){
						m+=c[x];
					}
					if(m.length()>0)
						if(tempMonth<Integer.parseInt(m))
							tempMonth=Integer.parseInt(m);
				}
				if(result[j].contains("��")){
					String y="";
					char[] c=result[j].toCharArray();
					for(int x=0;x<c.length-1;x++){
						y+=c[x];
					}
					if(y.length()>0)
						if(tempYear<Integer.parseInt(y))
							tempYear=Integer.parseInt(y);
				}
			}
			month-=tempMonth;
			while(month<=0){
				year-=1;
				month+=12;
			}
			year-=tempYear;
			//System.out.println(year+"-"+month);
			sql="update result set diagnoseDate='"+year+"-"+month+"' where id="+(i+1);
			try {
				st1.execute(sql);
				//System.out.println(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	//ȷ�﷽ʽ
	public void diagnoseWay(){
		sqlConn conn=new sqlConn();
		String diagnoseProcess[]=null;
		String sql=null;
		Statement st1 = null;
		try {
			st1 = conn.dbConn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		diagnoseProcess=conn.getData("diagnoseProcess");
		//��ȡ����ȷ�﷽ʽ
		List<String> lines=new ArrayList<String>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("ȷ�﷽ʽ.txt"),"UTF-8"));
			String line=null;
			while((line=br.readLine()) != null){
				lines.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�ж�ȷ�﷽ʽ
		for (int i = 0; i < diagnoseProcess.length; i++) {
			//System.out.println(diagnoseProcess[i]);
			//System.out.println("i="+i);
			String[] temp=diagnoseProcess[i].split("��");
			for (int j = 0; j < temp.length; j++) {
				for(String x:lines){
					if(temp[j].contains(x))
						//if(temp[j].contains("��") || !temp[j].contains("δ��"))
						//	System.out.println(temp[j]);
						System.out.println(x);
				}
			}
			System.out.println();
		}
	}
}
