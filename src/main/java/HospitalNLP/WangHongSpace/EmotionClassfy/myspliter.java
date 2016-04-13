package EmotionClassfy;
import ICTCLAS.I3S.AC.ICTCLAS50;


public class myspliter {
	
	public static String stringsplit;
	public void Participle(String str){
		testICTCLAS_ParagraphProcess(str);
	}
	
public  void init(){
	ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
	String argu = ".";
	try
	{
		//初始化
		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
		{
			System.out.println("Init Fail!");
			return;
		}	
	} catch (Exception ex){
		}
		
		//设置词性标注表
		testICTCLAS50.ICTCLAS_SetPOSmap(2);

		//导入用户词典
		int nCount = 0;
		String usrdir = "userdict.txt"; //用户字典路径
		byte[] usrdirb = usrdir.getBytes();//将String类型转化为bytes类型
		//导入用户字典，返回导入用户字典个数第一个为用户字典路径，第二个为用户字典的编码方式
		nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(usrdirb, 0);
		nCount = 0;
		System.out.println("导入用户词典成功");
	}

public static void testICTCLAS_ParagraphProcess(String sInput){
	
	try
	{
		ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
		byte nativeBytes1[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 0, 0);
		//System.out.println(nativeBytes1.length);
		stringsplit = new String(nativeBytes1, 0, nativeBytes1.length, "GB2312");
	}
	catch (Exception ex)
	{
	}

}

}
