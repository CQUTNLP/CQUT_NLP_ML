package HospitalNLP.TianWorkSpace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianjingwei on 16/4/2.
 */
public class MatchTest {

    public void ContainTest(){
        String str="Hello World";  //待判断的字符串
        String prexStr="ll";
        String reg=".*ll.*";  //判断字符串中是否含有特定字符串ll
        System.out.println(str.matches(reg));
        System.out.println(str.contains(prexStr));
    }

    public void Patterntest(){
//        Pattern p1  =  Pattern.compile( "(.*)ll(.*)" );
        Pattern p2  =  Pattern.compile( "(.*)-(.*)-(.*)-(.*)" );
        String s  =   " 123aa-34345bb-234cc-00 " ;
//        String s = "Hello World";
        Matcher m  =  p2.matcher(s);
//        while (m.find())
//        {
//            System.out.println( " m.group(): " + m.group());  // 打印所有
//
//            System.out.println( " m.group(1): " + m.group( 1 ));  // 打印数字的
//
//            System.out.println( " m.group(2): " + m.group( 2 ));  // 打印字母的
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
        matchTest.Patterntest();
    }
}
