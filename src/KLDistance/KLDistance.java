package KLDistance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleToLongFunction;

import org.omg.CORBA.PUBLIC_MEMBER;

import BLEU.DataTransandAsss;
import Impovement.Improvement;
import ReadfromPeople.LoadAssessment;
import ReadfromPeople.LoadScoresofMachineAssessment;
import ReadfromPeople.Passage;
import ReadfromPeople.ScoresforPassage;
//这个类包含两个算法，一个是算kldistance 的 但是算出来不相关  所以第二个算法直接计算相关度，有结果
public class KLDistance 
{
	ArrayList<Passage> ScoresfromLDA;
	/*
	 * Passage结构 String SystemID; 
	 * //系统编号 String DocID;
	 * //译文文档编号 double Score;
	 * //计算过程 存放的是
	 */
	public ArrayList<Passage> getScoresfromLDA() {
		return ScoresfromLDA;
	}

	public void setScoresfromLDA(ArrayList<Passage> scoresfromLDA) {
		ScoresfromLDA = scoresfromLDA;
	}


	public KLDistance(ArrayList<Passage> scoresfromLDA) 
	{
		ScoresfromLDA = scoresfromLDA;
	}

	public KLDistance() 
	{
		ScoresfromLDA = new ArrayList<Passage>();
	}
////============计算KL距离==============================================================================
	public void conuntdistance(ReadfromTheta rft,ScoresforPassage namefordoc)//namefordoc后一个是为了获取对应的文件名和系统名
	{
		ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
		ArrayList<Theta> Translation=rft.getTranslation();
		ArrayList<Theta> Answer=rft.getAnswer();
		ArrayList<double[]> forcount=null;
		List <Double>socreforall= new ArrayList<Double>();		
		for (int i=0;i<Translation.size();i++)
		{
			forcount=new ArrayList<double[]>();
			for(int j=0;j<Answer.size();j++)
			{
		      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
			 double[] d=countdistance_twofile_KL(Translation.get(i).getData_one_sys(),Answer.get(j).getData_one_sys(),Translation.get(0).getrowandcolumn());
			 forcount.add(d);
			}
			for(int k=0;k<forcount.get(0).length;k++)  //取answer的每个的k 计算平均 并存
			{
				double avg=0;
				for(int q=0;q<forcount.size();q++)
				{
					avg+=forcount.get(q)[k];
				}
				avg=avg/forcount.size();
				socreforall.add(avg);  //跑一次k增加1  跑一次i增加100
			}
		}
		for (int i=0;i<ScoresfromLDA.size();i++)
		{
			ScoresfromLDA.get(i).setScore(socreforall.get(i));
		}
	}

	public double[] countdistance_twofile_KL(double[] translation, double[] answer,String rowandcolumn) //100*66列的
	{
		String []a=rowandcolumn.split("\\*");
		int row=Integer.parseInt(a[0]);
		int column=Integer.parseInt(a[1]);
		double[]temptrans=null;
		double[]tempansw=null;
		double[]score=new double[row];
		int j=0;
 		for(int i=0;i<row;i++)
		{
 			//拆分100*66 为每行66的数组  传入
			temptrans=Arrays.copyOfRange(translation, j, j+column); //最后一个元素是exclusive的 多看jdk说明!!!!
			tempansw=Arrays.copyOfRange(answer, j, j+column);
			score[i]=countdistance_oneline_KL(temptrans,tempansw);
			j=j+column;
		
		}
		return score;
	}
	public double countdistance_oneline_KL(double[] translation, double[] answer) //KLD（T|A）已知答案，估计翻译
	{
		double distance=0;
		for (int i=0;i<translation.length;i++)
		{
		    distance+=translation[i]*Math.log(translation[i]/answer[i]);
		}
		return distance;
	}
	
//===============直接归一化协方差============上面的kldsitance做出来无相关性=============================
	public void conuntcorrelation(ReadfromTheta rft,ScoresforPassage namefordoc)//namefordoc后一个是为了获取对应的文件名和系统名
	{
		ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
		ArrayList<Theta> Translation=rft.getTranslation();
		ArrayList<Theta> Answer=rft.getAnswer();
		ArrayList<double[]> forcount=null;
		List <Double>socreforall= new ArrayList<Double>();		
		for (int i=0;i<Translation.size();i++)
		{
			forcount=new ArrayList<double[]>();
			for(int j=0;j<Answer.size();j++)
			{
		      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
			 double[] d=countcorrelation_twofile(Translation.get(i).getData_one_sys(),Answer.get(j).getData_one_sys(),Translation.get(0).getrowandcolumn());
			 forcount.add(d);
			}
			for(int k=0;k<forcount.get(0).length;k++)  //取answer的每个的k 计算平均 并存
			{
				double avg=0;
				for(int q=0;q<forcount.size();q++)
				{
					avg+=forcount.get(q)[k];
				}
				avg=avg/forcount.size();
				socreforall.add(avg);  //跑一次k增加1  跑一次i增加100
			}
		}
		for (int i=0;i<ScoresfromLDA.size();i++)
		{
			ScoresfromLDA.get(i).setScore(socreforall.get(i));
		}
	}
	
