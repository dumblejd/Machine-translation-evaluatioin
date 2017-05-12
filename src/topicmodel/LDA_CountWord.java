package topicmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

//not use this class, pls use generalproc/ComputerTFIDF class
//TF-IDF是一种统计方法，用以评估一字词对于一个文件集或一个语料库中的其中一份文件的重要程度。
//字词的重要性随着它在文件中出现的次数成正比增加，但同时会随着它在语料库中出现的频率成反比下降。
//TF-IDF加权的各种形式常被搜索引擎应用，作为文件与用户查询之间相关程度的度量或评级。
//除了TF-IDF以外，因特网上的搜索引擎还会使用基于链接分析的评级方法，以确定文件在搜寻结果中出现的顺序。
public class LDA_CountWord {
	//compute TF/IDF
	public HashMap<String, Integer> hm;
	private String Encoding="ASCII";
	
	public LDA_CountWord(String encoding){
		this.Encoding =encoding;
	}
	
	public void countAllDocumentTF(String docspath) throws IOException {
		
		//compute tf(term frequency)
		String var1 = "";		
		hm = new LinkedHashMap<String, Integer>();
		Integer total = 0;
		File fd = new File(docspath);	  	
		if (fd.isDirectory()){
			//is directory
			 String[]  tempList  =  fd.list();      //返回的值是所有的文件或者文件夹  所以下面要重新进行判断
			 File  temp  =  null;  
		      for  (int  i  =  0;  i  <  tempList.length;  i++)  
		      {  
		           if  (docspath.endsWith(File.separator))   //如果是“//” 结尾直接加上文件  否则添加“//”      用这个方法来完成对docspath下所有文件检索前的路径设置
		           {  
		              temp  =  new  File(docspath  +  tempList[i]);  
		           }  
		           else  
		           {  
		              temp  =  new  File(docspath  +  File.separator  +  tempList[i]);  
		           }  
		            if (temp.isDirectory())         //如果重新设置好的路径 是一个目录，跳出本次循环，对tempList的下一个进行路径设置
		            {
		            	continue;
		            }
		            //如果是设置完路径，此路径代表文件（!temp.isDirectory()） 就进行读取
			        InputStreamReader isrr=new InputStreamReader(new FileInputStream(temp),this.Encoding);
					BufferedReader bfr = new BufferedReader(isrr);
					String docline = bfr.readLine(); 
					total=0;
					Integer tmpnum = 0;
					while (docline != null) 
					{					
						String[] words = docline.split("[ \\t\\n]");      //用tab 换行，空格 等来分词
						for (String word : words) 
						{
							total++;
							word = word.toLowerCase();    //计算词语总数，并全都转化为小写
							if (hm.containsKey(word)) 
							{
								tmpnum = (Integer) hm.get(word);   //在hashmap中的设定为（word，次数） 发现读到重复的 则删除原来的 加入一个次数+1的
								tmpnum++;
								hm.remove(word);
								hm.put(word, tmpnum);
							} 
							else 
							{
								hm.put(word, 1);
							}
						}
						docline = bfr.readLine();       //继续下一行
					}				
					bfr.close();
					isrr.close();
		      
				if  (docspath.endsWith(File.separator))  {
					 writeHMtoDocument(hm, docspath +"tf_dir\\" +temp.getName()+".allwordcount", total);                   //根据带不带“//“  来创建主题频率的文件
		              
		         }  
		         else  {  
		        	 writeHMtoDocument(hm, docspath +  File.separator+"tf_dir\\" +temp.getName()+".allwordcount", total);    
		              
		         }  
				
				hm.clear();
		      }
				
		}
		else {
			System.out.print("you should input a directory name,not a file name");
			return;
		}
	}
	public void writeHMtoDocument(HashMap<String, Integer> hm,
			String outfilename, Integer TotalWordsNum) throws IOException {
		
		//begin to write TF file
		//File outSentFile = new File(outfilename);
		//FileWriter fw = new FileWriter(outSentFile);
		OutputStreamWriter oswSvmfile=new OutputStreamWriter(new FileOutputStream(outfilename),this.Encoding);
		BufferedWriter bw = new BufferedWriter(oswSvmfile);
		//Iterator iter = map.entrySet().iterator(); 和 Iterator iter = map.keySet().iterator();
		//第一种 遍历了两遍map  第二种只遍历一遍  效率要搞很多
		//第一种 直接传给另一个Map.Entry     　 Object key = entry.getKey(); Object val = entry.getValue(); 可以直接取
		//第二种 需要两次，一次 iterator 一次get Object key = iter.next(); Object val = map.get(key); 
		Iterator<String> it = hm.keySet().iterator();
		String key = "";
		Integer value = 0;
		bw.write(TotalWordsNum.toString() + " Count TF\n");

		while (it.hasNext()) {
			key = it.next();
			value = hm.get(key);
			bw.write(key + " " + value + " " + value.doubleValue()
					/ TotalWordsNum + "\n");
		}
		bw.flush();
		oswSvmfile.close();
		bw.close();
		
	}
	//=============和上面一样，这个函数是count，返回所有词的计数，String word[] = docline.split(" ");
	public HashMap<String, Integer> prepare_countIDF(String Filedir) throws IOException {
		//在信息检索中，使用最多的权重是“逆文本频率指数” （Inverse document frequency 缩写为ＩＤＦ），
		//它的公式为ｌｏｇ（Ｄ／Ｄｗ）其中Ｄ是全部网页数。比如，我们假定中文网页数是Ｄ＝１０亿，
		//应删除词“的”在所有的网页中都出现，即Ｄｗ＝１０亿，那么它的ＩＤＦ＝log(10亿/10亿）= log (1) = ０。
		//假如专用词“原子能”在两百万个网页中出现，即Ｄｗ＝２００万，则它的权重ＩＤＦ＝log(500) =6.2
		HashMap<String, Integer> hglobal = new HashMap<String, Integer>();
		// this hglobal stores the document number contains per word
		//notice:filedir stores all distinct dealed file which in fact no content doc ,but tf doc. 
		File f = new File(Filedir);
		File[] filelist = null;
		if (f.isDirectory())  
		{
			filelist = f.listFiles();
		}
		for (int i = 0; i < filelist.length; i++) {
			//FileReader ffr = new FileReader(filelist[i]);
		    InputStreamReader isrr=new InputStreamReader(new FileInputStream(filelist[i]),this.Encoding);
			BufferedReader bfr = new BufferedReader(isrr);
			String docline = bfr.readLine();
			docline = bfr.readLine();
			Integer wordc = 0;
			while (docline != null) {
				String word[] = docline.split(" ");
				if (hglobal.containsKey(word[0])) {
					wordc = hglobal.get(word[0]);
					hglobal.remove(word[0]);
					hglobal.put(word[0], wordc + 1);
					//break;//this is added by gong,I think this must be done,because in one document maybe a word ocurrs more time, I only to know how number docs contains this word
				} else
					hglobal.put(word[0], 1);
				docline = bfr.readLine();
			}
			bfr.close();
			isrr.close();				
		}
		System.out.print("prepare idf data sucessfully!");
		return (HashMap<String, Integer>) hglobal;
	}

