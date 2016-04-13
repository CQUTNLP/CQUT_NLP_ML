package NumberRecognition;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @param args
 * author,Striver
 */
public class MyBPNetWork {
	double []inputdata;
	double []x;
	int expect;
	int HiddenLayer;
	int []NeuralNumber;
	double speed=0.1;//����Ӧѧϰ����
	double lasterror=0;
	double error;
	double output=0;
	double [][]weight;
	double [][]lastweight;//���붯������
	double []y;
	double []Threshold;
	private double []nowgradient;
	private double []parentgradient;
	ArrayList<double [][]>allweight;
	ArrayList<double [][]>newweight;
	ArrayList<double [][]>weightcorrect;
	ArrayList<double []>ally;
	ArrayList<double []>allThreshold;
	double range = (float) 2.4/2;
	
	public void Getinputdata(double []inputdata,int expect)//��ȡ�������
	{
		this.inputdata = inputdata;
		this.x = inputdata.clone();
		this.expect = expect;
	}
	public void input()
	{
		System.out.println("������ĸ���");
		Scanner s1 = new Scanner(System.in);
		HiddenLayer = s1.nextInt();
		NeuralNumber = new int[HiddenLayer];
		for(int i=0;i<HiddenLayer;i++)
		{
			System.out.println("��"+(i+1)+"�����Ԫ�ĸ���");
			Scanner s2 = new Scanner(System.in);
			NeuralNumber[i]=s2.nextInt();
		}
		allweight = new ArrayList<double[][]>();
		ally = new ArrayList<double[]>();
		allThreshold = new ArrayList<double[]>();
		newweight = new ArrayList<double[][]>();
		weightcorrect = new ArrayList<double[][]>();
		s1.close();
	}
	public void InitWeight()//��ʼ��Ȩ�غ���ֵ
	{
		for(int Layer=0;Layer<=HiddenLayer;Layer++)
		{
			if(Layer==0)//��������ӵ�������
			{
				weight = new double[NeuralNumber[0]][inputdata.length];
				lastweight = new double[NeuralNumber[0]][inputdata.length];
				Threshold = new double[NeuralNumber[0]];
				for(int i=0;i<NeuralNumber[0];i++)
					for(int j=0;j<inputdata.length;j++)
					{
						weight[i][j]=(Math.random()*2*range-range);//��ʼ��Ȩ��
						lastweight[i][j]=0;
					}
				allweight.add(weight);
				weightcorrect.add(lastweight);
				for(int j=0;j<NeuralNumber[0];j++)
					Threshold[j]=(Math.random()*2*range-range);//��ʼ����ֵ
				allThreshold.add(Threshold);
			}
			else if(Layer==HiddenLayer)
			{
				weight = new double[1][NeuralNumber[HiddenLayer-1]];
				lastweight = new double[1][NeuralNumber[HiddenLayer-1]];
				for(int i=0;i<NeuralNumber[HiddenLayer-1];i++)
				{
					weight[0][i]=(Math.random()*2*range-range);//��ʼ��Ȩ��
					lastweight[0][i]=0;
				}
				allweight.add(weight);
				weightcorrect.add(lastweight);
				Threshold = new double[1];
				Threshold[0]=(Math.random()*2*range-range);//��ʼ����ֵ
				allThreshold.add(Threshold);
			}
			else//���������ӵ�������
			{
				weight = new double[NeuralNumber[Layer]][NeuralNumber[Layer-1]];
				lastweight = new double[NeuralNumber[Layer]][NeuralNumber[Layer-1]];
				Threshold = new double[NeuralNumber[Layer]];
				for(int i=0;i<NeuralNumber[Layer];i++)
					for(int j=0;j<NeuralNumber[Layer-1];j++)
					{
						weight[i][j]=(Math.random()*2*range-range);//��ʼ��Ȩ��
						lastweight[i][j]=0;
					}
				allweight.add(weight);
				weightcorrect.add(lastweight);
				for(int j=0;j<NeuralNumber[Layer];j++)
					Threshold[j]=(Math.random()*2*range-range);//��ʼ����ֵ
				allThreshold.add(Threshold);
			}
		}
		newweight.addAll(allweight);
	}
	public void sigmoid()//�����
	{
		for(int i=0;i<HiddenLayer;i++)
		{
			if(i==0)
			{
				x = inputdata.clone();
			}
			if(i>0)
				x=y;
			y = new double[NeuralNumber[i]];
			for(int j=0;j<NeuralNumber[i];j++)
			{
				double input=0;
				for(int k=0;k<x.length;k++)//����������ʵ�����
				{
					input+=x[k]*allweight.get(i)[j][k];
				}
				input=input-allThreshold.get(i)[j];
				y[j] = 1/(1+Math.pow(Math.E,-(input)));
			}
			ally.add(y);
		}
		for(int j=0;j<y.length;j++)
		{
			output+=y[j]*allweight.get(HiddenLayer)[0][j];
		}
		output=output-allThreshold.get(HiddenLayer)[0];
		output = 1/(1+Math.pow(Math.E,-(output)));
		error = expect-output;
	}
	public void WeightTrain()
	{
		weight = new double[1][NeuralNumber[HiddenLayer-1]];
		lastweight = new double[1][NeuralNumber[HiddenLayer-1]];
		nowgradient = new double[1];//����������ݶ�
		nowgradient[0] = output*(1-output)*error;
		parentgradient=nowgradient.clone();
		for(int i=0;i<NeuralNumber[HiddenLayer-1];i++)
		{
			lastweight[0][i]=speed*nowgradient[0]*ally.get(HiddenLayer-1)[i];
			weight[0][i]=allweight.get(HiddenLayer)[0][i]+lastweight[0][i]+weightcorrect.get(HiddenLayer)[0][i];
		}
		weightcorrect.set(HiddenLayer,lastweight);
		newweight.set(HiddenLayer,weight);//����������������Ȩ������
		Threshold[0]=allThreshold.get(HiddenLayer)[0]+speed*(-1)*nowgradient[0];
		allThreshold.set(HiddenLayer, Threshold);
		for(int i=HiddenLayer-1;i>=0;i--)
		{
			if(i==0)//���������������
			{
				weight = new double[NeuralNumber[0]][inputdata.length];
				lastweight = new double[NeuralNumber[0]][inputdata.length];
				nowgradient = new double[NeuralNumber[0]];
				Threshold = new double[NeuralNumber[0]];
				for(int j=0;j<NeuralNumber[0];j++)
				{
					nowgradient[j]=0;
					for(int k=0;k<parentgradient.length;k++)
						nowgradient[j]+=allweight.get(i+1)[k][j]*parentgradient[k];
					nowgradient[j]*=ally.get(i)[j]*(1-ally.get(i)[j]);
					for(int k=0;k<inputdata.length;k++)
					{
						lastweight[j][k] = speed*inputdata[k]*nowgradient[j];
						weight[j][k]=allweight.get(i)[j][k]+lastweight[j][k]+weightcorrect.get(i)[j][k];
					}
					Threshold[j]=allThreshold.get(i)[j]+speed*(-1)*nowgradient[j];
				}
				parentgradient=nowgradient.clone();
				newweight.set(i,weight);
				weightcorrect.set(i, lastweight);
				allThreshold.set(i, Threshold);
			}
			else//����������������
			{
				weight = new double[NeuralNumber[i]][NeuralNumber[i-1]];
				lastweight = new double[NeuralNumber[i]][NeuralNumber[i-1]];
				nowgradient = new double[NeuralNumber[i]];
				Threshold = new double[NeuralNumber[i]];
				for(int j=0;j<NeuralNumber[i];j++)
				{
					nowgradient[j]=0;
					for(int k=0;k<parentgradient.length;k++)
						nowgradient[j]+=allweight.get(i+1)[k][j]*parentgradient[k];
					nowgradient[j]*=ally.get(i)[j]*(1-ally.get(i)[j]);
					for(int k=0;k<NeuralNumber[i-1];k++)
					{
						lastweight[j][k] = speed*ally.get(i-1)[k]*nowgradient[j];
						weight[j][k]=allweight.get(i)[j][k]+lastweight[j][k]+weightcorrect.get(i)[j][k];
					}
					Threshold[j]=allThreshold.get(i)[j]+speed*(-1)*nowgradient[j];
				}
				parentgradient=nowgradient.clone();
				newweight.set(i,weight);
				weightcorrect.set(i,lastweight);
				allThreshold.set(i, Threshold);
			}
		}
	}
	public void update()
	{
		allweight.clear();
		allweight.addAll(newweight);
		ally.clear();
	}
	public void run()
	{
		sigmoid();//��ʼ���Լ������
		WeightTrain();//Ȩ��ѵ��
		update();//����Ȩֵ
//		if(Math.pow(lasterror,2)<Math.pow(error,2))//ͨ��Ƚ����δε����ƽ����������ѧϰ����
//			speed=speed*1.05;//�����һ�ε����ƽ���ʹ�����һ�εģ���˵�����Խ��Խ����Ҫ����ѧϰ���ʣ������С
//		else
//			speed=speed*0.7;
//		lasterror = error;
	}
	public double GetError()
	{
		return this.error;
	}
}
