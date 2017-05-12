package topicmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import topicmodel.jgibblda.Dictionary;
import edu.stanford.nlp.process.Morphology;

class myFileFilter implements FileFilter{
	//override
	public boolean accept(File pathname){
		/*String filename=pathname.getName().toLowerCase();
		if (filename.contains(".txt")){
			return false;
		}
		else
		{
			return true;
		}
		*/
		//=====上面注释的 找出txt文件，去除后 现在是普通的判断是不是文件
		if (pathname.isFile()){
			return true;
		}
		else
			return false;
	}
}

public class LDA_FilterStopWord {
	//first we must prepare the LDA data for dealing
	//first we should filter the stopword, if required , we do stemming
	//modified by gongzhengxian	
	public String Encoding="gbk";
	static String perlpath="c:\\perl\\bin\\perl ";
	String apppath="";
	
	public void tokenfile(String fileDir,String outputdir) throws IOException {
		//tokenize一下英文文件,否则会出错

		File ff = new File(fileDir);
		if (ff.isDirectory()){
			//要处理该目录下的所有文件
			 String[]  tempList  =  ff.list();  
			 File  temp  =  null;  
		      for  (int  i  =  0;  i  <  tempList.length;  i++)  {  
		           if  (fileDir.endsWith(File.separator))  {  
		              temp  =  new  File(fileDir  +  tempList[i]);  
		           }  
		           else  {  
		              temp  =  new  File(fileDir  +  File.separator  +  tempList[i]);  
		           }  
		           String cmd;
		   		String msg="";
		   		String brs="";
		   		cmd=perlpath+apppath+"\\src\\generalproc\\perlscript\\tokenizer2.perl "+"-l en -i "+temp.getPath()+" -o "+outputdir+temp.getName();
		   		//tokenizer2是我自己根据tokenizer.perl改写的,因为在exec时不支持用<>的输入输出,所以我用了参数的形式来修正了,detokenizer2同理
		   			   		
		   		try{
		   			Process prc=Runtime.getRuntime().exec(cmd);
		   			final InputStream ins1=prc.getErrorStream();      //获取子进程的错误流。如果错误输出被重定向，则不能从该流中读取错误输出。
		   			final InputStream ins2=prc.getInputStream();
		   			new Thread(){
		   				public void run(){
				   			try{
					   			BufferedReader br=new BufferedReader(new InputStreamReader(ins1));
					   			String brs="";
					   			while((brs=br.readLine())!=null);
					   			br.close();
					   			br=null;
				   			}
				   			catch(Exception te)
				   			{
				   				System.out.print(te.getMessage());
				   			}
				   				
		   				}
		   			}.start();
		   			/*new Thread(){
		   				public void run(){
				   			try{
					   			BufferedReader br=new BufferedReader(new  InputStreamReader(ins2));
					   			String brs="";
					   			while((brs=br.readLine())!=null);
					   			br.close();
					   			br=null;
				   			}
				   			catch(Exception te)
				   			{
				   				System.out.print(te.getMessage());
				   			}
				   				
		   				}
		   			}.start();*/
		   			
		   			prc.waitFor();
		   			
		   		}
		   		catch(IOException ioex){
		   			System.out.print(ioex.getMessage());
		   		}
		   		catch(Exception ex){
		   			System.out.print(ex.getMessage()) ;
		   		}
		   		
			  		
		      }
		      
		}
	
	}
	
