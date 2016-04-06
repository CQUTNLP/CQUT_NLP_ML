package HospitalNLP.TianWorkSpace.Filter;

import HospitalNLP.TianWorkSpace.Utils.MysqlJdbc;
import HospitalNLP.TianWorkSpace.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianjingwei on 16/4/3.
 */
public class IsLungCannerFilter {
    private String sql;
    private List<Patient> patients;
    public IsLungCannerFilter(){
        setSql("select id,t3 from text ");
        ResultSet rs =new MysqlJdbc().SetMysqlJdbc("test",getSql());
        this.patients = this.Rs2List(rs);
//        this.show();
    }
    public void show(){
        for(Patient patient:patients){
            System.out.println("patient:"+patient.getId()+"  IsLungCanner:"+patient.getIsLungCanner());
        }
    }
    private List Rs2List(ResultSet rs){
        List<Patient> ppatients = new ArrayList<Patient>();
        try {
            while(rs.next()){
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setIsLungCanner(IsTrue(rs.getString("t3")));
                ppatients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("rs¡¨Ω”≥ˆ¥Ì");
            e.printStackTrace();
        }
        return ppatients;
    }
    public String IsTrue(String sentence){
        String result="flase";
        sentence=sentence.replaceAll("\\u3001","."); // \\u3001  «∂Ÿ∫≈
//        System.out.println(sentence);
//        System.out.println(sentence.split("\\d\\.\\d?")[1]);
        String fisrt_sentences=sentence.split("\\d\\.\\d?")[1];
        String rule1Pattern=".*∑Œ.*∞©.*";
        String rule2Pattern= "£ø|//?";
        String rule3Pattern= "¡Ÿ¥≤’Ô∂œ";
        Pattern p  =  Pattern.compile(rule1Pattern);
        Matcher m = p.matcher(fisrt_sentences);
        if (m.find()){
            result="true";
//            result=fisrt_sentences.contains("£ø")?"maybe":"true";
            if(Pattern.compile(rule2Pattern).matcher(fisrt_sentences).find()){
                result="maybe";
            }
            if(Pattern.compile(rule3Pattern).matcher(fisrt_sentences).find()){
                result="maybe";
            }
        }
        return result;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }


    public void setSql(String sql) {
        this.sql = sql;
    }


    public String getSql() {
        return sql;
    }

    public static void main(String[] args) {
        IsLungCannerFilter isLungCannerFilter = new IsLungCannerFilter();
        isLungCannerFilter.show();
    }

}

