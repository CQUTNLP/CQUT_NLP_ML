package HospitalNLP.TianWorkSpace.Filter;

import HospitalNLP.TianWorkSpace.Utils.MysqlJdbc;
import HospitalNLP.TianWorkSpace.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianjingwei on 16/4/3.
 *
 * 确诊日期过滤器
 *
 */
public class QiagnoseDateFilter {
    private String sql;
    private List<Patient> patients;
    public QiagnoseDateFilter(){
        setSql("select * from text  ");
        ResultSet rs =new MysqlJdbc().SetMysqlJdbc("test",getSql());
        this.patients = this.QiagnoseDate(rs);
    }

    public void show(){
        for(Patient patient:patients){
            System.out.println("patient:"+patient.getId()+"  IsLungCanner:"+patient.getIsLungCanner()+" CannerDate:"+patient.getCannerDate());
        }
    }
    public List<Patient> QiagnoseDate(ResultSet rs){
        List<Patient> patientss = new ArrayList<Patient>() ;
        try {
            while (rs.next()){
                Patient patient = new Patient();
                String date=null;
                Integer id=rs.getInt("id");
                String t0=rs.getString("t0"); //出院记录
                String t3=rs.getString("t3"); //入院诊断
                String t4=rs.getString("t4"); //诊断经过
                String t6=rs.getString("t6"); //出院诊断

                Integer whichNum=this.MakeSureCanner(t3,t6);
                System.out.print("whichNum:"+whichNum+":");
                switch (whichNum){
                    case 1: date = ParseQiagnoseDate(t0);break;
                    case 2: date = ParseQiagnoseDate2(t4);break;
                    default: ;
                }
                patient.setId(id);
                patient.setCannerDate(date);
                patient.setIsLungCanner(whichNum > 0 ? "true" : "false");
                patientss.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("rs出错");
            e.printStackTrace();
        }
        return patientss;
    }

    public  Integer MakeSureCanner(String t3,String t6){
        IsLungCannerFilter isLungCannerFilter=new IsLungCannerFilter();
        Boolean bollean1 = isLungCannerFilter.IsTrue(t3).equals("true");
        Boolean bollean2 = isLungCannerFilter.IsTrue(t6).equals("true");
        if(bollean1==Boolean.TRUE && bollean2==Boolean.TRUE){
            return 1;
        }else if(bollean2==Boolean.TRUE){
            return 2;
        }
        return 0;
    }

    public String ParseQiagnoseDate2(String sentence){
        String Ps= ".*(\\d{4}-\\d{2}-\\d{2}).*";
        Pattern p  =  Pattern.compile(Ps);
        String[] splitStr=sentence.split("结果.*提示.*癌");
        for (String s:splitStr){
            Matcher m = p.matcher(s);
            Boolean b=m.find();
            if(b){
                return m.group(1);
//                System.out.println(m.group(1));
            }
        }
        try {
            throw new Exception("ParseQiagnoseDate2方法找不到确诊日期");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String ParseQiagnoseDate(String sentence){
        String finalDate=null;
//      TODO:变量Ps需要导入'确诊'相关词典
        String Ps= ".*(确诊|靶向治疗).*(\\d{1,2})(月|日|年|周|天).*(\\d{4}-\\d{2}-\\d{2}).*";
        Pattern p  =  Pattern.compile(Ps);
//        String s="2015-08-10，17:35          出 院 记 录   患者康中模，男，63岁，其他劳动者，因“反复咳嗽、痰血5月，确诊肺癌1月余”于2015-08-04入院。                                         ";
        Matcher m = p.matcher(sentence);
        Boolean b=m.find();
//        System.out.println(b + ":" + sentence);
        if(b){
//            for (int i=0;i<=m.groupCount();i++){
//                System.out.println("group"+i+":"+m.group(i));
//            }
            finalDate=DateDiff(Integer.valueOf(m.group(2)),m.group(4),m.group(3));
        }
        if(finalDate==null){
            String Ps2= ".*岁.*因.*后.*(\\d{1,2})(月|日|年|周|天).*(\\d{4}-\\d{2}-\\d{2}).*";
            Pattern p2  =  Pattern.compile(Ps2);
//        String s="2015-08-10，17:35          出 院 记 录   患者康中模，男，63岁，其他劳动者，因“反复咳嗽、痰血5月，确诊肺癌1月余”于2015-08-04入院。                                         ";
            Matcher m2 = p2.matcher(sentence);
            Boolean b2=m2.find();
            if(b2){
                finalDate=DateDiff(Integer.valueOf(m2.group(1)),m2.group(3),m2.group(2));
            }
        }
        return finalDate;
    }
    public String DateDiff(Integer num,String date,String type){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("日期解析异常");
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(myDate);
        if (type.equals("年")) {
            c.add(Calendar.YEAR, -num);
        }else if(type.equals("月")){
            c.add(Calendar.MONTH, -num);
        }else if(type.equals("日")||type.equals("天")){
            c.add(Calendar.DATE,-num);
        }else if(type.equals("周")){
            c.add(Calendar.DATE,-(num*7));
        }else {
            try {
                throw new Exception("日期type类型不是:年月日天周");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        myDate = c.getTime();
        System.out.println(formatter.format(myDate));
        return formatter.format(myDate);
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public String getSql() {
        return sql;
    }


    public static void main(String[] args) {
        QiagnoseDateFilter qiagnoseDateFilter = new QiagnoseDateFilter();
        qiagnoseDateFilter.show();
    }
}