	public void FilterStopWord(String sw_file_path,String filepath,String outputdirfile, boolean StemFlag) throws IOException {
		if  (outputdirfile.endsWith(File.separator))  {  
	    	
         }  
         else  {  
        	 outputdirfile=outputdirfile+File.separator;
         } 
		//sw_file_path 停用词字典文件路径及名字
		//filepath 需要进行过滤的文件或者文件夹
		//stemflag =true 表示要词形还原  否则不还原
		//过滤掉英文停用词
		//可能会出现乱码,所以不用FileReader,还是用InputStreamReader
		File inSentFile = new File(sw_file_path);
		InputStreamReader isr=new InputStreamReader(new FileInputStream(inSentFile),this.Encoding);
		//FileReader fr = new FileReader(inSentFile);
		//BufferedReader br = new BufferedReader(fr);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		Dictionary dict = new Dictionary();
		while (line != null) {
			if (!line.startsWith("#")) {
				dict.addWord(line.toLowerCase());
				System.out.println(line);
			}
			line = br.readLine();
		}
		//fr.close();
		isr.close();
		br.close();
		//上面是读入停用词放置到内存
		//下面是开始过滤文件中的停用词

		//开始过滤
		//分是路径还是文件,如果是文件,则只要处理一个文件,如果是路径,需要处理该目录下的所有文件

		File ff = new File(filepath);
		if (ff.isDirectory()){
			//要处理该目录下的所有文件
			if (!StemFlag){
				//不要词形还原
				 File[] temp=ff.listFiles(new myFileFilter());//因为文件夹里含有子文件夹和文件，我只需要文件，所以我自己做了个文件过滤器
				 for (int i=0;i<temp.length;i++){
					 FilterStopWordOneFile(temp[i].getPath(), outputdirfile+temp[i].getName()+".nostop", dict);//真正处理停用词
				 }
			}
			else
			{
				File[] temp=ff.listFiles(new myFileFilter());//因为文件夹里含有子文件夹和文件，我只需要文件，所以我自己做了个文件过滤器
				 for (int i=0;i<temp.length;i++){
					 FilterStopWordOneFile_withstem(temp[i].getPath(), outputdirfile+temp[i].getName()+".nostop", dict);//真正处理停用词
				 }
			}
			 /*
			 String[]  tempList  =  ff.list();  //,ff.list包括里面还有的文件夹名。所以还要进一步处理
			 
			 File  temp  =  null;  
		      for  (int  i  =  0;  i  <  tempList.length;  i++)  {  
		           if  (filepath.endsWith(File.separator))  {  
		              temp  =  new  File(filepath  +  tempList[i]);  
		           }  
		           else  {  
		              temp  =  new  File(filepath  +  File.separator  +  tempList[i]);  
		           }  
		            //FileReader ffr = new FileReader(temp);
		            //BufferedReader bfr = new BufferedReader(ffr);
		            //InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp),this.Encoding);
			  		//BufferedReader bfr = new BufferedReader(isrr);
			  		//String docline = bfr.readLine();
			  		//while (docline != null) {
			  			FilterStopWordOneFile(temp.getPath(), outputdirfile+temp.getName()+".nostop", dict);//真正处理停用词
			  			//docline = bfr.readLine();
			  		//}
			  		//bfr.close();
			  		//isrr.close();
			  		//ffr.close();
		      }
			*/
		      
		}
		else if(ff.isFile()){
			//仅处理一个文件
			/*InputStreamReader isrr=new InputStreamReader(new FileInputStream(ff),this.Encoding);
	  		BufferedReader bfr = new BufferedReader(isrr);
	  		String docline = bfr.readLine();
	  		while (docline != null) {*/
	  			FilterStopWordOneFile(ff.getPath(), ff.getPath()+".nostop", dict);//真正处理停用词
	  			/*docline = bfr.readLine();
	  		}
	  		bfr.close();
	  		isrr.close();*/
		}
		
	}
	
