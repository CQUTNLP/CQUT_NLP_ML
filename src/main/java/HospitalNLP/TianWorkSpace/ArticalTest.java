package HospitalNLP.TianWorkSpace;

import HospitalNLP.TianWorkSpace.Unit.dbConn;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianjingwei on 16/4/2.
 */
public class ArticalTest {

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
            try {
                statement1 = dbconn.dbConn.createStatement();
                ResultSet rs;
                String sql1="select * from terms order by id";
                rs = statement1.executeQuery(sql1);  //????????ж??????????
                int pos=0;
                while(rs.next()){
                    String terms[]=c.split(rs.getString("term"));
                    content.add(terms[0]);
                    c=c.replace(terms[0],"");
                    c=c.replaceAll("\\'"+rs.getString("term")+"'", "");
                    pos++;
                }
                content.add(c);
                pos=0;
                String t="";
                while(pos<content.size()){
                    t+="'"+content.get(pos)+"'";
                    t+=",";
                    pos++;
                }
                t=t.substring(0,t.length()-1);  // 去掉最后的逗号
                String col="t0,t1,t2,t3,t4,t5,t6,t7";
                sql1="insert into text ("+col+") values("+t+")";
                System.out.println(sql1);
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
        System.out.println("该目录下对象个数："+tempList.length);
//        System.out.println("文     件："+tempList[1]);
//        String s=tempList[1].toString();
//        fileLoder(s);

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                System.out.println("文     件："+tempList[i]);
                String s=tempList[i].toString();
//                System.out.println(s);
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
