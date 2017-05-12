package ReadfromPeople;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadAssessment 
{
	String FileName;
	ArrayList<Assessment> Ass_list;  //存放评分文件数据 mt chinese assessment
	ArrayList<Judge> Judge_list;     //存放每个评委的评分次数
	
	public ArrayList<Assessment> getAss_list() {
		return Ass_list;
	}
	public void setAss_list(ArrayList<Assessment> ass_list) {
		Ass_list = ass_list;
	}
	public ArrayList<Judge> getJudge_list() {
		return Judge_list;
	}
	public void setJudge_list(ArrayList<Judge> judge_list) {
		Judge_list = judge_list;
	}
	public LoadAssessment(String FileName)
	{
		this.FileName=FileName;
		this.Ass_list=new ArrayList<Assessment>(); //初始化
		this.Judge_list=new ArrayList<Judge>();
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public void Load()//读取并结算不同评委给分次数
	{
		try {
	            File file=new File(this.FileName);
	            if(file.isFile() && file.exists())
	            { 
	            	//判断文件是否存在 并读取===============11111111
	                InputStreamReader read = new InputStreamReader(new FileInputStream(file));//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                while((lineTxt = bufferedReader.readLine()) != null){
	                    //System.out.println(lineTxt);
	                    String[] arr = lineTxt.split(",");
	                    Assessment temp=new Assessment("0","0","0","0","0",0,0,"0","0");
	                    temp.setSystemID(arr[0]);
	                    temp.setDocID(arr[1]);
	                    temp.setJudge(arr[2]);
	                    temp.setGoldStandardID(arr[3]);
	                    temp.setSegID(arr[4]);
	                    temp.setFluencyJudge(Double.parseDouble(arr[5]));
	                    temp.setAdequacyJudge(Double.parseDouble(arr[6]));
	                    temp.setCommentonForA(arr[7]);
	                    temp.setJudgeTime(arr[8]);
	                    Ass_list.add(temp);
	                }
	                //======================================11111111
	              //计算不同评委给出1-5分的次数,存入============2222222222
	                ArrayList<Assessment> a=this.Ass_list;
	                String tempname=null;
	                int []count=null;
	                Judge tempjudge=null;
 	                for(int i=0;i<a.size();i++)
	        		{
	                	if(!a.get(i).getJudge().equals(tempname))
	                	{
	                		count=new int[5];
	                		tempjudge=new Judge(tempname,count);
	                		for(int j=0;j<count.length;j++)
	                		{
	                			count[j]=0;
	                		}
	                		tempname=a.get(i).getJudge();
	                		tempjudge.setName(tempname);
	                		for(int k=0;k<a.size();k++)
	                		{
	                			if(a.get(k).getJudge().equals(tempname))
	                			{
	                				count[(int)(a.get(k).getAdequacyJudge()-1)]++;
	                			}
	                		}
	                		tempjudge.setCount_vote(count);
	                		//tempjudge.print();

	                		this.Judge_list.add(tempjudge);
	                	}
	        		}
	                for(int i=0;i<this.Judge_list.size();i++)
	                {
	                	this.Judge_list.get(i).cal_Score_forcount();//调用函数方便后期计算  拆分的时候请注意 不然出NUll Pointer错误！！！！
	                }
	               //=====================================2222222222
		                read.close();
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
	
	public void PrintAsslist()
	{
		//int count=0;
		ArrayList<Assessment> a=this.Ass_list;
		for(int i=0;i<a.size();i++)
		{
			a.get(i).print();
			//count++;
		}
		//System.out.println(count);
	}
	public void PrintJudgelist()
	{
		//int count=0;
		ArrayList<Judge> a=this.Judge_list;
		for(int i=0;i<a.size();i++)
		{
			a.get(i).print_count();
			//count++;
		}
	}
//	public static void main(String[] args)
//	{
//		LoadAssessment la=new LoadAssessment("E://biyesheji//data//mt_chinese_debug.txt");
//		la.Load();
////		la.Printarrylist();
////		la.PrintJudgelist();
//		ScoresforPassage sp = new ScoresforPassage();
//		sp.cal_Score(la.getAss_list(), la.getJudge_list());  //测试成功  能够把同一文章的所有评价拉出
//		sp.sort();
//		sp.PrintPassagelist();
//		//sp.saveinfile("E://biyesheji//data//result.txt");
//	}

}

