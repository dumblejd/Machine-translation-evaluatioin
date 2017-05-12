package ReadfromPeople;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadScoresofMachineAssessment {

	String FileName;
	ArrayList<Passage> Passage_list;
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public ArrayList<Passage> getPassage_list() {
		return Passage_list;
	}
	public void setPassage_list(ArrayList<Passage> passage_list) {
		Passage_list = passage_list;
	}
	public LoadScoresofMachineAssessment(String fileName) {
		FileName = fileName;
		Passage_list =new ArrayList<Passage>();
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
	public void Load()//读取并结算不同评委给分次数
	{
		try {
	            File file=new File(this.FileName);
	            if(file.isFile() && file.exists())
	            {   
	                 Scanner sc=new Scanner(file);//注意这里的参数是FileReader类型的fin  
	                 while(sc.hasNext())
	                 {//如果有内容  
	                	String lineTxt=null;
	                	//System.out.println(lineTxt);
	                	sc.next();
	                	lineTxt=sc.next();
	                    Passage temppassage=new Passage("0","0",0);
	                    temppassage.setSystemID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setDocID(lineTxt);
	                    lineTxt=sc.next();
	                    temppassage.setScore(Double.parseDouble(lineTxt));
	                    Passage_list.add(temppassage);
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
	
	public static void main(String[] args) 
	{
		LoadScoresofMachineAssessment lsfma=new LoadScoresofMachineAssessment("E://biyesheji//BLEU-doc.scr");
		lsfma.Load();
		for(int i=0;i<lsfma.getPassage_list().size();i++)
		{
			lsfma.getPassage_list().get(i).print();
		}

	}

}