	public void FilterStopWord_singlesys(String sw_file_path,String filepath,String sysid,String outputdirfile) throws IOException {
		//sw_file_path 停用词字典文件路径及名字,过滤一个参考系统的文件
		//filepath 需要进行过滤的文件或者文件夹
		//过滤掉英文停用词
		//可能会出现乱码,所以不用FileReader,还是用InputStreamReader
		File inSentFile = new File(sw_file_path);
		InputStreamReader isr=new InputStreamReader(new FileInputStream(inSentFile),this.Encoding);
		//FileReader fr = new FileReader(inSentFile);
		//BufferedReader br = new BufferedReader(fr);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		Dictionary dict = new Dictionary();
		while (line != null) {
			if (!line.startsWith("#")) {
				dict.addWord(line.toLowerCase());
				System.out.println(line);
			}
			line = br.readLine();
		}
		//fr.close();
		isr.close();
		br.close();
		//上面是读入停用词放置到内存
		//下面是开始过滤文件中的停用词

		//开始过滤
		//分是路径还是文件,如果是文件,则只要处理一个文件,如果是路径,需要处理该目录下的所有文件

		File ff = new File(filepath);
		if (ff.isDirectory()){
			//要处理该目录下的所有文件
			 String[]  tempList  =  ff.list();  
			 File  temp  =  null;  
		      for  (int  i  =  0;  i  <  tempList.length;  i++)  {  
		    	   if (!tempList[i].contains(sysid)) continue;//不是指定系统的参考文献,不要
		           if  (filepath.endsWith(File.separator))  {  
		              temp  =  new  File(filepath  +  tempList[i]);  
		           }  
		           else  {  
		              temp  =  new  File(filepath  +  File.separator  +  tempList[i]);  
		           }  
		            //FileReader ffr = new FileReader(temp);
		            //BufferedReader bfr = new BufferedReader(ffr);
		            /*InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp),this.Encoding);
			  		BufferedReader bfr = new BufferedReader(isrr);
			  		String docline = bfr.readLine();
			  		while (docline != null) {*/
			  			FilterStopWordOneFile(temp.getPath(), outputdirfile+temp.getName()+".nostop", dict);//真正处理停用词
			  			/*docline = bfr.readLine();
			  		}
			  		bfr.close();
			  		isrr.close();*/
			  		//ffr.close();
		      }
		      
		}
		else if(ff.isFile()){
			//仅处理一个文件
			/*InputStreamReader isrr=new InputStreamReader(new FileInputStream(ff),this.Encoding);
	  		BufferedReader bfr = new BufferedReader(isrr);
	  		String docline = bfr.readLine();
	  		while (docline != null) {*/
	  			FilterStopWordOneFile(ff.getPath(), ff.getPath()+".nostop", dict);//真正处理停用词
	  			/*docline = bfr.readLine();
	  		}
	  		bfr.close();
	  		isrr.close();*/
		}
		
	}
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
	public void FilterStopWordOneFile(String infilename, String outfilename,
			Dictionary dict) throws IOException {
		////真正处理停用词
		//infilename 输入文件,outfilename是输出文件,dict是加载到内存的停用词字典
		InputStreamReader isrr=new InputStreamReader(new FileInputStream(infilename),this.Encoding);
  		BufferedReader br = new BufferedReader(isrr);
		//FileReader fr = new FileReader(infilename);
		//BufferedReader br = new BufferedReader(fr);
		//FileWriter fw = new FileWriter(outfilename);
		//OutputStreamWriter isw=new OutputStreamWriter(new FileOutputStream(outfilename),"gbk");
		OutputStreamWriter isw=new OutputStreamWriter(new FileOutputStream(outfilename),this.Encoding);
		//BufferedWriter bw = new BufferedWriter(fw);
		BufferedWriter bw = new BufferedWriter(isw);
		String line = br.readLine();
		while (line != null) {
			if (line.trim()!=""){
				String tmp[] = line.split(" ");
				boolean emptyflag=true;
				for (int i = 0; i < tmp.length; i++) 
				{
					if(this.Encoding.equals("ASCII"))
					{
						//中文不需要这个判断
						if(containsOutAscii(tmp[i].toLowerCase()))
							continue;//在测评语料中发现有乱码
					}
					if (dict.contains(tmp[i].toLowerCase())
							|| dict.contains(tmp[i]))
					{
						continue;
					}
						//bw.write(tmp[i] + " ");
					bw.write(tmp[i].toLowerCase() + " ");
					emptyflag=false;
				}
				if (!emptyflag)	bw.newLine();
				{
				bw.flush();
				}
			}
			line = br.readLine();
		}
		bw.flush();
		br.close();
		isrr.close();
		//fr.close();
		bw.close();
		//fw.close();
		isw.close();
	}
	public void FilterStopWordOneFile_withstem(String infilename, String outfilename,
			Dictionary dict) throws IOException {
		////真正处理停用词
		//infilename 输入文件,outfilename是输出文件,dict是加载到内存的停用词字典
		InputStreamReader isrr=new InputStreamReader(new FileInputStream(infilename),this.Encoding);
  		BufferedReader br = new BufferedReader(isrr);
		//FileReader fr = new FileReader(infilename);
		//BufferedReader br = new BufferedReader(fr);
		//FileWriter fw = new FileWriter(outfilename);
		//OutputStreamWriter isw=new OutputStreamWriter(new FileOutputStream(outfilename),"gbk");
		OutputStreamWriter isw=new OutputStreamWriter(new FileOutputStream(outfilename),this.Encoding);
		//BufferedWriter bw = new BufferedWriter(fw);
		BufferedWriter bw = new BufferedWriter(isw);
		String line = br.readLine();
		Morphology mp=new Morphology();  
		while (line != null) {
			if (line.trim()!=""){
				String tmp[] = line.split(" ");
				boolean emptyflag=true;
				for (int i = 0; i < tmp.length; i++) 
				{
					String orgword=tmp[i];
					String stemword=orgword;
					if(this.Encoding.equals("ASCII"))
					{
						//中文不需要这个判断
						if(containsOutAscii(tmp[i].toLowerCase()))
						{
							continue;//在测评语料中发现有乱码
						}
						//准备英语文本，需要最小化和词形还原
						orgword=tmp[i].toLowerCase();
						try
						{
							stemword=mp.stem(orgword);
						}catch(Exception ex2){}
						
						if (stemword==null) stemword=orgword;
					}
					//有时候中文里夹着英文，所以又一次判断最小化
					if (dict.contains(stemword.toLowerCase())
							|| dict.contains(stemword))
						continue;
					//bw.write(tmp[i] + " ");
					bw.write(stemword.toLowerCase() + " ");
					emptyflag=false;
				}
				if (!emptyflag)	bw.newLine();
				bw.flush();
			}
			line = br.readLine();
		}
		bw.flush();
		br.close();
		isrr.close();
		//fr.close();
		bw.close();
		//fw.close();
		isw.close();
	}
	void createfilelists(String filename,String filepath) throws IOException{
		File ff = new File(filepath);
		if (ff.isDirectory()){
			//String[]  tempList  =  ff.list();
			File[] tempList=ff.listFiles(new myFileFilter());
			OutputStreamWriter isw=new OutputStreamWriter(new FileOutputStream(filename),this.Encoding);
			BufferedWriter bw = new BufferedWriter(isw);
		    for  (int  i  =  0;  i  <  tempList.length;  i++)  {  		    	   
		    	bw.write(tempList[i].getPath());
		    	bw.write('\n');
		    }
		    bw.flush();
			bw.close();		   
		    isw.close();
		}
		else
		{
			System.out.print("sorry, it's not a dir path");
		}
	}
	public static void main(String[] args)  throws IOException{
		//注意在调用本程序之前需要先tokenize
		//我不仅去除停用词,对于英文部分,还进行了最小化(输出的时候),把不是英文的乱码去除掉. 
		LDA_FilterStopWord ldaf=new LDA_FilterStopWord();
		
		//String Apppath=System.getProperty("user.dir");//相对于当前用户目录的相对路径 
		//ldaf.apppath=Apppath;
		//ldaf.tokenfile("E:\\biyesheji\\data\\fbis\\fbis_content", "E:\\biyesheji\\data\\fbis\\fbis_content_token");
		/*
		//产生为moses中的生成tf-idf的中间文件服务
		ldaf.Encoding ="ASCII";
		ldaf.createfilelists("E:\\corpus_data\\deal\\cn\\tf_idf\\filelists.txt", "E:\\corpus_data\\deal\\cn\\");
		ldaf.createfilelists("E:\\corpus_data\\deal\\en\\tf_idf\\filelists.txt", "E:\\corpus_data\\deal\\en\\");
		*/
		//ldaf.tokenfile("G:\\语料库\\nist\\nist2005\\ref\\puretext", "G:\\语料库\\nist\\nist2005\\ref\\puretexttoken\\");
		//String sys="E01";
		//ldaf.tokenfile("E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain\\"+sys+"\\", "E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain_token\\"+sys+"\\");
		
		
		//过滤中文
		//ldaf.Encoding="gbk";
		//ldaf.FilterStopWord("F:\\tpdir\\cn_stop_tfidf.txt","f:\\tpdir\\cn\\","f:\\tpdir\\cn\\no_stop\\" );
		//ldaf.FilterStopWord("D:\\research_program\\eclipse_workspace\\tp\\file\\stopwords_cn.txt","E:\\语料库\\fbis\\document_units\\new_2011_12_21\\cn_segbystanford\\","E:\\语料库\\fbis\\lda\\cn\\nostop\\" );
		//过滤英文
		ldaf.Encoding="ASCII";//对英文部分又作了乱码判断
		//ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt","E:\\biyesheji\\data\\fbis\\fbis_content","E:\\biyesheji\\data\\fbis\\fbis_content_nostop\\",false );
		//ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt","E:\\biyesheji\\data\\fbis\\fbis_content","E:\\biyesheji\\data\\fbis\\fbis_stop_withstem\\",true );
		
		
		//ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt","E:\\biyesheji\\data\\fbis\\fbis_content","E:\\biyesheji\\data\\fbis\\fbis_stop_withstem\\",true );

		File file=new File("E:\\biyesheji\\data\\MT_content");
		File[] tempList = file.listFiles();
		
		for(int i=0;i<tempList.length;i++)
		{
			   if (tempList[i].isDirectory()) {
			    System.out.println("文件夹："+tempList[i]);
			    File file2=new File("E:\\biyesheji\\data\\MT_nostop\\"+tempList[i].getName());
			    file2.mkdir();
			    String t="E:\\biyesheji\\data\\MT_nostop\\"+tempList[i].getName();
			    System.out.println(t);
				ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt",tempList[i].getPath(),t,false );
			   }
		}
		
		
		for(int i=0;i<tempList.length;i++)
		{
			if (tempList[i].isDirectory()) {
			    System.out.println("文件夹："+tempList[i]);
			    File file2=new File("E:\\biyesheji\\data\\MT_nostop_withstem\\"+tempList[i].getName());
			    file2.mkdir();
			    String t="E:\\biyesheji\\data\\MT_nostop_withstem\\"+tempList[i].getName();
				ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt",tempList[i].getPath(),t,true );
			   }
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//
//		ldaf.FilterStopWord("E:\\biyesheji\\lda工具\\stopwords_en.txt",
//				"E:\\biyesheji\\data\\fbis\\fbis_content",
//				"E:\\biyesheji\\data\\fbis\\fbis_stop_withstem\\" ,true);//true indicate need stemming
		/*
		for(int tempk=1;tempk<15;tempk++){
			if ((tempk>=11) && (tempk<14)) continue;
			String sys="E";
			if (tempk<10)
				sys=sys+"0"+tempk;
			else
				sys=sys+tempk;
		
		    ldaf.FilterStopWord("D:\\research_program\\eclipse_workspace\\tp\\file\\stopwords_en.txt",
				"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\translation_plain_token\\"+sys+"\\",
				"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\translation_plain_token\\nostop_withstem\\"+sys+"\\",true );
		}
		*/
		/*
		for(int tempk=1;tempk<23;tempk++){
			if (((tempk>=5) && (tempk<9))||(tempk==10)||(tempk==13)||(tempk>=16) && (tempk<22)) continue;
			String sys="E";
			if (tempk<10)
				sys=sys+"0"+tempk;
			else
				sys=sys+tempk;
		
		    ldaf.FilterStopWord("D:\\research_program\\eclipse_workspace\\tp\\file\\stopwords_en.txt",
				"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain_token\\"+sys+"\\",
				"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\translation_plain_token\\nostop_withstem\\"+sys+"\\",true );
		}
		*/
		/*
		ldaf.FilterStopWord("D:\\research_program\\eclipse_workspace\\tp\\file\\stopwords_en.txt",
				"E:\\语料库\\languagemodel\\xin_eng_plain_token\\",
				"E:\\语料库\\languagemodel\\xin_eng_plain_token_nostop\\",false );
		*/
		/*
		//这个是stem的结果
		ldaf.FilterStopWord("D:\\research_program\\eclipse_workspace\\tp\\file\\stopwords_en.txt",
				"E:\\语料库\\languagemodel\\xin_eng_plain_token\\",
				"E:\\语料库\\languagemodel\\xin_eng_plain_token_stem_nostop\\",true );//true 表需要词形还原
		*/
		
		//ldaf.FilterStopWord("E:\\myeclipse\\wsd\\file\\stopwords_cn.txt","G:\\语料库\\nist\\nist2005\\src\\srcpain\\","G:\\语料库\\nist\\nist2005\\src\\srcnostop\\" );
		//ldaf.FilterStopWord("G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cn_stop.tiyu","G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cn\\tiyu_seg","G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cnnostop\\" );
		
		//ldaf.FilterStopWord("G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cn_stop.txt","G:\\语料库\\2005年863机译翻译评测数据\\testfortp\\","G:\\语料库\\2005年863机译翻译评测数据\\testfortp\\" );
		//ldaf.FilterStopWord("E:\\myeclipse\\wsd\\file\\stopwords_cn.txt","G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cn\\","G:\\语料库\\哈工大机器翻译课题组语料\\tp\\cnnostop\\" );
		//tokenize english file
		//ldaf.apppath=System.getProperty("user.dir");//相对于当前用户目录的相对路径 
		//ldaf.apppath+="\\src\\generalproc\\";
		//ldaf.tokenfile("G:\\语料库\\nist\\nist2005\\ref\\refplain\\", "G:\\语料库\\nist\\nist2005\\ref\\refplaintok\\");
		//过滤英文
		//ldaf.Encoding="ASCII";
		//ldaf.FilterStopWord("E:\\myeclipse\\wsd\\file\\stopwords_en.txt","G:\\语料库\\nist\\nist2005\\ref\\puretexttoken\\","G:\\语料库\\nist\\nist2005\\ref\\refnostop\\" );
		//ldaf.FilterStopWord_singlesys("E:\\myeclipse\\wsd\\file\\stopwords_en.txt","G:\\语料库\\nist\\nist2005\\ref\\puretexttoken\\","chc","G:\\语料库\\nist\\nist2005\\ref\\refnostop\\" );
	}
}
