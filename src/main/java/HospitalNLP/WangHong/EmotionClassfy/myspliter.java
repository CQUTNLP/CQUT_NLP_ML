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
		//��ʼ��
		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
		{
			System.out.println("Init Fail!");
			return;
		}	
	} catch (Exception ex){
		}
		
		//���ô��Ա�ע��
		testICTCLAS50.ICTCLAS_SetPOSmap(2);

		//�����û��ʵ�
		int nCount = 0;
		String usrdir = "userdict.txt"; //�û��ֵ�·��
		byte[] usrdirb = usrdir.getBytes();//��String����ת��Ϊbytes����
		//�����û��ֵ䣬���ص����û��ֵ������һ��Ϊ�û��ֵ�·�����ڶ���Ϊ�û��ֵ�ı��뷽ʽ
		nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(usrdirb, 0);
		nCount = 0;
		System.out.println("�����û��ʵ�ɹ�");
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