	public double[] countcorrelation_twofile(double[] translation, double[] answer,String rowandcolumn) //100*66列的
	{
		String []a=rowandcolumn.split("\\*");
		int row=Integer.parseInt(a[0]);
		int column=Integer.parseInt(a[1]);
		double[]temptrans=null;
		double[]tempansw=null;
		double[]score=new double[row];
		int j=0;
 		for(int i=0;i<row;i++)
		{
 			//拆分100*66 为每行66的数组  传入
			temptrans=Arrays.copyOfRange(translation, j, j+column); //最后一个元素是exclusive的 多看jdk说明!!!!
			tempansw=Arrays.copyOfRange(answer, j, j+column);
			score[i]=countcorrelation_oneline(temptrans,tempansw);
			j=j+column;
		
		}
		return score;
	}
	public double countcorrelation_oneline(double[] translation, double[] answer) //KLD（T|A）已知答案，估计翻译
	{
		double avgtrans=0;
		double avganswer=0;
		
		for (int i=0;i<translation.length;i++)
		{
		    avgtrans+=translation[i];
		}
		avgtrans=avgtrans/translation.length;
		for (int i=0;i<answer.length;i++)
		{
			avganswer+=answer[i];
		}
		avganswer=avgtrans/answer.length;
		

		double total=0;
	
		 double numerator=0;
		 double denominator=0;
		 
		 double denominator_1=0;
		 double denominator_2=0;   
		 
		 double t1=0;
		 double t2=0;
		 for(int i=0;i<translation.length;i++)
		 {
					 numerator+=(translation[i]-avgtrans)*(answer[i]-avganswer);
					 denominator_1+= Math.pow(translation[i]-avgtrans,2);
					 denominator_2+= Math.pow(answer[i]-avganswer,2);
		 }
		 double result=0;
		 denominator_1=Math.sqrt(denominator_1);
		 denominator_2=Math.sqrt(denominator_2);
		 denominator=denominator_1*denominator_2;
				 
		 result=numerator/denominator;
		
		return result;
	}
	
//============只取LDA大的主题计算 前五个 归一化协方差================================================================
	public void conuntcorrelation_highpossibility(ReadfromTheta rft,ScoresforPassage namefordoc,int lengthofdataofanswer)//namefordoc后一个是为了获取对应的文件名和系统名
	{
		ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
		ArrayList<Theta> Translation=rft.getTranslation();
		ArrayList<Theta> Answer=rft.getAnswer();
		ArrayList<double[]> forcount=null;
		List <Double>socreforall= new ArrayList<Double>();		
		for (int i=0;i<Translation.size();i++)
		{
			forcount=new ArrayList<double[]>();
			for(int j=0;j<Answer.size();j++)
			{
		      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
			 double[] d=countcorrelation_twofile_highpossibility(Translation.get(i).getData_one_sys(),Answer.get(j).getData_one_sys(),Translation.get(0).getrowandcolumn(),lengthofdataofanswer);
			 forcount.add(d);
			}
			for(int k=0;k<forcount.get(0).length;k++)  //取answer的每个的k 计算平均 并存
			{
				double avg=0;
				for(int q=0;q<forcount.size();q++)
				{
					avg+=forcount.get(q)[k];
				}
				avg=avg/forcount.size();
				socreforall.add(avg);  //跑一次k增加1  跑一次i增加100
			}
		}
		for (int i=0;i<ScoresfromLDA.size();i++)
		{
			ScoresfromLDA.get(i).setScore(socreforall.get(i));
		}
	}
	
