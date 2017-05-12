package ReadfromPeople;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class ScoresforPassage  //每篇文章的评分
{
	ArrayList<Passage> Passage_list;
	
	public ScoresforPassage(ArrayList<Passage> passage_list) {
		Passage_list = passage_list;
	}

	public ScoresforPassage() {
		Passage_list=new ArrayList<Passage>();
	}
	
	public ArrayList<Passage> getPassage_list() {
		return Passage_list;
	}

	public void setPassage_list(ArrayList<Passage> passage_list) {
		Passage_list = passage_list;
	}

	public void PrintPassagelist()
	{
		ArrayList<Passage> a=this.Passage_list;
		for(int i=0;i<a.size();i++)
		{
			a.get(i).print();
			//count++;
		}
	}
	public void saveinfile(String filename)
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
		ArrayList<Passage> a=new ArrayList<Passage>();
		for(int i=0;i<this.Passage_list.size();i++)  //深拷贝
		{
			a.add(new Passage(this.Passage_list.get(i)));
		}
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
	//==========================//对象类排序===========
	public void sort()        //对象类排序  
	{
		 MyComparator mc = new MyComparator();  
	     Collections.sort(this.Passage_list,mc); 
	}
	
	public class MyComparator implements Comparator {  
		   
	    //接口，必须实现的方法  
	    public int compare(Object o1, Object o2) {  
	    	Passage p1 = (Passage) o1;  
	    	Passage p2 = (Passage) o2;  
	    	String sa=p1.getSystemID().substring(1);
	    	int q=p1.getDocID().charAt(0);
	    	sa=sa+q;
	    	sa=sa+p1.getDocID().substring(3);
	    	String ba=p2.getSystemID().substring(1);
	    	int p=p2.getDocID().charAt(0);
	    	ba=ba+p;
	    	ba=ba+p2.getDocID().substring(3);
	    	double a=Double.parseDouble(sa);
	    	double b=Double.parseDouble(ba);
	        if (a > b)  
	            return 1;  
	        else if (b > a)  
	            return -1;  
	        else  
	            return 0;  
	    }  
	}  
	//======================================================
	public void cal_Score(ArrayList<Assessment> Ass_list,ArrayList<Judge> Judge_list)
	{
		ArrayList<Assessment> A=Ass_list;
		ArrayList<Judge> J=Judge_list;
		ArrayList<Judge> tempJ= null;//存找到的评委的信息
		Judge judge;   //找到一个评委的分数存的
		
		ArrayList<Assessment> listforpassage = null;
		
		String tempsys_doc_id=null;
        //String tempdocid=null;
        String tempseg=null;
        double tempscore=0;
        HashMap hm=new HashMap();    //用来排除重复值
        for (int i = 0; i < A.size(); i++) //找出一个系统中的一篇文章的不同人
        {
        	String t=A.get(i).getSystemID()+A.get(i).getDocID( );
			if(!t.equals(tempsys_doc_id)&&(!hm.containsKey(t)))   //把E11,AFC20030203.0023  作为一个字符串你来判断文章
			{
				tempsys_doc_id=t;
				hm.put(t, t);
				listforpassage=new ArrayList<Assessment>(); //建一个新的表存放对一篇文章评价的数据       
				for (int k=0;k<A.size();k++)
				{
					if(tempsys_doc_id.equals(A.get(k).getSystemID()+A.get(k).getDocID()))
					{
						listforpassage.add(A.get(k));//放入新的链表来给另一个函数计算，不然会叠很多循环
					}
				}
				double score=0;
//				System.out.println(listforpassage.size());
//				for(int p=0;i<listforpassage.size();p++)
//				{
//					listforpassage.get(p).print();
//				}
				score=cal_passage(listforpassage,J);  //调用本类的函数计算这篇文章的分数
				Passage temppassage=new Passage(A.get(i).getSystemID(),A.get(i).getDocID(),score);
				this.Passage_list.add(temppassage);
			}
		}
        
        
	}
	
	public double cal_passage(ArrayList<Assessment> listforpassage,ArrayList<Judge> Judge_list)//计算一篇文章的分数  此处链表中全是一篇文章的数据
	{
		ArrayList<Assessment> lfp=listforpassage;
		ArrayList<Judge> judge=Judge_list;
		int lengthofpassage=0;
		//====================找到这篇文章的总句数==============================
		int segplusone=1;  //代表seg+1                                                           总话数+1
		String length="S"+segplusone;
		for (int i = 0; i < lfp.size(); i++)   //查找总共有几句话
		{
			while(lfp.get(i).getSegID().equals(length))
			{
				segplusone++;
				length="S"+(segplusone);
			}
		}
		//======================获取所有评委的名字===============================
		String tempname=null;
		ArrayList name=new ArrayList();  //存放评委名字的链表                      评委名字链表
		for (int i = 0; i < lfp.size(); i++)     //获取所有评委的名字
		{
			for (int j=1;j<segplusone-1;j++)
			{
				if(!lfp.get(i).getJudge().equals(tempname))
				{
					tempname=lfp.get(i).getJudge();
					name.add(tempname);
				}
			}
		}
		//======================找到评委名字 在 Judge中的位置==========================这个函数要求文件是按名字排列的(即读取后的文件不能改动)
		int[] index_name_inJudge=new int[name.size()];  //评委名字 在Judge中的位置
		int k=0;
		for(int i = 0; i <name.size(); i++) 
		{
			for(int j=0;j<judge.size();j++)
			{
				if(name.get(i).equals(judge.get(j).getName()))
				{
					index_name_inJudge[k]=j;
					k++;
				}
			}
		}
		//==================外文翻译中公式5.1计算分数=======================
		
//		for(int i=0;i<judge.size();i++)
//		{
//			judge.get(i).print_count();
//			
//		}
//		System.out.println("***************");
//		for(int i=0;i<6;i++)
//		{
//		System.out.println(judge.get(1).getScore_forcount()[i]);
//		}
//		System.out.println("***************");
//		System.out.println("size"+lfp.size());
//		System.out.println("***************");
//		for(int i=0;i<lfp.size();i++)
//		{
//			lfp.get(i).print();
//			//count++;
//		}
//		System.out.println("***************");
		double scoreforseg[]=new double[segplusone-1];  //存放每句的总评分
		
		//Judge成员：double [] Score_forcount=new double[6];    //0-4 放的是 M<x次数  5放的是评分次数总数
		double Numerator;  //分子
		double Denominator; //分母
		
		for (int i = 1; i < segplusone; i++) 
		{
			int q=0;
			Numerator=0;
			Denominator=0;	
			for (int j= 0; j < lfp.size(); j++)
			{
				if(lfp.get(j).getSegID().equals("S"+i))
				{
					int temp=(int)lfp.get(j).getAdequacyJudge();//分数
					//System.out.println(judge.get(0).getScore_forcount()[3]);
					Numerator+=judge.get(index_name_inJudge[q]).getScore_forcount()[temp-1];
					//System.out.println(Numerator);
					Numerator+=((double)judge.get(index_name_inJudge[q]).getCount_vote()[temp-1])/2;
					Denominator+=judge.get(index_name_inJudge[q]).getScore_forcount()[5];
					q++;
				}
			}
			scoreforseg[i-1]=Numerator/Denominator;
		}
		
		//分数=scoreforseg/总句数
		double sum=0;
		double score=0;
		for (int i = 0; i < scoreforseg.length; i++) 
		{
			sum+=scoreforseg[i];
		}
		score=sum/scoreforseg.length;
		
		return score;
	}

	public void loadonefile(String evaluationpath) throws FileNotFoundException
	{
		File file2=new File(evaluationpath);
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
                this.Passage_list.add(temppassage);
             }
	    }
	}

	
}
