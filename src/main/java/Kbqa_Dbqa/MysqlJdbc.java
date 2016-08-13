package Kbqa_Dbqa;

import java.io.*;
import java.sql.*;

/**
 * Created by tianjingwei on 16/4/1.
 */
public class MysqlJdbc {

    // 驱�?��?�?�??
    public String driver = "com.mysql.jdbc.Driver";
    // URL????�?访�?????��??�???test
    public  String url = "jdbc:mysql://127.0.0.1:3306/";
    public String database;
    private ResultSet rs=null;
    private Statement statement=null;
    private Connection conn=null;
    MysqlJdbc(){
    }
    MysqlJdbc(String database){
        init(database);
    }
    public void clearTable(String table ){
        try {
            statement.executeUpdate("DELETE FROM "+table+";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void  init(String database){
        this.database=database;
        url+=database;
        // ??载驱?��?�?
        try {
            Class.forName(driver);
            // �?�??��??�??
            conn = DriverManager.getConnection(url);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // statement?��?��?��?SQL�???
            statement = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Integer runSql(String sql){
        int flag=1;
        try
        {
//            sql= new String(sql.getBytes(),"GBK");
            flag = statement.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println("sql:"+sql);
            e.printStackTrace();
        }
        return  flag;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String database="test";
        MysqlJdbc mysqlJdbc = new MysqlJdbc(database);
        Integer num=1;
        String fileName="/Users/tianjingwei/Documents/cqut/kbqa-dbqa/NLPCC2016QA/knowledge/nlpcc-iccpol-2016.kbqa.kb.mention2id";
        String fileName2="/Users/tianjingwei/Documents/cqut/kbqa-dbqa/NLPCC2016QA/knowledge/output.txt";
        File file=new File(fileName);
        File fileWriter= new File (fileName2);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "utf-8"));
//            BufferedWriter writer = new BufferedWriter(new FileWriter(fileWriter));
            String line;
            String sql=null;
            int badLine=0;
            Long startTime = System.currentTimeMillis();
            mysqlJdbc.clearTable("kbqa_dbqa");
            while((line=reader.readLine())!=null)
            {
//                line = new String(line.getBytes("utf-8"),"GBK");
                line=line.replace("'","\"").replace("\\","");
                String[] line2word=line.split("\\|\\|\\|");
                if(line2word.length<=1||line2word[1].length()>5000){
                    badLine++;
//                    System.out.println("跳过句子"+line);
                    continue;
                }
                try {

                    sql="insert into kbqa_dbqa (col1,col2) values ('" +
                            line2word[0].trim()+"','"+
                            line2word[1].trim()+
                            "');";
                }catch (ArrayIndexOutOfBoundsException e){
                    badLine++;
                    System.out.println("错误的sql:"+sql);
                }
//                System.out.println(line);
//                System.out.println(sql);
                mysqlJdbc.runSql(sql);
//                writer.write(sql);
                if(num%100000==0){
                    Long NowTime = System.currentTimeMillis();
                    System.out.println("已插入"+num+"用时"+(NowTime-startTime)/1000.0+"秒");
                }
                num++;
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("已完成\n总用时" + (startTime - endTime) / 1000.0 + "秒");
            System.out.println("成功插入" + num + "行");
            System.out.println("BadLine" + badLine + "行");
            reader.close();
//            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
