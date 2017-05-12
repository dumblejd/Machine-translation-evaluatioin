package Impovement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.DoubleToLongFunction;

import javax.print.attribute.standard.RequestingUserName;

import KLDistance.KLDistance;
import ReadfromPeople.Correlation;
import ReadfromPeople.Passage;
import edu.stanford.nlp.ling.CoreAnnotation;

public class Improvement {

	ArrayList<Passage> BLEU;
	ArrayList<Passage> LDA;
	ArrayList<Passage> HUMAN;
	double x;
	double max;
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public ArrayList<Passage> getBLEU() {
		return BLEU;
	}
	public void setBLEU(ArrayList<Passage> bLEU) {
		BLEU = bLEU;
	}
	public ArrayList<Passage> getLDA() {
		return LDA;
	}
	public void setLDA(ArrayList<Passage> lDA) {
		LDA = lDA;
	}
	public ArrayList<Passage> getHUMAN() {
		return HUMAN;
	}
	public void setHUMAN(ArrayList<Passage> hUMAN) {
		HUMAN = hUMAN;
	}

	public Improvement()
	{
		BLEU=new ArrayList<Passage>();
		LDA=new ArrayList<Passage>();
		HUMAN=new ArrayList<Passage>();
	}
	public void Load(String Bleupath,String Ldapath,String Humanpath)//读取并结算不同评委给分次数
	{
		try {
	            File file=new File(Bleupath);
	            if(file.isFile() && file.exists())
	            {   
	                 Scanner sc=new Scanner(file);//注意这里的参数是FileReader类型的fin  
	                 while(sc.hasNext())
	                 {//如果有内容  
	                	String lineTxt=null;
	                	//System.out.println(lineTxt);
	                	lineTxt=sc.next();
	                	if(!lineTxt.startsWith("E"))
	                	{
	                	lineTxt=sc.next();
	                	}
	                    Passage temppassage=new Passage("0","0",0);
	                    temppassage.setSystemID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setDocID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setScore(Double.parseDouble(lineTxt));
	                    BLEU.add(temppassage);
	                 }
			    }
	            
	            File file2=new File(Ldapath);
	            if(file2.isFile() && file2.exists())
	            {   
	                 Scanner sc=new Scanner(file2);//注意这里的参数是FileReader类型的fin  
	                 while(sc.hasNext())
	                 {//如果有内容  
	                	String lineTxt=null;
	                	//System.out.println(lineTxt);
	                	lineTxt=sc.next();
	                	if(!lineTxt.startsWith("E"))
	                	{
	                	lineTxt=sc.next();
	                	}
	                    Passage temppassage=new Passage("0","0",0);
	                    temppassage.setSystemID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setDocID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setScore(Double.parseDouble(lineTxt));
	                    LDA.add(temppassage);
	                 }
			    }
	            
	            File file3=new File(Humanpath);
	            if(file3.isFile() && file3.exists())
	            {   
	                 Scanner sc=new Scanner(file3);//注意这里的参数是FileReader类型的fin  
	                 while(sc.hasNext())
	                 {//如果有内容  
	                	String lineTxt=null;
	                	//System.out.println(lineTxt);
	                	lineTxt=sc.next();
	                	if(!lineTxt.startsWith("E"))
	                	{
	                	lineTxt=sc.next();
	                	}
	                    Passage temppassage=new Passage("0","0",0);
	                    temppassage.setSystemID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setDocID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setScore(Double.parseDouble(lineTxt));
	                    HUMAN.add(temppassage);
	                 }
			    }
	            else
			    {
			        System.out.println("找不到指定的文件");
			    }
		    } 
		catch (Exception e) 
			{
		        System.out.println("读取文件内容出错");
		        e.printStackTrace();
		    }
 
	}
	public double findratio() //找到能提升correlation的系数    x 和1-x
	{
		ArrayList<Passage> temp=null;
		ArrayList<Passage> temphuman=null;

		double x=0;
		double max=0;
		double finalx=0;
		double y=0;
		double finaly=0;
		double test=0.3191761;
		String qqq=null;
		Correlation co=new Correlation(0);
		for(x=0.01;x<=1;x=x+0.01)
		{

			temp=new ArrayList<Passage>();
			for(int clone=0;clone<BLEU.size();clone++)
			{
				temp.add(new Passage(BLEU.get(clone)));
			}
			
			for(int i=0;i<temp.size();i++)
			{
				temp.get(i).setScore(x*temp.get(i).getScore()+(1-x)*LDA.get(i).getScore());
			}
			KLDistance kl=new KLDistance();
			temp=kl.standardise(temp);
			co.cal_correlation(HUMAN, temp);
			if(co.getCorrelation()>max&&co.getCorrelation()>test)
			{
				max=co.getCorrelation();
				finalx=x;
			    qqq=Double.toString(x)+"  "+Double.toString(1-x)+"  "+Double.toString(max);
			}
		   
		}
        System.out.println(qqq);
        this.x=finalx;
        this.max=max;
		return max;
	}
	public void saveimproved(String savepath)
	{
//	String SystemID; //系统编号
//	String DocID;//译文文档编号
//	double Score;   //计算过程中 存放的是
		ArrayList<Passage> impove=new ArrayList<Passage>();
		Passage passage=null;
		for(int i=0;i<BLEU.size();i++)
		{
			passage=new Passage("","",0);
			passage.setDocID(BLEU.get(i).getDocID());
			passage.setSystemID(BLEU.get(i).getSystemID());
			double score=BLEU.get(i).getScore()*this.x+LDA.get(i).getScore()*(1.0-this.x);
			passage.setScore(score);
			impove.add(passage);
		}
		
		FileWriter fw = null;
		try {
		//如果文件存在，就删除，如果文件不存在，则创建文件
		File f=new File(savepath);
		 if(f.exists())
		{
			f.delete();
		}
		fw = new FileWriter(f, true);
		} catch (IOException e) {
		e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);

		for(int i=0;i<impove.size();i++)
		{
			pw.println(impove.get(i).getSystemID()+" "+impove.get(i).getDocID()+"  "+impove.get(i).getScore());
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
	public static void main(String[] args) 
	{
		Improvement a=new Improvement();
		//a.Load("E:\\biyesheji\\BLEU-doc.scr", "E:\\biyesheji\\data\\LDA_correclation_highpossiblity_threshold.txt", "E:\\biyesheji\\data\\result.txt");
		//a.Load("E:\\biyesheji\\BLEU-doc.scr", "E:\\biyesheji\\data\\KLDcorrelation.txt", "E:\\biyesheji\\data\\result.txt");

		//a.Load("C:\\Users\\JinDi\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\BLEU-doc.scr", "C:\\Users\\JinDi\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\LDA_correlation.txt", "C:\\Users\\JinDi\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\human_evaluation.txt");

		// a.getHUMAN().get(0).print();
//		for(int i=0;i<a.getLDA().size();i++)
//		{
//			a.getLDA().get(i).print();
//		}
//		for(int i=0;i<a.getBLEU().size();i++)
//		{
//			a.getBLEU().get(i).print();
//		}
//		for(int i=0;i<a.getHUMAN().size();i++)
//		{
//			a.getHUMAN().get(i).print();
//		}
//		a.getBLEU().get(0).print();
//		a.getHUMAN().get(0).print();
//        a.getLDA().get(0).print();
        System.out.println(a.findratio());
//        a.getBLEU().get(0).print();
//		a.getHUMAN().get(0).print();
//        a.getLDA().get(0).print();
//		Correlation co=new Correlation(0);
//          co.cal_correlation(a.getHUMAN(), a.getLDA());
//          co.cal_correlation(a.getHUMAN(), a.getBLEU());
	}
	

}
