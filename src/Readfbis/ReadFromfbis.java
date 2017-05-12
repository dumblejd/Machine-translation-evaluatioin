package Readfbis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BLEU.DataTransandAsss;
import BLEU.DataprepareforBLEU;
import BLEU.LoadTransandAss;

public class ReadFromfbis 
{
	boolean containsOutAscii(String str){
		char[] arr=str.toCharArray();
		for(int i=0;i<arr.length;i++)
		{
			int num=(int)arr[i];
			if (num<0 || num>127)
			{
				return true;
			}
		}
		return false;		
	}
	
	public void readfromfbis(String docspath,String savepath) throws IOException
	{
		ArrayList<DataTransandAsss> tempLoadTransandAss=new ArrayList<DataTransandAsss>();
		DataTransandAsss tempdata=null;
		ArrayList<String> Seg=null;
		String var1 = "";		
		Integer total = 0;
		File fd = new File(docspath);	  	
        InputStreamReader isrr=new InputStreamReader(new FileInputStream(fd),"ASCII");//这里我去掉了encoding
		BufferedReader bfr = new BufferedReader(isrr);
		
		OutputStreamWriter osw=null;
		
		String temppath;
		
		String docline = bfr.readLine(); 
		docline = bfr.readLine();
		Seg=new ArrayList<String>();	
		 Pattern pattern = Pattern.compile("doc id=\"(.*?)\"");  
		 Matcher matcher = pattern.matcher(docline);
		 
		 Pattern patternstop=Pattern.compile("</doc>"); 
		 Matcher matcherstop=patternstop.matcher(docline);
		 
		 
		 
		while (docline != null) 
		{
			matcher = pattern.matcher(docline);
			if(matcher.find())
			{
				temppath=savepath+File.separator+matcher.group(1);
				osw=new OutputStreamWriter(new FileOutputStream(temppath)); //发现就创建
				
				String stop=bfr.readLine();
				matcherstop=patternstop.matcher(stop);
				while(!matcherstop.find())
				{
					Pattern patternread=Pattern.compile("<trg>(.*?)</trg>");   //读取句子
					 Matcher matcherread=patternread.matcher(stop);
					 if(matcherread.find())
					 {
					 String word=matcherread.group(1);
					 word=word.substring(1);  //去除开头空格
					 String []deletestrange=word.split(" ");
					 for(int q=0;q<deletestrange.length;q++)
					 {
						 if(!containsOutAscii(deletestrange[q].toLowerCase()))
						 {
							 osw.write(deletestrange[q]+" ");
						 }
					 }
					 osw.write("\r\n");   //写入并换行
				     }
					 stop=bfr.readLine();
					 matcherstop=patternstop.matcher(stop);
				}
				osw.close();
			}
			docline = bfr.readLine();       //继续下一行
		}
		
		//=======================================
		bfr.close();
		isrr.close();
}
	
	public static void main(String[] args) throws IOException
	{
		ReadFromfbis a= new ReadFromfbis();
		a.readfromfbis("E://biyesheji//fbis.xml", "E://biyesheji//data//fbis//fbis_content");
	}
}
