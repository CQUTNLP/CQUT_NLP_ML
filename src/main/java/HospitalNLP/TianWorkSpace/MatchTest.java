package HospitalNLP.TianWorkSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianjingwei on 16/4/2.
 */
public class MatchTest {

    public void ContainTest(){
        String str="Hello World";  //�??��????�?�?�?
        String prexStr="ll";
        String reg=".*ll.*";  //?��??�?�?串中?????????��??�?�?�?ll
        System.out.println(str.matches(reg));
        System.out.println(str.contains(prexStr));
    }
    public void ttt(){
        String Ps= ".*��.*��.*";
        String PPS= "���ʷ����Ķ�";
        Pattern p  =  Pattern.compile(Ps);
        String s="1.���·��۰���T4N2M0����b�ڣ�2���෢ǻ϶���Թ��� 3����ή��  ";
        Matcher m = p.matcher(s);
        System.out.println(s.replace("��","."));
//        if(m.find()){
//        System.out.println(m.find());

//        System.out.println(c);
//        }
//        String[] c=s.split("\\d\\.\\d?");
//        System.out.println("11:"+PPS);
//        for(String z:c){
//            System.out.println(z);
//        }

    }
    public void xxx(){
        String Ps= ".*ȷ��.*(\\d{1,2})(��|��|��|��).*(\\d{4}-\\d{2}-\\d{2}).*";
        String Ps2= ".*(ȷ��|��������).*(\\d{1,2})(��|��|��|��|��).*(\\d{4}-\\d{2}-\\d{2}).*";
        Pattern p  =  Pattern.compile(Ps2);
        String s=" 2015-05-29��09:46          �� Ժ �� ¼   �����ﾮ�����У�53�꣬רҵ������Ա����ȷ�����·��ٰ�23�¡���2015-05-27��Ժ��                                    ";
        Matcher m = p.matcher(s);
        Boolean b=m.find();
        System.out.println(b);
        if(b){
            for (int i=0;i<=m.groupCount();i++){
                System.out.println("group"+i+":"+m.group(i));
            }
        }
    }
    public void date(){
        String str = "2013-07-18";
        Integer date=9;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date myDate = formatter.parse(str);
            Calendar c = Calendar.getInstance();
            c.setTime(myDate);
            c.add(Calendar.MONTH, -date);
            myDate = c.getTime();
            System.out.println(formatter.format(myDate));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void zzz(){
        String Ps= ".*(\\d{4}-\\d{2}-\\d{2}).*";
        Pattern p  =  Pattern.compile(Ps);
        String str=" ���ƾ�������Ժ��������ؼ�飬����ʾ��B��������19.00pg/mL����ϸ��6.41��10^9/L����" +
                "����ϸ���ٷ���64.1%��C-��Ӧ����8.0mg/L��������ԭ0.05ng/ml�����߿�ԭ147.03ng/ml����" +
                "�࿹ԭ242 395.60U/ml�����࿹ԭ125 1422.67U/ml���ز�CT��ʾ��1. �ҷ���Ҷǰ���׶�ռλ" +
                "�Բ��䣬������Χ�ͷΰ������Դ󣬽�����֧����һ����顣2. �ҷμ������Ҷ�쳣�ܶ�Ӱ������ת��" +
                "��������3. �ҷ�ɢ����֢��4. �ݸ���˫�����ܰͽ��״�5. �İ�������Һ��6. ��6׵���쳣�ܶ�Ӱ" +
                "��ת�����������ܳ��⡣7. ������֬���Ρ���֧�������ʾ�����¶ζ෢��ڣ������֧����ȡ�����ʾ" +
                "Ϊ���ٰ�������������ָ�����ΰ���������ʾEGFR��ALKͻ�����ԡ����뻼�߼�����ͨ����2015-11-29��" +
                "������������+�δﲬ��ȫ���ƣ�ͬʱ�������������޸����ʡ����ƺ󸴲�Ѫ���桢������δ�������쳣����" +
                "��ʾ�ϼ�ҽ��ͬ������԰����Ժ��                                 ";
        String[] splitStr=str.split("��ʾ.*��");
        for (String s:splitStr){
            Matcher m = p.matcher(s);
            Boolean b=m.find();
            if(b){
                System.out.println(m.group(1));
                break;
            }
        }
    }
    public void Patterntest(){
//        Pattern p1  =  Pattern.compile( "(.*)ll(.*)" );
        Pattern p2  =  Pattern.compile( "(.*)-(.*)-(.*)-(.*)" );
        String s  =   " 123aa-34345bb-234cc-00 " ;
//        String s = "Hello World";
        Matcher m  =  p2.matcher(s);
//        while (m.find())
//        {
//            System.out.println( " m.group(): " + m.group());  // ???��????
//
//            System.out.println( " m.group(1): " + m.group( 1 ));  // ???��?��????
//
//            System.out.println( " m.group(2): " + m.group( 2 ));  // ???��??�???
//            System.out.println();
//
//        }
        if(m.find()){
            for(int i=0;i<m.groupCount();i++){
                System.out.println(m.group(i).trim());
            }
        }

    }
    public static void main(String[] args) {
        MatchTest matchTest = new MatchTest();
        matchTest.xxx();
    }
}
