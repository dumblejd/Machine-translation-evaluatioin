package topicmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDA_PrepareLDADataTxt {
	private String Encoding="ASCII";
	
	public void prePareLDAByFilelists(int filecount,String fileDir,String filelists,String outputfile) throws IOException{
		//filecount 是filedir文件夹下的(过滤了停用词的)文档个数, 这是根据filelists去生成lda数据文件，用于测试topic model数据的场合。
		//根据filelists的目的是后面查看测试文件的topic的方便，因为后面model后的文档次序是跟输入的lda.txt的次序相关的
		//outputDir是为了把写出文件的文件夹传入进去，为了能在该文件夹底下自动生成一个filelists文件，记录lda的文件次序
		//outputfile是输出文件名，是存放要真正构建lda模型的准备数据.
		//这个程序是把一个文件夹下的所有过滤掉停用词的文档并成一个lda文件
		Comparator c = new Comparator() {
			public int compare(Object el1, Object el2) {
				String key1 = el1.toString().toLowerCase() + "_"
						+ el1.toString();
				String key2 = el2.toString().toLowerCase() + "_"
						+ el2.toString();
				return key1.compareTo(key2);
			}
		};
		
		File ff = new File(fileDir);
		if (ff.isDirectory()){	
			//if (!outputDir.endsWith(File.separator)) outputDir+=File.separator;
			if (!fileDir.endsWith(File.separator)) fileDir+=File.separator;
			
			InputStreamReader iswfilelists=new InputStreamReader(new FileInputStream(filelists),this.Encoding);
			BufferedReader brfilelists = new BufferedReader(iswfilelists);
			OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(outputfile),this.Encoding);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(Integer.toString(filecount));//先写入文档总数
			bw.newLine();
			
			String linestr="";
			
			while((linestr=brfilelists.readLine())!=null)
			{	
			//**********************************
			//然后把每个文档的过滤过的		    	
		    	
			 //  File temp  =  new  File(fileDir  +  linestr+".nostop");   
			 File temp  =  new  File(fileDir  +  linestr);   
		
			  
		    
			  //FileWriter fw = new FileWriter(outputDir + temp.getName() + ".lda.txt");		
			    
				InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp),this.Encoding);
				BufferedReader fmfi = new BufferedReader(isrr);
				
				ArrayList<String> allnostop = new ArrayList();
				
				String linenostop = fmfi.readLine();
					
				if (this.Encoding.compareTo("ASCII")==0)   
				{
					//add by gong 2010.10.13, add ignore upcase and lowcase and add stemming for english 
					//Stemmer smer=new Stemmer();
					while (linenostop != null) {	
						String[] words = linenostop.split(" ");										
						for (int j = 0; j < words.length; j++) {							
							String tempstemmer=words[j];	
							//tempstemmer=smer.stem(tempstemmer); // this do stemmer, but this stemmer seemed too strange. so ignored
							if(!isdigit(tempstemmer))   //add by gong 2010.10.11,remove digit number
								allnostop.add(tempstemmer.toLowerCase());
						}
						linenostop = fmfi.readLine();				
					}
					
				}
				else
				{
					//chinese words not require low case and stemmer
					while (linenostop != null) {	
						String[] words = linenostop.split(" ");
										
						for (int j = 0; j < words.length; j++) {							
							allnostop.add(words[j]);
						}
						linenostop = fmfi.readLine();				
					}
				}
				
				fmfi.close();
				isrr.close();
				String[] a = (String[]) allnostop.toArray(new String[allnostop.size()]);//链表转换为数组
				Arrays.sort(a, c);
				for (int j = 0; j < a.length; j++) 
				{
					bw.write(a[j] + " ");
				}					
				bw.newLine();
				allnostop.clear();
			//***************************************
		    }
		    brfilelists.close();		  
		    iswfilelists.close();
		    bw.flush();
			bw.close();
			osw.close();	
		}
		
	}
	public void prePareLDA(int filecount,String fileDir,String outputDir,String outputfile) throws IOException {
		//filecount 是filedir文件夹下的文档个数, 这是自动生成filelists的版本，用于训练topic model的场合
		//outputDir是为了把写出文件的文件夹传入进去，为了能在该文件夹底下自动生成一个filelists文件，记录lda的文件次序
		//outputfile是输出文件名，是存放要真正构建lda模型的准备数据.
		//这个程序是把一个文件夹下的所有过滤掉停用词的文档并成一个lda文件
		Comparator c = new Comparator() {
			public int compare(Object el1, Object el2) {
				String key1 = el1.toString().toLowerCase() + "_"
						+ el1.toString();
				String key2 = el2.toString().toLowerCase() + "_"
						+ el2.toString();
				return key1.compareTo(key2);
			}
		};
		Comparator cfilename = new Comparator() {                            //比大小排序
			public int compare(Object el1, Object el2) {
				/*String key1 = el1.toString().toLowerCase() + "_"
						+ el1.toString();
				String key2 = el2.toString().toLowerCase() + "_"
						+ el2.toString();
						*/
				String key1=el1.toString().toLowerCase();
				String key2=el2.toString().toLowerCase();
				return key1.compareTo(key2);
			}
		};
		File ff = new File(fileDir);
		if (ff.isDirectory())
		{	
			if (!outputDir.endsWith(File.separator)) outputDir+=File.separator;
			OutputStreamWriter oswfilelists=new OutputStreamWriter(new FileOutputStream(outputDir+"filelists.txt"),this.Encoding);
			BufferedWriter bwfilelists = new BufferedWriter(oswfilelists);
			OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(outputDir+outputfile),this.Encoding);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(Integer.toString(filecount));//先写入文档总数
			bw.newLine();
			
			String[]  tempList  =  ff.list();  
			
			
			//modified by gong on 2010.9.29
			Arrays.sort(tempList,cfilename);//按照文件名排序，否则后面我不知道哪个文件是哪个文件，因为后面生成的model中的
			//*.theta中是按照lda前期准备的数据的次序来表示哪个是第一个文件，哪个是第二个文件的。
		    
			
			 File  temp  =  null;  
			 if (!fileDir.endsWith(File.separator)) fileDir+= File.separator; 
			//然后把每个文档的过滤过的
		    for  (int  i  =  0;  i  <  tempList.length;  i++)  {  
		    	bwfilelists.write(tempList[i]+'\n');	//record the dealing order of files
		    	
			    temp  =  new  File(fileDir  +  tempList[i]);      
			
		    
			  //FileWriter fw = new FileWriter(outputDir + temp.getName() + ".lda.txt");		
			    
				InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp),this.Encoding);
				BufferedReader fmfi = new BufferedReader(isrr);
				
				ArrayList<String> allnostop = new ArrayList();
				
				String linenostop = fmfi.readLine();
					
				if (this.Encoding.compareTo("ASCII")==0)
				{
					//add by gong 2010.10.13, add ignore upcase and lowcase and add stemming for english 
					//Stemmer smer=new Stemmer();
					while (linenostop != null) {	
						String[] words = linenostop.split(" ");										
						for (int j = 0; j < words.length; j++) {							
							String tempstemmer=words[j];	
							//tempstemmer=smer.stem(tempstemmer); // this do stemmer, but this stemmer seemed too strange. so ignored
							if(!isdigit(tempstemmer))   //add by gong 2010.10.11,remove digit number
								allnostop.add(tempstemmer.toLowerCase());
						}
						linenostop = fmfi.readLine();				
					}
					
				}
				else
				{
					//chinese words not require low case and stemmer
					while (linenostop != null) {	
						String[] words = linenostop.split(" ");
										
						for (int j = 0; j < words.length; j++) {							
							allnostop.add(words[j]);
						}
						linenostop = fmfi.readLine();				
					}
				}
				fmfi.close();
				isrr.close();
				String[] a = (String[]) allnostop.toArray(new String[allnostop.size()]);
				//Arrays.sort(a, c);
				for (int j = 0; j < a.length; j++) {
					bw.write(a[j] + " ");
				}					
				bw.newLine();
				allnostop.clear();
			
		    }
		    bwfilelists.close();
		    bwfilelists.close();
		    oswfilelists.close();
		    bw.flush();
			bw.close();
			osw.close();	
		}
	}
	public boolean isdigit(String str){		
		//Pattern pattern = Pattern.compile("[0-9]+\\,?\\.?[0-9]+");//remove 100 100,000 10.8 such digit	
		Pattern pattern = Pattern.compile("([0-9]+\\-?\\.?\\,?)*");//remove  3088-2504-1628 0691-0007-6638-2653-5388-2654
		Matcher isNum = pattern.matcher(str);		
		if( !isNum.matches() )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public void prePareLDAOnefile(String fileDir,String outputfile) throws IOException {
		
		//这个程序是把一个文件里内容生成一个lda文件
		Comparator c = new Comparator() {
			public int compare(Object el1, Object el2) {
				String key1 = el1.toString().toLowerCase() + "_"
						+ el1.toString();
				String key2 = el2.toString().toLowerCase() + "_"
						+ el2.toString();
				return key1.compareTo(key2);
			}
		};
		
		File ff = new File(fileDir);
		if(ff.isFile()){
			OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(outputfile),this.Encoding);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(Integer.toString(1));//先写入文档总数
			bw.newLine();
			InputStreamReader isrr=new InputStreamReader(new FileInputStream(ff),this.Encoding);
			BufferedReader fmfi = new BufferedReader(isrr);
			
			ArrayList<String> allnostop = new ArrayList();
			
			String linenostop = fmfi.readLine();
								
			while (linenostop != null) 
			{	
				String[] words = linenostop.split(" ");
				for (int j = 0; j < words.length; j++) 
				{
					allnostop.add(words[j]);
				}
				linenostop = fmfi.readLine();				
			}
			fmfi.close();
			isrr.close();
			String[] a = (String[]) allnostop.toArray(new String[allnostop.size()]);
			Arrays.sort(a, c);
			for (int j = 0; j < a.length; j++) {
				bw.write(a[j] + " ");
			}					
			bw.newLine();
			allnostop.clear();
			bw.flush();
			bw.close();
			osw.close();
		}
		else
		{
			System.out.print("this function only can deal one file,not a directory");
		} 
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//在用这个功能之前应该先使用LDA_FilterStopWordl类得到过滤过停用词的方法，再使用
		//它生成的数据来为LDA生成准备数据，最后采用lda程序生成lda的topic model
		LDA_PrepareLDADataTxt ldaf=new LDA_PrepareLDADataTxt();
		//ldaf.prePareLDAOnefile("G:\\语料库\\2005年863机译翻译评测数据\\testfortp\\test.canyin.src.nostop", "G:\\语料库\\2005年863机译翻译评测数据\\testfortp\\lda.canyin");
		//ldaf.inferTm("G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cntp\\canyin\\model\\", "model-final","lda.canyin.test");
		//start to prepare lda
		
		/*		
		ldaf.prePareLDAOnefile("G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cnnostop\\canyin_seg.nostop", "G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cntp\\canyin\\lda.canyin");
		ldaf.useLDA("G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cntp\\canyin\\lda.canyin","G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cntp\\canyin\\model");
			
		*/	
		//生成中文的lda文件		
		// for training
			//ldaf.prePareLDA(50,"G:\\语料库\\nist\\nist2005\\src\\srcnostop\\AFC" , "G:\\语料库\\nist\\nist2005\\src\\srcnostop\\AFC.lda.txt");
		//ldaf.prePareLDA(50,"G:\\语料库\\nist\\nist2005\\src\\srcnostop\\XIN" , "G:\\语料库\\nist\\nist2005\\src\\srcnostop\\XIN.lda.txt");
		
		
		//********//生成英文的lda文件,生成一个list的文件，还有一个存放了所有文档的文件，一行一个文件
		//ldaf.prePareLDA(5498,"E:\\biyesheji\\data\\fbis\\fbis_content_nostop" , "E:\\biyesheji\\data\\fbis\\fbis_content_nostop_filelist","passageoneline.nostop");

		//ldaf.prePareLDA(5498,"E:\\biyesheji\\data\\fbis\\fbis_stop_withstem" , "E:\\biyesheji\\data\\fbis\\fibs_content_nostop_stem_filelist","passageoneline.nostop");
//		File file=new File("E:\\biyesheji\\data\\MT_nostop_withstem");
//		File[] tempList = file.listFiles();
//		
//		for(int i=0;i<tempList.length;i++)
//		{
//			   if (tempList[i].isDirectory()) {
//			    File file2=new File("E:\\biyesheji\\data\\MT_nostop_withstem_list\\"+tempList[i].getName());
//			    file2.mkdir();
//			    String t="E:\\biyesheji\\data\\MT_nostop_withstem\\"+tempList[i].getName();
//			    System.out.println(t);
//				ldaf.prePareLDA(100,t , "E:\\biyesheji\\data\\MT_nostop_withstem_list\\"+tempList[i].getName(),"passageoneline.nostop");
//			   }
//		}
		
		//for test
		try {
			
			//ldaf.prePareLDAByFilelists(5498, "E:\\biyesheji\\data\\fbis\\fbis_content_nostop\\","E:\\biyesheji\\data\\fbis\\fbis_content_nostop_filelist\\filelists.txt","E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda_en_all_fortrain.dat");

			//ldaf.prePareLDAByFilelists(5498, "E:\\biyesheji\\data\\fbis\\fbis_stop_withstem\\","E:\\biyesheji\\data\\fbis\\fibs_content_nostop_stem_filelist\\filelists.txt","E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat");

			File file3=new File("E:\\biyesheji\\data\\MT_nostop_withstem");
			File[] tempList2 = file3.listFiles();
			
			for(int i=0;i<tempList2.length;i++)
			{
				   if (tempList2[i].isDirectory()) {
				    File file2=new File("E:\\biyesheji\\data\\MT_nostop_withstem_lda\\"+tempList2[i].getName());
				    file2.mkdir();
				    String t="E:\\biyesheji\\data\\MT_nostop_withstem\\"+tempList2[i].getName();
				    System.out.println(t);
					ldaf.prePareLDAByFilelists(100,t , "E:\\biyesheji\\data\\MT_nostop_withstem_list\\"+tempList2[i].getName()+"\\filelists.txt","E:\\biyesheji\\data\\MT_nostop_withstem_lda\\"+tempList2[i].getName()+"\\lda_en_all_fortrain.dat");
				   }
			}
			//String sys="E22";			
			//ldaf.prePareLDAByFilelists(100, "E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain_token\\nostop\\"+sys+"\\", 
			//		"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\filelists.txt",
			//		"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en\\"+sys+".dat");
			/*
			ldaf.prePareLDAByFilelists(765416, "E:\\语料库\\languagemodel\\xin_eng_plain_token_nostop\\", 
					"E:\\语料库\\languagemodel\\filelists_len5.txt",
					"E:\\语料库\\languagemodel\\lda\\en.ldatrain.dat");
			*/
			/*
			ldaf.prePareLDAByFilelists(86070, "E:\\语料库\\languagemodel\\xin_eng_plain_token_nostop\\", 
							"E:\\语料库\\languagemodel\\filelists_len5_2004.txt",
							"E:\\语料库\\languagemodel\\lda\\en2004.ldatrain.dat");
			*/
			/*
			ldaf.prePareLDAByFilelists(86070, "E:\\语料库\\languagemodel\\xin_eng_plain_token_nostop\\", 
					"E:\\语料库\\languagemodel\\filelists_len5_2004.txt",
					"E:\\语料库\\languagemodel\\lda\\en2004.ldatrain.dat");
			*/
			/*
			ldaf.prePareLDAByFilelists(86070, "E:\\语料库\\languagemodel\\xin_eng_plain_token_stem_nostop\\", 
					"E:\\语料库\\languagemodel\\filelists_len5_2004.txt",
					"E:\\语料库\\languagemodel\\lda\\en2004_stem.ldatrain.dat");
			*/
			/*
			for(int tempk=1;tempk<23;tempk++){
				if (((tempk>=5) && (tempk<9))||(tempk==10)||(tempk==13)||(tempk>=16) && (tempk<22)) continue;
				String sys="E";
				if (tempk<10)
					sys=sys+"0"+tempk;
				else
					sys=sys+tempk;				
				ldaf.prePareLDAByFilelists(100, "E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain_token\\nostop_withstem\\"+sys+"\\", 
						"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\filelists.txt",
						"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga_stem\\"+sys+".dat");
			    
			}
			*/
			/*
			for(int tempk=1;tempk<15;tempk++){
				if ((tempk>=11) && (tempk<14)) continue;
				String sys="E";
				if (tempk<10)
					sys=sys+"0"+tempk;
				else
					sys=sys+tempk;				
				ldaf.prePareLDAByFilelists(100, "E:\\语料库\\LDC2003T17\\mt_chinese_p2\\translation_plain_token\\nostop_withstem\\"+sys+"\\", 
						"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\filelists.txt",
						"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga_stem\\"+sys+".dat");
			    
			}
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
