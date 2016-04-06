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
        String str="Hello World";  //寰??ゆ????瀛?绗?涓?
        String prexStr="ll";
        String reg=".*ll.*";  //?ゆ??瀛?绗?涓蹭腑?????????瑰??瀛?绗?涓?ll
        System.out.println(str.matches(reg));
        System.out.println(str.contains(prexStr));
    }
    public void ttt(){
        String Ps= ".*肺.*癌.*";
        String PPS= "请问肺在哪儿";
        Pattern p  =  Pattern.compile(Ps);
        String s="1.左下肺鳞癌（T4N2M0，Ⅲb期）2、多发腔隙性脑梗塞 3、脑萎缩  ";
        Matcher m = p.matcher(s);
        System.out.println(s.replace("、","."));
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
        String Ps= ".*确诊.*(\\d{1,2})(月|日|年|周).*(\\d{4}-\\d{2}-\\d{2}).*";
        String Ps2= ".*(确诊|靶向治疗).*(\\d{1,2})(月|日|年|周|天).*(\\d{4}-\\d{2}-\\d{2}).*";
        Pattern p  =  Pattern.compile(Ps2);
        String s=" 2015-05-29，09:46          出 院 记 录   患者田井超，男，53岁，专业技术人员，因“确诊右下肺腺癌23月”于2015-05-27入院。                                    ";
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
        String str=" 诊疗经过：入院后完善相关检查，辅查示：B型钠尿肽19.00pg/mL、白细胞6.41×10^9/L、中" +
                "性粒细胞百分数64.1%、C-反应蛋白8.0mg/L、降钙素原0.05ng/ml、癌胚抗原147.03ng/ml、糖" +
                "类抗原242 395.60U/ml、糖类抗原125 1422.67U/ml。胸部CT提示：1. 右肺下叶前基底段占位" +
                "性病变，考虑周围型肺癌可能性大，建议纤支镜进一步检查。2. 右肺及左肺上叶异常密度影，考虑转移" +
                "性肿瘤。3. 右肺散在炎症。4. 纵隔及双肺门淋巴结肿大。5. 心包少量积液。6. 胸6椎体异常密度影" +
                "，转移性肿瘤不能除外。7. 弥漫性脂肪肝。纤支镜检查提示气管下段多发结节，于左侧支气管取活检提示" +
                "为肺腺癌，患者无手术指征，肺癌基因检查提示EGFR、ALK突变阴性。经与患者家属沟通后于2015-11-29日" +
                "采用培美曲塞+奈达铂行全身化疗，同时采用唑来磷酸修复骨质。化疗后复查血常规、肝肾功未见明显异常，经" +
                "请示上级医生同意后，予以办理出院。                                 ";
        String[] splitStr=str.split("提示.*癌");
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
//            System.out.println( " m.group(): " + m.group());  // ???版????
//
//            System.out.println( " m.group(1): " + m.group( 1 ));  // ???版?板????
//
//            System.out.println( " m.group(2): " + m.group( 2 ));  // ???板??姣???
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
