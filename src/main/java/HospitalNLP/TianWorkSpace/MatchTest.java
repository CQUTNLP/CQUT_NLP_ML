package HospitalNLP.TianWorkSpace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianjingwei on 16/4/2.
 */
public class MatchTest {

    public void ContainTest(){
        String str="Hello World";  //å¾??¤æ????å­?ç¬?ä¸?
        String prexStr="ll";
        String reg=".*ll.*";  //?¤æ??å­?ç¬?ä¸²ä¸­?????????¹å??å­?ç¬?ä¸?ll
        System.out.println(str.matches(reg));
        System.out.println(str.contains(prexStr));
    }
    public void ttt(){
        String Ps= ".*·Î.*°©.*";
        String PPS= "ÇëÎÊ·ÎÔÚÄÄ¶ù";
        Pattern p  =  Pattern.compile(Ps);
        String s="1.×óÏÂ·ÎÁÛ°©£¨T4N2M0£¬¢óbÆÚ£©2¡¢¶à·¢Ç»Ï¶ÐÔÄÔ¹£Èû 3¡¢ÄÔÎ®Ëõ  ";
        Matcher m = p.matcher(s);
        System.out.println(s.replace("¡¢","."));
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
    public void Patterntest(){
//        Pattern p1  =  Pattern.compile( "(.*)ll(.*)" );
        Pattern p2  =  Pattern.compile( "(.*)-(.*)-(.*)-(.*)" );
        String s  =   " 123aa-34345bb-234cc-00 " ;
//        String s = "Hello World";
        Matcher m  =  p2.matcher(s);
//        while (m.find())
//        {
//            System.out.println( " m.group(): " + m.group());  // ???°æ????
//
//            System.out.println( " m.group(1): " + m.group( 1 ));  // ???°æ?°å????
//
//            System.out.println( " m.group(2): " + m.group( 2 ));  // ???°å??æ¯???
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
        matchTest.ttt();
    }
}
