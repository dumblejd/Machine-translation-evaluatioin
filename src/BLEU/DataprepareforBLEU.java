package BLEU;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DataprepareforBLEU 
{
	ArrayList<DataTransandAsss> Translation;
	ArrayList<DataTransandAsss> Answer;
	 
	public ArrayList<DataTransandAsss> getTranslation() {
		return Translation;
	}

	public void setTranslation(ArrayList<DataTransandAsss> translation) {
		Translation = translation;
	}

	public ArrayList<DataTransandAsss> getAnswer() {
		return Answer;
	}

	public void setAnswer(ArrayList<DataTransandAsss> answer) {
		Answer = answer;
	}

	
	public DataprepareforBLEU ()
	{
		this.Translation=new ArrayList<DataTransandAsss>();
		this.Answer=new ArrayList<DataTransandAsss>();
	}
	
	public DataprepareforBLEU(int Trans_start_index,int Trans_end_index,int Answer_start_index,int Answer_end_index,ArrayList<DataTransandAsss> alldata)
	{
		this.Translation=new ArrayList<DataTransandAsss>();
		this.Answer=new ArrayList<DataTransandAsss>();
		String temp;
		for(int i=0;i<alldata.size();i++)
		{
			for(int j=Trans_start_index;j<=Trans_end_index;j++)
			{
				
				if(j<=9&&j>=1)
				{
				  temp="0"+Integer.toString(j);
				}
				else
				{
					temp=Integer.toString(j);
				}
				if(alldata.get(i).getSystemid().equals("E"+temp))
				{
					Translation.add(alldata.get(i));
				}
			}
		}
		for(int i=0;i<alldata.size();i++)
		{
			for(int j=Answer_start_index;j<=Answer_end_index;j++)
			{
				if(j<=9&&j>=1)
				{
				  temp="0"+Integer.toString(j);
				}
				else
				{
					temp=Integer.toString(j);
				}
				if(alldata.get(i).getSystemid().equals("E"+temp))
				{
					Answer.add(alldata.get(i));
				}
			}
		}
	}
	
	public void Ngramdata(ArrayList<DataTransandAsss> TransorAnswer,int ngram)
	{
		//提取n元
		for(int i=0;i<TransorAnswer.size();i++)
		{
			for(int j=0;j<TransorAnswer.get(i).getSeg().size();j++)
			{
				String []temp =TransorAnswer.get(i).getSeg().get(j).split("\\s+");
				TransorAnswer.get(i).getSeperate().add(temp);
			}
		}
		for(int i=0;i<TransorAnswer.size();i++)
		{
			for(int j=0;j<TransorAnswer.get(i).getSeperate().size();j++)  //getSeperate().size() 一个文件有几句话
			{
				int count=TransorAnswer.get(i).getSeperate().get(j).length;  //一句话中的单词数
				String []Ngramdata=new String[count+1-ngram];   //n元拆分后的长度  并new
				for(int k=0;k<Ngramdata.length;k++)
				{
					Ngramdata[k]=TransorAnswer.get(i).getSeperate().get(j)[k];
					for(int q=1;q<ngram;q++)
					{
					Ngramdata[k]+=TransorAnswer.get(i).getSeperate().get(j)[k+q];
					}
				}
				TransorAnswer.get(i).getNgramdata().add(Ngramdata);
			}
				
		}
		
	}
	public void Createcontentfile(ArrayList<DataTransandAsss> translation,String filepath) throws IOException
	{
		//在文件夹下放lda用的数据，一行一句，一篇文章一个txt
	  String sysid=null;
	  File file=null;
	  String filepath_content=null;
	  String pathtemp=null;
	  for(int i=0;i<translation.size();i++)
	  {
		  //创建文件夹
		  if(!translation.get(i).getSystemid().equals(sysid))
		  {
			  sysid= translation.get(i).getSystemid();
		      if  (filepath.endsWith(File.separator))  {  
		    	  file  =  new  File(filepath  +  sysid);  
		    	  pathtemp=filepath  +  sysid;
	           }  
	           else  {  
	        	   file  =  new  File(filepath  +  File.separator  +  sysid); 
	        	   pathtemp=filepath  +  File.separator  +  sysid;
	           } 
		      file.mkdir();
		  }
		  filepath_content=pathtemp+File.separator+translation.get(i).getDocid()+".txt";//创建具体文件的路劲
		  
		  OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(filepath_content),"ASCII");
		  BufferedWriter bw = new BufferedWriter(osw);
		  
		  for(int j=0;j<translation.get(i).getSeg().size();j++)
		  {
			  bw.write(translation.get(i).getSeg().get(j));
			  if(j+1==translation.get(i).getSeg().size())
			  {
			  }
			  else
			  {
			  bw.newLine();
			  }
		  }
		  bw.close();
		  osw.close();
	  }
	}
}