	public double[] countcorrelation_twofile_highpossibility(double[] translation, double[] answer,String rowandcolumn,int lengthofdataofanswer) //100*66列的
	{
		String []a=rowandcolumn.split("\\*");
		int row=Integer.parseInt(a[0]);
		int column=Integer.parseInt(a[1]);
		double[]temptrans=null;
		double[]tempansw=null;
		double[]score=new double[row];
		int j=0;
 		for(int i=0;i<row;i++)
		{
 			//拆分100*66 为每行66的数组  传入
			temptrans=Arrays.copyOfRange(translation, j, j+column); //最后一个元素是exclusive的 多看jdk说明!!!!
			tempansw=Arrays.copyOfRange(answer, j, j+column);
			score[i]=countcorrelation_oneline_highpossibility(temptrans,tempansw,lengthofdataofanswer);
			j=j+column;
		
		}
		return score;
	}
	public double countcorrelation_oneline_highpossibility(double[] t, double[] a,int lengthofdataofanswer) //KLD（T|A）已知答案，估计翻译
	{
		double []temp_t=new double[t.length];
		double []temp_a=new double[a.length];
		for(int i=0;i<t.length;i++)//深拷贝
		{
			temp_t[i]=t[i];
			temp_a[i]=a[i];
		}
//		int []index_a=new int[a.length];
//		for(int i=0;i<index_a.length;i++)  //初始化序号
//		{
//			index_a[i]=i;
//		}
		for(int i=0;i<temp_a.length;i++)
		{
			for(int j=i+1;j<temp_a.length;j++)
			{
				if(temp_a[i]<temp_a[j])
				{
					double temp=temp_a[i];
					temp_a[i]=temp_a[j];
					temp_a[j]=temp;
					
					double temp2=temp_t[i];
					temp_t[i]=temp_t[j];
					temp_t[j]=temp2;
					
//					int tempindex=index_a[i];  //对序号也进项换
//					index_a[i]=index_a[j];
//					index_a[j]=tempindex;
					
				}
					
			}
		}
		double []translation=new double[temp_t.length];
		double []answer=new double[temp_a.length];
		for(int i=0;i<t.length;i++)//深拷贝
		{
			translation[i]=temp_t[i];
			answer[i]=temp_a[i];
		}
		//下面是计算
		double avgtrans=0;
		double avganswer=0;
		
		for (int i=0;i<lengthofdataofanswer-1;i++)
		{
		    avgtrans+=translation[i];
		}
		avgtrans=avgtrans/lengthofdataofanswer;
		for (int i=0;i<lengthofdataofanswer-1;i++)
		{
			avganswer+=answer[i];
		}
		avganswer=avgtrans/lengthofdataofanswer;
		

		double total=0;
	
		 double numerator=0;
		 double denominator=0;
		 
		 double denominator_1=0;
		 double denominator_2=0;   
		 
		 double t1=0;
		 double t2=0;
		 for(int i=0;i<lengthofdataofanswer-1;i++)
		 {
					 numerator+=(translation[i]-avgtrans)*(answer[i]-avganswer);
					 denominator_1+= Math.pow(translation[i]-avgtrans,2);
					 denominator_2+= Math.pow(answer[i]-avganswer,2);
		 }
		 double result=0;
		 denominator_1=Math.sqrt(denominator_1);
		 denominator_2=Math.sqrt(denominator_2);
		 denominator=denominator_1*denominator_2;
				 
		 result=numerator/denominator;
		
		return result;
	}	

//=============数满足阈值的 有多少个有多少个有多少个有多少个有多少个有多少个！==发现这个阈值挺难找，取0.05还是有很多3个的 所以打算直接找出前五个===================
		public void countthreshold_num(ReadfromTheta rft,ScoresforPassage namefordoc,double threshold)
		{
			ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
			ArrayList<Theta> Translation=rft.getTranslation();
			ArrayList<Theta> Answer=rft.getAnswer();
			ArrayList<double[]> forcount=null;
			List <Double>socreforall= new ArrayList<Double>();		
		
				forcount=new ArrayList<double[]>();
				for(int j=0;j<Answer.size();j++)
				{
			      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
				 double[] d=countthreshold_answer_threshold_num(Answer.get(j).getData_one_sys(),Answer.get(0).getrowandcolumn(),threshold);
				 forcount.add(d);
				}
				for(int i=0;i<forcount.get(0).length;i++)
				{
					System.out.println(forcount.get(0)[i]);
				}
				for(int i=0;i<forcount.get(1).length;i++)
				{
					System.out.println(forcount.get(1)[i]);
				}
				for(int i=0;i<forcount.get(2).length;i++)
				{
					System.out.println(forcount.get(2)[i]);
				}
				for(int i=0;i<forcount.get(3).length;i++)
				{
					System.out.println(forcount.get(3)[i]);
				}
		}
		public double[] countthreshold_answer_threshold_num(double[] answer,String rowandcolumn,double threshold) //100*66列的
		{
			String []a=rowandcolumn.split("\\*");
			int row=Integer.parseInt(a[0]);
			int column=Integer.parseInt(a[1]);
			double[]tempansw=null;
			double[]score=new double[row];
			int j=0;
	 		for(int i=0;i<row;i++)
			{
	 			//拆分100*66 为每行66的数组  传入
				tempansw=Arrays.copyOfRange(answer, j, j+column);
				score[i]=countthreshold_onefile_threshold_num(tempansw,threshold);
				j=j+column;
			
			}
			return score;
		}
		//这个是算数量的11111111
		public double countthreshold_onefile_threshold_num(double[] answer,double threshold)
		{
			double count=0;
			for(int i=0;i<answer.length;i++)
			{
				if(answer[i]>threshold)
				{
					count++;
				}
			}
			return count;
			
		}
//==================算只有阈值的归一化协方差============================================================================================
		public void countthreshold_score(ReadfromTheta rft,ScoresforPassage namefordoc,double threshold)
		{
			ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
			ArrayList<Theta> Translation=rft.getTranslation();
			ArrayList<Theta> Answer=rft.getAnswer();
			ArrayList<double[]> forcount=null;
			List <Double>socreforall= new ArrayList<Double>();		
			for (int i=0;i<Translation.size();i++)
			{
				forcount=new ArrayList<double[]>();
				for(int j=0;j<Answer.size();j++)
				{
			      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
				 double[] d=countthreshold_twofile_score(Translation.get(i).getData_one_sys(),Answer.get(j).getData_one_sys(),Translation.get(0).getrowandcolumn(),threshold);
				 forcount.add(d);
				}
				for(int k=0;k<forcount.get(0).length;k++)  //取answer的每个的k 计算平均 并存
				{
					double avg=0;
					for(int q=0;q<forcount.size();q++)
					{
						avg+=forcount.get(q)[k];
					}
					avg=avg/forcount.size();
					socreforall.add(avg);  //跑一次k增加1  跑一次i增加100
				}
			}
			for (int i=0;i<ScoresfromLDA.size();i++)
			{
				ScoresfromLDA.get(i).setScore(socreforall.get(i));
			}
		}
		public double[] countthreshold_twofile_score(double[]translation,double[] answer,String rowandcolumn,double threshold) //100*66列的
		{
			String []a=rowandcolumn.split("\\*");
			int row=Integer.parseInt(a[0]);
			int column=Integer.parseInt(a[1]);
			double[]temptrans=null;
			double[]tempansw=null;
			double[]score=new double[row];
			int j=0;
	 		for(int i=0;i<row;i++)
			{
	 			//拆分100*66 为每行66的数组  传入
				temptrans=Arrays.copyOfRange(translation, j, j+column); //最后一个元素是exclusive的 多看jdk说明!!!!
				tempansw=Arrays.copyOfRange(answer, j, j+column);
				score[i]=countthreshold_onefile_score(temptrans,tempansw,threshold);
				j=j+column;
			
			}
			return score;
		}
		//这个是算阈值分数的22222222
		public double countthreshold_onefile_score(double[] t,double[] a,double threshold)
		{
			
			double []temp_t=new double[t.length];
			double []temp_a=new double[a.length];
			for(int i=0;i<t.length;i++)//深拷贝
			{
				temp_t[i]=t[i];
				temp_a[i]=a[i];
			}

			int num=(int) countthreshold_onefile_threshold_num(temp_a,threshold);
			
				for(int i=0,j=0;i<temp_a.length&&j<num;i++)
				{
					if(temp_a[i]>=threshold)
					{
						temp_a[j]=temp_a[i];
						j++;
					}
				}

			
			 
				double lengthofdataofanswer=num;
			
			double []translation=new double[temp_t.length];
			double []answer=new double[temp_a.length];
			for(int i=0;i<t.length;i++)//深拷贝
			{
				translation[i]=temp_t[i];
				answer[i]=temp_a[i];
			}

			
			double avgtrans=0;
			double avganswer=0;
			
			for (int i=0;i<lengthofdataofanswer-1;i++)
			{
			    avgtrans+=translation[i];
			}
			avgtrans=avgtrans/lengthofdataofanswer;
			for (int i=0;i<lengthofdataofanswer-1;i++)
			{
				avganswer+=answer[i];
			}
			avganswer=avgtrans/lengthofdataofanswer;
			

			double total=0;
		
			 double numerator=0;
			 double denominator=0;
			 
			 double denominator_1=0;
			 double denominator_2=0;   
			 
			 double t1=0;
			 double t2=0;
			 for(int i=0;i<lengthofdataofanswer-1;i++)
			 {
						 numerator+=(translation[i]-avgtrans)*(answer[i]-avganswer);
						 denominator_1+= Math.pow(translation[i]-avgtrans,2);
						 denominator_2+= Math.pow(answer[i]-avganswer,2);
			 }
			 double result=0;
			 denominator_1=Math.sqrt(denominator_1);
			 denominator_2=Math.sqrt(denominator_2);
			 denominator=denominator_1*denominator_2;
					 
			 result=numerator/denominator;
			
			return result;
			
		}
//==============计算correlation， 每行有阈值，不满的取前五个==========================================================
		public void conuntcorrelation_highpossibility_threshold(ReadfromTheta rft,ScoresforPassage namefordoc,int lengthofdataofanswer,double threshold)//namefordoc后一个是为了获取对应的文件名和系统名
		{
			ScoresfromLDA=namefordoc.getPassage_list(); //先放result的结果，然后下面计算好再set
			ArrayList<Theta> Translation=rft.getTranslation();
			ArrayList<Theta> Answer=rft.getAnswer();
			ArrayList<double[]> forcount=null;
			List <Double>socreforall= new ArrayList<Double>();		
			for (int i=0;i<Translation.size();i++)
			{
				forcount=new ArrayList<double[]>();
				for(int j=0;j<Answer.size();j++)
				{
			      //传入的是每个系统所对应的 100*66(文件数*topic数)个数据
				 double[] d=countcorrelation_twofile_highpossibility_threshold(Translation.get(i).getData_one_sys(),Answer.get(j).getData_one_sys(),Translation.get(0).getrowandcolumn(),lengthofdataofanswer,threshold);
				 forcount.add(d);
				}
				for(int k=0;k<forcount.get(0).length;k++)  //取answer的每个的k 计算平均 并存
				{
					double avg=0;
					for(int q=0;q<forcount.size();q++)
					{
						avg+=forcount.get(q)[k];
					}
					avg=avg/forcount.size();
					socreforall.add(avg);  //跑一次k增加1  跑一次i增加100
				}
			}
			for (int i=0;i<ScoresfromLDA.size();i++)
			{
				ScoresfromLDA.get(i).setScore(socreforall.get(i));
			}
		}
		
