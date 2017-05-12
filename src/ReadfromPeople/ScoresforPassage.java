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

public class ScoresforPassage  //ÿƪ���µ�����
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
		//����ļ����ڣ���ɾ��������ļ������ڣ��򴴽��ļ�
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
		for(int i=0;i<this.Passage_list.size();i++)  //���
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
	//==========================//����������===========
	public void sort()        //����������  
	{
		 MyComparator mc = new MyComparator();  
	     Collections.sort(this.Passage_list,mc); 
	}
	
	public class MyComparator implements Comparator {  
		   
	    //�ӿڣ�����ʵ�ֵķ���  
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
		ArrayList<Judge> tempJ= null;//���ҵ�����ί����Ϣ
		Judge judge;   //�ҵ�һ����ί�ķ������
		
		ArrayList<Assessment> listforpassage = null;
		
		String tempsys_doc_id=null;
        //String tempdocid=null;
        String tempseg=null;
        double tempscore=0;
        HashMap hm=new HashMap();    //�����ų��ظ�ֵ
        for (int i = 0; i < A.size(); i++) //�ҳ�һ��ϵͳ�е�һƪ���µĲ�ͬ��
        {
        	String t=A.get(i).getSystemID()+A.get(i).getDocID( );
			if(!t.equals(tempsys_doc_id)&&(!hm.containsKey(t)))   //��E11,AFC20030203.0023  ��Ϊһ���ַ��������ж�����
			{
				tempsys_doc_id=t;
				hm.put(t, t);
				listforpassage=new ArrayList<Assessment>(); //��һ���µı��Ŷ�һƪ�������۵�����       
				for (int k=0;k<A.size();k++)
				{
					if(tempsys_doc_id.equals(A.get(k).getSystemID()+A.get(k).getDocID()))
					{
						listforpassage.add(A.get(k));//�����µ�����������һ���������㣬��Ȼ����ܶ�ѭ��
					}
				}
				double score=0;
//				System.out.println(listforpassage.size());
//				for(int p=0;i<listforpassage.size();p++)
//				{
//					listforpassage.get(p).print();
//				}
				score=cal_passage(listforpassage,J);  //���ñ���ĺ���������ƪ���µķ���
				Passage temppassage=new Passage(A.get(i).getSystemID(),A.get(i).getDocID(),score);
				this.Passage_list.add(temppassage);
			}
		}
        
        
	}
	
	public double cal_passage(ArrayList<Assessment> listforpassage,ArrayList<Judge> Judge_list)//����һƪ���µķ���  �˴�������ȫ��һƪ���µ�����
	{
		ArrayList<Assessment> lfp=listforpassage;
		ArrayList<Judge> judge=Judge_list;
		int lengthofpassage=0;
		//====================�ҵ���ƪ���µ��ܾ���==============================
		int segplusone=1;  //����seg+1                                                           �ܻ���+1
		String length="S"+segplusone;
		for (int i = 0; i < lfp.size(); i++)   //�����ܹ��м��仰
		{
			while(lfp.get(i).getSegID().equals(length))
			{
				segplusone++;
				length="S"+(segplusone);
			}
		}
		//======================��ȡ������ί������===============================
		String tempname=null;
		ArrayList name=new ArrayList();  //�����ί���ֵ�����                      ��ί��������
		for (int i = 0; i < lfp.size(); i++)     //��ȡ������ί������
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
		//======================�ҵ���ί���� �� Judge�е�λ��==========================�������Ҫ���ļ��ǰ��������е�(����ȡ����ļ����ܸĶ�)
		int[] index_name_inJudge=new int[name.size()];  //��ί���� ��Judge�е�λ��
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
		//==================���ķ����й�ʽ5.1�������=======================
		
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
		double scoreforseg[]=new double[segplusone-1];  //���ÿ���������
		
		//Judge��Ա��double [] Score_forcount=new double[6];    //0-4 �ŵ��� M<x����  5�ŵ������ִ�������
		double Numerator;  //����
		double Denominator; //��ĸ
		
		for (int i = 1; i < segplusone; i++) 
		{
			int q=0;
			Numerator=0;
			Denominator=0;	
			for (int j= 0; j < lfp.size(); j++)
			{
				if(lfp.get(j).getSegID().equals("S"+i))
				{
					int temp=(int)lfp.get(j).getAdequacyJudge();//����
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
		
		//����=scoreforseg/�ܾ���
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
             Scanner sc=new Scanner(file2);//ע������Ĳ�����FileReader���͵�fin  
             while(sc.hasNext())
             {//���������  
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
