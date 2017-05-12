package ReadfromPeople;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Impovement.Improvement;

public class Correlation 
{
	double Correlation;

	
	public Correlation(double correlation) {
		super();
		Correlation = correlation;
	}


	public double getCorrelation() {
		return Correlation;
	}


	public void setCorrelation(double correlation) {
		Correlation = correlation;
	}

	public void cal_correlation(ArrayList<Passage> human,ArrayList<Passage> machine)
	{
		ArrayList<Passage> h=human;
		ArrayList<Passage> m=machine;
		 List<Double> scoreforsystem = new ArrayList<Double>();
		//��������  ���ⲻ��Ӧ������� ÿ������һһ��Ӧ�����Ǻ��滹�ǽ��еı���ȫ�Ĳ��ң��Է���һ��
		ScoresforPassage sfp=new ScoresforPassage();
		sfp.Passage_list=m;
		sfp.sort();
		m=sfp.getPassage_list();
		//===========================================
		
	//===========���ͬһ��ϵͳ��systemid�����ļ������µ�����	
		ArrayList<Passage> h_sys=new ArrayList<Passage>();
		ArrayList<Passage> m_sys=new ArrayList<Passage>();
		
		String systemid=h.get(0).getSystemID();
		for(int i=0;i<h.size();i++)
		{
			if(!h.get(i).getSystemID().equals(systemid))  
			{		
				scoreforsystem.add(cal_system_correlation(h_sys,m_sys));
				systemid=h.get(i).getSystemID();
				h_sys=new ArrayList<Passage>();
				m_sys=new ArrayList<Passage>();
			}
			for(int j=0;j<m.size();j++)
			{
				if(h.get(i).getSystemID().equals(m.get(j).getSystemID())&&h.get(i).getDocID().equals(m.get(j).getDocID()))
				{
					h_sys.add(h.get(i));
					m_sys.add(m.get(j));
				}
			}
			if(i==(h.size()-1))  //��ĩβ��ʱ��  ����ʣ���
			{
				scoreforsystem.add(cal_system_correlation(h_sys,m_sys));
			}
		}
		
		//����ƽ��ֵ
		double sum=0;
		for(int i=0;i<scoreforsystem.size();i++)
		{
			sum+=scoreforsystem.get(i);
		}
		this.Correlation=sum/scoreforsystem.size();
		System.out.println(this.Correlation);
	}
	
	public double cal_system_correlation(ArrayList<Passage> human_sys,ArrayList<Passage> machine_sys)
	{
		ArrayList<Passage> h=human_sys;
		ArrayList<Passage> m=machine_sys;
		
		int size=h.size();
		double avghuman=0;
		double avgmachine=0;
		double total=0;
		 for(int i=0;i<size;i++)    //�ֱ����ƽ��ֵ
		 {
			 total+=h.get(i).getScore();
		 }
		 avghuman=total/size;
		 total=0;
		 for(int i=0;i<size;i++)    //�ֱ����ƽ��ֵ
		 {
			 total+=m.get(i).getScore();
		 }
		 avgmachine=total/size;
		 //System.out.println("average h="+avghuman+"  avergage m="+avgmachine);
		 double numerator=0;
		 double denominator=0;
		 
		 double denominator_1=0;
		 double denominator_2=0;   
		 
		 double t1=0;
		 double t2=0;
		 for(int i=0;i<size;i++)
		 {
			 for(int j=0;j<size;j++)
			 {
				if(h.get(i).getSystemID().equals(m.get(j).getSystemID())&&h.get(i).getDocID().equals(m.get(j).getDocID()))
				 {
					 numerator+=(h.get(i).getScore()-avghuman)*(m.get(j).getScore()-avgmachine);
					 denominator_1+= Math.pow(h.get(i).getScore()-avghuman,2);
					 denominator_2+= Math.pow(m.get(j).getScore()-avgmachine,2);
				 }
			 }
		 }
		 double result=0;
		 denominator_1=Math.sqrt(denominator_1);
		 denominator_2=Math.sqrt(denominator_2);
		 denominator=denominator_1*denominator_2;
				 
		 result=numerator/denominator;
		 //System.out.println(result);
		return result;
	}



	public static void main(String[] args) {
//		Improvement a=new Improvement();
//		a.Load("E:\\biyesheji\\BLEU-doc.scr", "E:\\biyesheji\\data\\KLDresult_standard.txt", "E:\\biyesheji\\data\\result.txt");
//		Correlation co=new Correlation(0);
//        double count=0;
//		double x=0.99;
//		ArrayList<Passage> temp=a.getBLEU();
//
//		
//			
//		
//			for(int i=0;i<600;i++)
//			{
//				temp.get(i).setScore(x*a.getBLEU().get(i).getScore()+(1-x)*a.getLDA().get(i).getScore());
//			}
//			co.cal_correlation(a.getHUMAN(), temp);
//			count++;
//			double y=co.getCorrelation();
//	   
//		
//         System.out.println(count);
		String s1 = "abc";//��ʼ��һ���ַ�������s1
		  String s2 = s1; //��s1��ʾ���ַ������Ƹ�s2
		  s1="cde";//�ı�s1���ַ�������
		  System.out.println(s1);
		  System.out.println(s2);
	}
}