		public double[] countcorrelation_twofile_highpossibility_threshold(double[] translation, double[] answer,String rowandcolumn,int lengthofdataofanswer,double threshold) //100*66列的
		{
			String []a=rowandcolumn.split("\\*");
			int row=Integer.parseInt(a[0]);
			int column=Integer.parseInt(a[1]);
			double[]temptrans=null;
			double[]tempansw=null;
			double[]score=new double[row];
			int j=0;
	 		for(int i=0;i<row;i++)
			{
	 			//拆分100*66 为每行66的数组  传入
				temptrans=Arrays.copyOfRange(translation, j, j+column); //最后一个元素是exclusive的 多看jdk说明!!!!
				tempansw=Arrays.copyOfRange(answer, j, j+column);
				score[i]=countcorrelation_oneline_highpossibility222(temptrans,tempansw,lengthofdataofanswer, threshold);
				j=j+column;
			
			}
			return score;
		}
		public double countcorrelation_oneline_highpossibility222(double[] t, double[] a,int lengthofdataofanswer,double threshold) 
		{
			//这个函数式 有阈值满足4-6个的 就用 其他的用前5个
			double []temp_t=new double[t.length];
			double []temp_a=new double[a.length];
			for(int i=0;i<t.length;i++)//深拷贝
			{
				temp_t[i]=t[i];
				temp_a[i]=a[i];
			}
//			int []index_a=new int[a.length];
//			for(int i=0;i<index_a.length;i++)  //初始化序号
//			{
//				index_a[i]=i;
//			}
			int num=(int) countthreshold_onefile_threshold_num(temp_a,threshold);
			
			if(num>=lengthofdataofanswer)
{
	for(int i=0,j=0;i<temp_a.length&&j<num;i++)
	{
		if(temp_a[i]>=threshold)
		{
			temp_a[j]=temp_a[i];
			j++;
		}
	}
}
			else
{
	for(int i=0;i<temp_a.length;i++)
	{
		for(int j=i+1;j<temp_a.length;j++)
		{
			if(temp_a[i]<temp_a[j])
			{
				double temp=temp_a[i];
				temp_a[i]=temp_a[j];
				temp_a[j]=temp;
				
				double temp2=temp_t[i];
				temp_t[i]=temp_t[j];
				temp_t[j]=temp2;
				
//						int tempindex=index_a[i];  //对序号也进项换
//						index_a[i]=index_a[j];
//						index_a[j]=tempindex;
				
			}
				
		}
	}
}
			if(num>lengthofdataofanswer)
			{
				lengthofdataofanswer=num;
			}
			double []translation=new double[temp_t.length];
			double []answer=new double[temp_a.length];
			for(int i=0;i<t.length;i++)//深拷贝
			{
				translation[i]=temp_t[i];
				answer[i]=temp_a[i];
			}

			//下面是计算
			double avgtrans=0;
			double avganswer=0;
			
			for (int i=0;i<lengthofdataofanswer-1;i++)
			{
			    avgtrans+=translation[i];
			}
			avgtrans=avgtrans/lengthofdataofanswer;
			for (int i=0;i<lengthofdataofanswer-1;i++)
			{
				avganswer+=answer[i];
			}
			avganswer=avgtrans/lengthofdataofanswer;
			

			double total=0;
		
			 double numerator=0;
			 double denominator=0;
			 
			 double denominator_1=0;
			 double denominator_2=0;   
			 
			 double t1=0;
			 double t2=0;
			 for(int i=0;i<lengthofdataofanswer-1;i++)
			 {
						 numerator+=(translation[i]-avgtrans)*(answer[i]-avganswer);
						 denominator_1+= Math.pow(translation[i]-avgtrans,2);
						 denominator_2+= Math.pow(answer[i]-avganswer,2);
			 }
			 double result=0;
			 denominator_1=Math.sqrt(denominator_1);
			 denominator_2=Math.sqrt(denominator_2);
			 denominator=denominator_1*denominator_2;
					 
			 result=numerator/denominator;
			
			return result;
		}	
//=============================================================================================================================================

public void saveKLD(String filename)
	{
			FileWriter fw = null;
			try {
			//如果文件存在，就删除，如果文件不存在，则创建文件
			File f=new File(filename);
			 if(f.exists())
			{
				f.delete();
			}
			fw = new FileWriter(f, true);
			} catch (IOException e) {
			e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			ArrayList<Passage> a=this.ScoresfromLDA;
			for(int i=0;i<a.size();i++)
			{
				pw.println(a.get(i).getSystemID()+" "+a.get(i).getDocID()+"  "+a.get(i).getScore());
			}
			pw.flush();
			try {
			fw.flush();
			pw.close();
			fw.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	public ArrayList<Passage> standardise(ArrayList<Passage> a)
	{
		ArrayList<Passage> standard=a;
		double max=standard.get(0).getScore();
		double min=standard.get(0).getScore();
		for (int i=0;i<standard.size();i++)
		{
			if(standard.get(i).getScore()>max)
			{
				max=standard.get(i).getScore();
			}
			if(standard.get(i).getScore()<min)
			{
				min=standard.get(i).getScore();
			}
		}
		for(int i=0;i<standard.size();i++)
		{
			standard.get(i).setScore((standard.get(i).getScore()-min)/(max-min));
		}
		return standard;
	}
	public void standardise()
	{
		ArrayList<Passage> standard=this.ScoresfromLDA;
		double max=standard.get(0).getScore();
		double min=standard.get(0).getScore();
		for (int i=0;i<standard.size();i++)
		{
			if(standard.get(i).getScore()>max)
			{
				max=standard.get(i).getScore();
			}
			if(standard.get(i).getScore()<min)
			{
				min=standard.get(i).getScore();
			}
		}
		for(int i=0;i<standard.size();i++)
		{
			standard.get(i).setScore((standard.get(i).getScore()-min)/(max-min));
		}
		
	}
	public static void main(String[] args) throws IOException 
	{
		LoadAssessment la=new LoadAssessment("E://biyesheji//data//mt_chinese_easy.txt");
		la.Load();
//		la.Printarrylist();
//		la.PrintJudgelist();
		ScoresforPassage sp = new ScoresforPassage();
		sp.cal_Score(la.getAss_list(), la.getJudge_list());  //测试成功  能够把同�?文章的所有评价拉�?
		sp.sort();
		KLDistance kld=new KLDistance();
		ReadfromTheta r=new ReadfromTheta(9,22,1,4,"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem66");

		kld.conuntdistance(r,sp);     //KLDresult.txt                计算KL距离
		kld.saveKLD("E:\\biyesheji\\data\\KLDresult.txt");
		
		kld.conuntcorrelation(r, sp);//KLDcorrelation.txt            直接归一化协方差
		kld.saveKLD("E:\\biyesheji\\data\\KLDcorrelation.txt");
		
//		kld.countthreshold_num(r, sp, 0.04);//这个只是用来count的，会输出个数，别保存数据
		
		kld.countthreshold_score(r, sp, 0.04);   //threshold.txt     算只有阈值的归一化协方差
		kld.saveKLD("E:\\biyesheji\\data\\threshold.txt");
		
		kld.conuntcorrelation_highpossibility(r,sp,5);    //LDA_correclation_highpossiblity.txt  算前五个最大的LDA归一化协方差
		kld.saveKLD("E:\\biyesheji\\data\\LDA_correclation_highpossiblity.txt");
		
		kld.conuntcorrelation_highpossibility_threshold(r, sp, 5, 0.04);     //LDA_correclation_highpossiblity_threshold.txt   算有阈值的，不满五个取最大的五个  归一化协方差
		kld.saveKLD("E:\\biyesheji\\data\\LDA_correclation_highpossiblity_threshold.txt");
		
//		kld.standardise();
//        double max=0;
//        double temp=0;
//        double tempi=0;
//		for(int i=3;i<66;i++)
//        {
//			kld.conuntcorrelation_highpossibility(r,sp,i);
//			kld.saveKLD("E:\\biyesheji\\data\\LDA_correclation_highpossiblity.txt");
//			Improvement a=new Improvement();
//			a.Load("E:\\biyesheji\\BLEU-doc.scr", "E:\\biyesheji\\data\\LDA_correclation_highpossiblity.txt", "E:\\biyesheji\\data\\result.txt");
//
//        	temp=a.findratio();
//        	if(temp>max)
//        	{
//        		max=temp;
//        		tempi=i;
//        	}
//        	System.out.println(temp+" "+tempi);
//        }
//		System.out.println(temp+" "+tempi);
//		//kld.saveKLD("E:\\biyesheji\\data\\KLDresult_standard.txt");
//		//kld.setScoresfromLDA(kld.standardise(kld.ScoresfromLDA));
	}

}
