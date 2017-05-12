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
//<seg.*?>(.*?)</seg>   ��ȡseg���  
//docid="(.*?)"  ��ȡdocid
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

	public   List<File>  getallfilename(String docspath,List<File> empty)  //�õ��ļ��������ļ�
	{
		File dir = new File(docspath);
        File[] files = dir.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
        if (files != null) 
        {
            for (int i = 0; i < files.length; i++) 
            {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) 
                { // �ж����ļ������ļ���
                	getallfilename(files[i].getAbsolutePath(),empty); // ��ȡ�ļ�����·��
                } 
                else if (fileName.endsWith("sgm")) 
                { // �ж��ļ����Ƿ���sgm��β
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
//					 String[]  tempList  =  fd.list();      //���ص�ֵ�����е��ļ������ļ���  ��������Ҫ���½����ж�
//					 File  temp  =  null;  
//				      for  (int  i  =  0;  i  <  tempList.length;  i++)  
//				      {  
//				           if  (docspath.endsWith(File.separator))   //����ǡ�//�� ��βֱ�Ӽ����ļ�  ������ӡ�//��      �������������ɶ�docspath�������ļ�����ǰ��·������
//				           {  
//				              temp  =  new  File(docspath  +  tempList[i]);  
//				           }   
//				           else  
//				           {  
//				              temp  =  new  File(docspath  +  File.separator  +  tempList[i]);  
//				           }  
//				            if (temp.isDirectory())         //����������úõ�·�� ��һ��Ŀ¼����������ѭ������tempList����һ������·������
//				            {
//				            	continue;
//				            }
//				            //�����������·������·�������ļ���!temp.isDirectory()�� �ͽ��ж�ȡ
//					        InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp));//������ȥ����encoding
//							BufferedReader bfr = new BufferedReader(isrr);
//							String docline = bfr.readLine(); 
//							Seg=new ArrayList<String>();
//							tempdata=new DataTransandAsss("0","0",Seg);
//							//��docid=========================
//							 Pattern pattern = Pattern.compile("docid=\"(.*?)\"");  
//							 Matcher matcher = pattern.matcher(docline);
//							 matcher.find();
//							 tempdata.setDocid(matcher.group());    
//							//================================
//							//��sysid=========================
//							 pattern = Pattern.compile("sysid=\"(.*?)\"");  
//							 matcher = pattern.matcher(docline);
//							 matcher.find();
//							 tempdata.setSystemid(matcher.group()); 
//							//================================'
//							 docline = bfr.readLine();
//							 //��ȡ����========================
//							 pattern = Pattern.compile("<seg.*?>(.*?)</seg>");  
//							 
//							while (docline != null) 
//							{
//								matcher = pattern.matcher(docline);
//								if(matcher.find())
//								{
//								tempdata.getSeg().add(matcher.group());
//								}
//								docline = bfr.readLine();       //������һ��
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
				    	  
					        InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp));//������ȥ����encoding
							BufferedReader bfr = new BufferedReader(isrr);
							String docline = bfr.readLine(); 
							Seg=new ArrayList<String>();
							tempdata=new DataTransandAsss("0","0",Seg);
							//��docid=========================
							 Pattern pattern = Pattern.compile("docid=\"(.*?)\"");  
							 Matcher matcher = pattern.matcher(docline);
							 matcher.find();
							 tempdata.setDocid(matcher.group(1));    
							//================================
							//��sysid=========================
							 pattern = Pattern.compile("sysid=\"(.*?)\"");  
							 matcher = pattern.matcher(docline);
							 matcher.find();
							 tempdata.setSystemid(matcher.group(1)); 
							//================================'
							 docline = bfr.readLine();
							 //��ȡ����========================
							 pattern = Pattern.compile("<seg.*?>(.*?)</seg>");  
							 
							while (docline != null) 
							{
								matcher = pattern.matcher(docline);
								if(matcher.find())
								{
								String nospace=	matcher.group(1);
								nospace=nospace.substring(1);//ȥ����ͷ�ո�
								nospace = nospace.replaceAll("[^a-zA-Z0-9]", " $0 ");//�ڷ���ǰ�󶼼ӿո�
								nospace=nospace.replaceAll("\\s+"," ");       //�ո񶼱��һ��
								if(nospace.startsWith(" "))//��Ϊ���ŵ�Ե��  �����³��� ��ͷ�пո�
								{
									nospace=nospace.substring(1);
								}
								tempdata.getSeg().add(nospace);
								}
								docline = bfr.readLine();       //������һ��
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
