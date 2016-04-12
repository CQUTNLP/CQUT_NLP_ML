package NumberRecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
  /**
 	* @param args
 	* 基于神经网络的数字训练
 	* 训练数据为32*32的像素矩阵点
 	* 输入层有1024个神经元,隐含层可由自己设定层数与相应的神经元个数,输出层为1个神经元
 	*/
public class NeuralTrain {
	double[] pixels;//输入的32*32像素矩阵
	int expect;//对应的每个输入文件的期望值
	int size1;//输入训练样本个数
	int size2;//输入测试样本个数
	BufferedReader in1;
	BufferedReader in2;
	File[] data1;
	File[] data2;
	public static void main(String[] args) throws Exception{
		NeuralTrain train = new NeuralTrain();
		System.out.println("测试集个数 "+train.size2);
		MyBPNetWork bp = new MyBPNetWork();
//		double a[] = new double[2];//测试异或逻辑操作是否成功
//		a[0]=1.0;
//		a[1]=1.0;
//		bp.Getinputdata(a, 0);//异或逻辑操作
//		bp.input();
//		bp.InitWeight();
//		while(true)
//		{
//			bp.run();
//			if(Math.pow(bp.GetError(), 2)<0.001)
//				break;
//		}
//		System.out.println(bp.output);
		
		train.ImportData(0);
		bp.Getinputdata(train.pixels, train.expect);
		bp.input();
		bp.InitWeight();  
		bp.run();
		int num=0;
		while(true)
		{
			int count=1;
			while(count<train.size1)
			{
				train.ImportData(count);//将文件里的数据导入内存
				bp.Getinputdata(train.pixels,train.expect);//将输入数据传入神经网络
				bp.run();
				count++;
			}
			num++;
			System.out.println("循环第"+num+"次");
			System.out.println("误差"+bp.GetError());
			if(Math.pow(bp.GetError(), 2)<0.001||num==50)
				break;
		}
		System.out.println("期望值 "+train.test(500));
		bp.sigmoid();
		System.out.println("输出 "+bp.output);
		
//		boolean flag = true;
//		int count = 1;
//		while(true)
//		{
//			train.ImportData(count);
//			while(true)
//			{
//				bp.Getinputdata(train.pixels,train.expect);
//				bp.run();
//				if(Math.pow(bp.GetError(), 2)<0.001)
//					break;
//				//if(train.data[count].getName().charAt(0)=='2')
//				System.out.println("  误差  "+bp.GetError());
//			}
//			count++;
//			if(count==train.size&&bp.GetError()<0.001)
//				break;
//			if(count==train.size)
//				count=0;
//		}
	}
	public NeuralTrain()
	{
		File trainfile = new File("C:/Users/Administrator/Desktop/数字识别原始数据/trainingDigits");
		File testfile = new File("C:/Users/Administrator/Desktop/数字识别原始数据/testDigits");
		data1 = trainfile.listFiles();
		data2 = testfile.listFiles();
		size1 = data1.length;
		size2 = data2.length;
		pixels = new double[1024];
	}
	public void ImportData(int count) throws Exception
	{
		in1 = new BufferedReader(new FileReader(data1[count]));
		//System.out.println(data[count].getName());
		expect = Integer.parseInt(String.valueOf(data1[count].getName().charAt(0)));
		for(int i=0;i<32;i++)
		{
			for(int j=0;j<34;j++)
			{
				int c = in1.read();
				if(c!=13&&c!=10)
				{
					pixels[i*32+j] = c-48;
				}
			}
		}
	}
	public int test(int count) throws Exception
	{
		in2 = new BufferedReader(new FileReader(data2[count]));
		expect = Integer.parseInt(String.valueOf(data2[count].getName().charAt(0)));
		for(int i=0;i<32;i++)
		{
			for(int j=0;j<34;j++)
			{
				int c = in2.read();
				if(c!=13&&c!=10)
				{
					pixels[i*32+j] = c-48;
				}
			}
		}
		return expect;
	}
//	public void Init()//初始化权重和阈值
//	{
//		double range = (float) 2.4/2;
//		for(int i=0;i<1024;i++)
//			for(int j=0;j<2;j++)
//			{
//				weight1[i][j] = (Math.random()*2*range-range);
//			}
//		weight2[0][0] = (Math.random()*2*range-range);
//		weight2[1][0] = (Math.random()*2*range-range);
//		Threshold3=(Math.random()*2*range-range);
//		Threshold4=(Math.random()*2*range-range);
//		Threshold5=(Math.random()*2*range-range);
//	}
//	public void Forward()
//	{
//		double x3 = 0,x4 = 0,x5 = 0;
//		for(int i=0;i<1024;i++)
//		{
//			x3 += pixels[i]*weight1[i][0];
//			x4 += pixels[i]*weight1[i][1];
//		}
//		x3 = x3-Threshold3;
//		x4 = x4-Threshold4;
//		y3 = 1/(1+(Math.pow(Math.E, -x3)));
//		y4 = 1/(1+(Math.pow(Math.E, -x4)));
//		x5 = y3*weight2[0][0]+y4*weight2[1][0]-Threshold5;
//		y5 = 1/(1+(Math.pow(Math.E, -x5)));
//		error = expect-y5;
//	}
//	public void BackwordUpdate()
//	{
//		Gradient5 = y5*(1-y5)*error;
//		Gradient4 = y4*(1-y4)*weight2[1][0]*Gradient5;
//		Gradient3 = y3*(1-y3)*weight2[0][0]*Gradient5;
//		weight2[1][0]+=speed*y4*Gradient5;
//		weight2[0][0]+=speed*y3*Gradient5;
//		Threshold5+=speed*(-1)*Gradient5;
//		Threshold4+=speed*(-1)*Gradient4;
//		Threshold3+=speed*(-1)*Gradient3;
//		for(int i=0;i<1024;i++)
//		{
//			weight1[i][0]+=speed*pixels[i]*Gradient3;
//			weight1[i][1]+=speed*pixels[i]*Gradient4;
//		}
//	}
}
