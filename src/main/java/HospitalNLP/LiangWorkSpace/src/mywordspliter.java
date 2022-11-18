package HospitalNLP.LiangWorkSpace.src;

import HospitalNLP.LiangWorkSpace.src.ICTCLAS.I3S.AC.ICTCLAS50;

//锟斤拷装锟街达拷锟斤拷
public class mywordspliter {
	String insplit;
	public void split(String str){
		testICTCLAS_ParagraphProcess(str);
	}
	public void testICTCLAS_ParagraphProcess(String sInput)
	{
		
		try
		{
			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			String argu = ".";
			//锟斤拷始锟斤拷
			if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
			{
				System.out.println("Init Fail!");
				return;
			}

			
			//锟斤拷锟矫达拷锟皆憋拷注锟斤拷(0 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟�1 锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷注锟斤拷锟斤拷2 锟斤拷锟斤拷锟斤拷锟斤拷锟阶拷锟斤拷锟�3 锟斤拷锟斤拷一锟斤拷锟斤拷注锟斤拷)
			testICTCLAS50.ICTCLAS_SetPOSmap(2);


			//锟斤拷锟斤拷锟矫伙拷锟绞碉拷前锟街达拷
			byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"),1,1);//锟街词达拷锟斤拷 1  1
		//	System.out.println(nativeBytes.length);
			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
		//	System.out.println("未锟斤拷锟斤拷锟矫伙拷锟绞碉拷姆执式锟斤拷 " + nativeStr);//锟斤拷印锟斤拷锟�


			//锟斤拷锟斤拷锟矫伙拷锟街碉拷
			int nCount = 0;
			String usrdir = "userdict.txt"; //锟矫伙拷锟街碉拷路锟斤拷
			byte[] usrdirb = usrdir.getBytes();//锟斤拷string转锟斤拷为byte锟斤拷锟斤拷
			//锟斤拷锟斤拷锟矫伙拷锟街碉拷,锟斤拷锟截碉拷锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷为锟矫伙拷锟街碉拷路锟斤拷锟斤拷锟节讹拷锟斤拷锟斤拷锟斤拷为锟矫伙拷锟街碉拷谋锟斤拷锟斤拷锟斤拷锟�
			nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(usrdirb, 0);
			//System.out.println("锟斤拷锟斤拷锟矫伙拷锟绞革拷锟斤拷" + nCount);
			nCount = 0;


			//锟斤拷锟斤拷锟矫伙拷锟街碉拷锟斤拷俜执锟�
			byte nativeBytes1[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 2, 0);  //加上词性  2  1
		//	System.out.println(nativeBytes1.length);
			insplit = new String(nativeBytes1, 0, nativeBytes1.length, "GB2312");
		//	System.out.println("锟斤拷锟斤拷锟矫伙拷锟绞碉拷锟侥分词斤拷锟� " + nativeStr1);
			//锟斤拷锟斤拷锟矫伙拷锟街碉拷
			testICTCLAS50.ICTCLAS_SaveTheUsrDic();
			//锟酵放分达拷锟斤拷锟斤拷锟皆�
			testICTCLAS50.ICTCLAS_Exit();
		}
		catch (Exception ex)
		{
		}

	}

	public static void main(String[] args) {
		mywordspliter mws = new mywordspliter();
		String str="今天是个好天气";
		mws.split("str");
	}

}
