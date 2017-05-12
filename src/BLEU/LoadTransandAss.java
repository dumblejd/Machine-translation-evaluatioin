package BLEU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//<seg.*?>(.*?)</seg>   提取seg里的  
//docid="(.*?)"  提取docid
//sysid="(.*?)"
public class LoadTransandAss {
	String Filename;
	ArrayList<DataTransandAsss> AllData;
	
	public LoadTransandAss() {
		Filename = null;
		AllData = null;
	}

	public String getFilename() {
		return Filename;
	}


	public void setFilename(String filename) {
		Filename = filename;
	}


	public ArrayList<DataTransandAsss> getAllData() {
		return AllData;
	}


	public void setAllData(ArrayList<DataTransandAsss> allData) {
		AllData = allData;
	}

	public   List<File>  getallfilename(String docspath,List<File> empty)  //得到文件下所有文件
	{
		File dir = new File(docspath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) 
        {
            for (int i = 0; i < files.length; i++) 
            {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) 
                { // 判断是文件还是文件夹
                	getallfilename(files[i].getAbsolutePath(),empty); // 获取文件绝对路径
                } 
                else if (fileName.endsWith("sgm")) 
                { // 判断文件名是否以sgm结尾
                    String strFileName = files[i].getAbsolutePath();
                  //  System.out.println("---" + strFileName);
                    empty.add(files[i]);
                } 
                else 
                {
                    continue;
                }
            }

        }
         return empty;
	}
//	public void read(String docspath) throws IOException
//	{
//		ArrayList<DataTransandAsss> tempLoadTransandAss=new ArrayList<DataTransandAsss>();
//		DataTransandAsss tempdata=null;
//		ArrayList<String> Seg=null;
//		String var1 = "";		
//		Integer total = 0;
//		File fd = new File(docspath);	  	
//				if (fd.isDirectory())
//				{
//					//is directory
//					 String[]  tempList  =  fd.list();      //返回的值是所有的文件或者文件夹  所以下面要重新进行判断
//					 File  temp  =  null;  
//				      for  (int  i  =  0;  i  <  tempList.length;  i++)  
//				      {  
//				           if  (docspath.endsWith(File.separator))   //如果是“//” 结尾直接加上文件  否则添加“//”      用这个方法来完成对docspath下所有文件检索前的路径设置
//				           {  
//				              temp  =  new  File(docspath  +  tempList[i]);  
//				           }   
//				           else  
//				           {  
//				              temp  =  new  File(docspath  +  File.separator  +  tempList[i]);  
//				           }  
//				            if (temp.isDirectory())         //如果重新设置好的路径 是一个目录，跳出本次循环，对tempList的下一个进行路径设置
//				            {
//				            	continue;
//				            }
//				            //如果是设置完路径，此路径代表文件（!temp.isDirectory()） 就进行读取
//					        InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp));//这里我去掉了encoding
//							BufferedReader bfr = new BufferedReader(isrr);
//							String docline = bfr.readLine(); 
//							Seg=new ArrayList<String>();
//							tempdata=new DataTransandAsss("0","0",Seg);
//							//找docid=========================
//							 Pattern pattern = Pattern.compile("docid=\"(.*?)\"");  
//							 Matcher matcher = pattern.matcher(docline);
//							 matcher.find();
//							 tempdata.setDocid(matcher.group());    
//							//================================
//							//找sysid=========================
//							 pattern = Pattern.compile("sysid=\"(.*?)\"");  
//							 matcher = pattern.matcher(docline);
//							 matcher.find();
//							 tempdata.setSystemid(matcher.group()); 
//							//================================'
//							 docline = bfr.readLine();
//							 //读取句子========================
//							 pattern = Pattern.compile("<seg.*?>(.*?)</seg>");  
//							 
//							while (docline != null) 
//							{
//								matcher = pattern.matcher(docline);
//								if(matcher.find())
//								{
//								tempdata.getSeg().add(matcher.group());
//								}
//								docline = bfr.readLine();       //继续下一行
//							}
//							tempLoadTransandAss.add(tempdata);
//							//=======================================
//							bfr.close();
//							isrr.close();
//				       }
//				      this.AllData=tempLoadTransandAss;
//				
//				}
//				else 
//				{
//					System.out.print("you should input a directory name,not a file name");
//					return;
//				}
//	}

	public void read(String docspath) throws IOException
	{
		ArrayList<DataTransandAsss> tempLoadTransandAss=new ArrayList<DataTransandAsss>();
		DataTransandAsss tempdata=null;
		ArrayList<String> Seg=null;
		String var1 = "";		
		Integer total = 0;
		File fd = new File(docspath);	  	
				if (fd.isDirectory())
				{
					List<File> empty=new ArrayList<File>();
					List<File>  filelist =getallfilename(docspath,empty);
					//is directory
					 File  temp  =  null;  
				      for  (int  i  =  0;  i  <  filelist.size();  i++)  
				      {  
				    	    temp=filelist.get(i);
				    	  
					        InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp));//这里我去掉了encoding
							BufferedReader bfr = new BufferedReader(isrr);
							String docline = bfr.readLine(); 
							Seg=new ArrayList<String>();
							tempdata=new DataTransandAsss("0","0",Seg);
							//找docid=========================
							 Pattern pattern = Pattern.compile("docid=\"(.*?)\"");  
							 Matcher matcher = pattern.matcher(docline);
							 matcher.find();
							 tempdata.setDocid(matcher.group(1));    
							//================================
							//找sysid=========================
							 pattern = Pattern.compile("sysid=\"(.*?)\"");  
							 matcher = pattern.matcher(docline);
							 matcher.find();
							 tempdata.setSystemid(matcher.group(1)); 
							//================================'
							 docline = bfr.readLine();
							 //读取句子========================
							 pattern = Pattern.compile("<seg.*?>(.*?)</seg>");  
							 
							while (docline != null) 
							{
								matcher = pattern.matcher(docline);
								if(matcher.find())
								{
								String nospace=	matcher.group(1);
								nospace=nospace.substring(1);//去除开头空格
								nospace = nospace.replaceAll("[^a-zA-Z0-9]", " $0 ");//在符号前后都加空格
								nospace=nospace.replaceAll("\\s+"," ");       //空格都变成一个
								if(nospace.startsWith(" "))//因为符号的缘故  会重新出现 开头有空格
								{
									nospace=nospace.substring(1);
								}
								tempdata.getSeg().add(nospace);
								}
								docline = bfr.readLine();       //继续下一行
							}
							tempLoadTransandAss.add(tempdata);
							//=======================================
							bfr.close();
							isrr.close();
				       }
				      this.AllData=tempLoadTransandAss;
				
				}
				else 
				{
					System.out.print("you should input a directory name,not a file name");
					return;
				}
	}
	
	
}