	//==========这里使用的filedir 就是上面countAllDocumentTF 最后生成的文件\\tf_dir，并且继续在这个文件里写上计算好的数据
	public void computeIDF(String fileDir,int docs) throws IOException {
		//notice: filedir stores these files which contains TF. 
		//docs means the number of documents. 
		System.out.println("begin to compute words tf/idf ,pls waiting .....");
		//int docs=960;if there are 960 documents in sum 
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm = this.prepare_countIDF(fileDir);//这个必须是tf的文件夹
		File f = new File(fileDir);
		File[] filelist = null;
		if (f.isDirectory())
			filelist = f.listFiles();
		//notice: filedir stores these files which contains TF. 
		String line = "";
		for (int ifname = 0; ifname < filelist.length; ifname++) {
			//FileReader fmf = new FileReader(filelist[ifname]);
			InputStreamReader isrr=new InputStreamReader(new FileInputStream(filelist[ifname]),this.Encoding);
			BufferedReader fmfi = new BufferedReader(isrr);
			FileWriter fw = new FileWriter(filelist[ifname].toString().replace(
					"wordc", "wc"));//把wordc 替换成wc
			BufferedWriter bw = new BufferedWriter(fw);
			line = fmfi.readLine(); //这里多读一行是因为 写入的时候多谢了 bw.write(TotalWordsNum.toString() + " Count TF\n");
			bw.write(line
					+ " NDOC IDF(Log(totalDoc/havewordDoc)) TFIDF(TF*IDF)");
			bw.newLine();
			line = fmfi.readLine();
			while (line != null) {
				bw.write(line + " ");
				String[] tmp = line.split(" ");  
				//	bw.write(key + " " + value + " " + value.doubleValue(/ TotalWordsNum + "\n");   获取之前这样写入的数据
				Integer hadoc = hm.get(tmp[0]);   //  int a=hm.get（string）
				bw.write(hadoc + " ");
				Double ft = (Double) Math.log((docs*1.0)/ hadoc);
				bw.write(Double.toString(ft) + " ");
				Double tfidf = Double.valueOf(tmp[2]) * ft;
				bw.write(Double.toString(tfidf));
				bw.newLine();
				line = fmfi.readLine();
			}
			bw.flush();
			isrr.close();
			fmfi.close();
			bw.close();
			fw.close();

		}
		System.out.println("compute tf/idf finished ,now begin to write file....");
		for (int i = 0; i < filelist.length; i++) {
			filelist[i].delete();
		}
		System.out.println("tf/idf finished ");
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//not use this class, pls use generalproc/ComputerTFIDF class
		
		/*
		LDA_CountWord tfidf=new LDA_CountWord("GBK");
		
		//tfidf.countAllDocumentTF("G:\\语料库\\fbis\\document_units\\new_2011_12_21\\cn_tf_idf");
		tfidf.computeIDF("G:\\语料库\\fbis\\document_units\\new_2011_12_21\\cn_tf_idf\\tf_dir\\",10354);
		*/
		LDA_CountWord tfidf=new LDA_CountWord("ASCII");
		tfidf.countAllDocumentTF("G:\\语料库\\fbis\\document_units\\new_2011_12_21\\en_tf_idf");
		tfidf.computeIDF("G:\\语料库\\fbis\\document_units\\new_2011_12_21\\en_tf_idf\\tf_dir\\",10354);
	}
}
