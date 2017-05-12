package topicmodel.gong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import edu.stanford.nlp.process.Morphology;


public class PerPlexity {
	//自己写的测算训练语料的困惑度的类
	//HashMap<String,Integer> hmwords;
	ArrayList<ArrayList<Double>> hmwordstopics;	//the outside arraylist store topic,the inside arraylist store words
	ArrayList<ArrayList<Double>> hmdoctopics;//the outside arraylist stores doc, the inside store topic
	ArrayList<ArrayList<Integer>> hmdocwords;
	ArrayList<HashMap<Integer,String>> hmdocwordtopic;
	
	
	public PerPlexity(){
		//hmwords=new HashMap<String,Integer>();	
		hmwordstopics=new ArrayList();
		hmdoctopics=new ArrayList();
		hmdocwords=new ArrayList();
		hmdocwordtopic=new ArrayList();		
	}
	
	public ArrayList<ArrayList<Double>> getwordtopics(){
		//返回p(w|t)
		return hmwordstopics;
	}
	
	public ArrayList<ArrayList<Double>> getdoctopics(){
		//返回p（t|d）
		return hmdoctopics;		
	}

	
	void loadWordMap(String wordmapfile,Integer word_id){
		try{
			//HashMap<String,Integer> hmwords;
			HashMap<Integer,String> hmwords=new HashMap<Integer,String>();
			InputStreamReader isr=new InputStreamReader(new FileInputStream(wordmapfile),"gbk");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			linestr=br.readLine();//the first line refers to the sum counts of all words
			int count=1;
			if (linestr==null) System.out.println("the line "+count+" is error!");
			while((linestr=br.readLine())!=null){
				String[] strs=linestr.split(" ");
				if (strs.length<2) {
					System.out.println("error!line: "+count);
				}
				//hmwords.put(strs[0],Integer.valueOf(strs[1]));
				hmwords.put(Integer.valueOf(strs[1]),strs[0]);
				count++;
			}
			if (hmwords.containsKey(word_id))
			{
				System.out.println(hmwords.get(word_id).toString());
			}
			br.close();
			isr.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());			
		}		
	}
	public HashMap<Integer,String> loadWordMap(String wordmapfile){
		try{
			//HashMap<String,Integer> hmwords;
			HashMap<Integer,String> hmwords=new HashMap<Integer,String>();
			InputStreamReader isr=new InputStreamReader(new FileInputStream(wordmapfile),"gbk");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			linestr=br.readLine();//the first line refers to the sum counts of all words
			int count=1;
			if (linestr==null) System.out.println("the line "+count+" is error!");
			while((linestr=br.readLine())!=null){
				String[] strs=linestr.split(" ");
				if (strs.length<2) {
					System.out.println("error!line: "+count);
				}
				//hmwords.put(strs[0],Integer.valueOf(strs[1]));
				hmwords.put(Integer.valueOf(strs[1]),strs[0]);
				count++;
			}
			
			br.close();
			isr.close();
			return hmwords;
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}		
	}
	
	public HashMap<String,Integer> loadWordMapInverse(String wordmapfile){
		try{
			//HashMap<String,Integer> hmwords;
			HashMap<String,Integer> hmwords=new HashMap<String,Integer>();
			InputStreamReader isr=new InputStreamReader(new FileInputStream(wordmapfile),"gbk");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			linestr=br.readLine();//the first line refers to the sum counts of all words
			int count=1;
			if (linestr==null) System.out.println("the line "+count+" is error!");
			while((linestr=br.readLine())!=null){
				String[] strs=linestr.split(" ");
				if (strs.length<2) {
					System.out.println("error!line: "+count);
				}				
				hmwords.put(strs[0].trim(),Integer.valueOf(strs[1]));
				count++;
			}			
			br.close();
			isr.close();
			return hmwords;
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}		
	}
   
	public void LoadPhileFile(String phi){		
		try{
			//P(w|t) 
			InputStreamReader isr=new InputStreamReader(new FileInputStream(phi),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;		
			while((linestr=br.readLine())!=null){
				//one line means one topic, one column means one word				
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				//System.out.println(strs.length);
				ArrayList<Double> allwordstopic=new ArrayList<Double>();
				for(int i=0;i<strs.length;i++){
					allwordstopic.add(Double.parseDouble(strs[i]));
				}
				hmwordstopics.add(allwordstopic);//store words-topic mainly according to topic id 				
			}
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void LoadThetaFile(String theta){
		try{
			//store doc-P(doc|topic)
			//p(t|d)
			InputStreamReader isr=new InputStreamReader(new FileInputStream(theta),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			while((linestr=br.readLine())!=null){
				//one line means one doc , one column means one topic
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				ArrayList<Double> allDocstopic=new ArrayList<Double>();
				for(int i=0;i<strs.length;i++){
					allDocstopic.add(Double.parseDouble(strs[i]));
				}
				hmdoctopics.add(allDocstopic);//store words-topic mainly according to topic id 				
			}
			//System.out.println(count);
			br.close();
			isr.close();
			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	void readassign(String assignfile){
		///这种方法考虑在文档中出现的所有非停用词,不考虑在assign中出现的topic,而是考虑这个词在所有topic的分布情况,与eachdoc_computer_pw相对应
		//在对训练模型的计算复杂度来看,它具有随着topic增加,困惑度先降后升的趋势,因此我认为这种方法是正确的
		//这里我试过考虑过词重复出现问题,试过考虑重复多次出现的词多次计算和仅计算一次(代码中	if (!allDocstopic.contains(wordid)) 的是否使用)
		//发现这两种都有效果,不过定位到的topic数目还是有差异,对fbis的en的训练模型,考虑重复,是topic为100困惑度最低,但不考虑重复,topicnum=80似乎最好
		//从困惑度的值来看,似乎不考虑重复的值比较接近blei论文中的值	
		//另外,因为一个词是对所有topic来算的,似乎多算几次也没有什么作用
		try{			
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			while((linestr=br.readLine())!=null){
				//one line means one doc 
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				ArrayList<Integer> allDocstopic=new ArrayList<Integer>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					Integer wordid=Integer.parseInt(tempstrs[0]);
					if (!allDocstopic.contains(wordid)) //不考虑重复,文中一个词只出现1次
						allDocstopic.add(wordid);
				}
				hmdocwords.add(allDocstopic);//store words-topic mainly according to topic id 				
			}
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void readassign(String assignfile,ArrayList<ArrayList<Integer>> docwordsList){
		//功能与前面的readassign一样,只不过为了被别的调用而已
		try{			
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			while((linestr=br.readLine())!=null){
				//one line means one doc 
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				ArrayList<Integer> allDocstopic=new ArrayList<Integer>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					Integer wordid=Integer.parseInt(tempstrs[0]);
					if (!allDocstopic.contains(wordid)) //不考虑重复,文中一个词只出现1次
						allDocstopic.add(wordid);
				}
				docwordsList.add(allDocstopic);//store words-topic mainly according to topic id 				
			}
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void readassign(String wordmapfile,String assignfile,ArrayList<ArrayList<String>> docwordsList){
		//功能与前面的readassign一样,只不过返回的是stem过的词而不是词的id,只不过为了被别的调用而已
		try{	
			HashMap<Integer,String> hmwords=loadWordMap(wordmapfile);
			
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			Morphology mp=new Morphology();
			while((linestr=br.readLine())!=null){
				//one line means one doc 
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				ArrayList<String> allDocstopic=new ArrayList<String>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					Integer wordid=Integer.parseInt(tempstrs[0]);
					String topicid=tempstrs[1];
					if (!hmwords.containsKey(wordid)){
						continue;
					}
					String word=hmwords.get(wordid);
					String wordstem="";
					try{
						wordstem=mp.stem(word);
					}catch(Exception ex){						
					}
					/*
					if (wordstem!=""&&!wordstem.equals(word))
					{
						System.out.println(word+":"+wordstem);
					}
					*/
					/*
					//只考虑词匹配
					if (wordstem!=""&&!allDocstopic.contains(wordstem)) //不考虑重复,文中一个词只出现1次
						allDocstopic.add(wordstem);
					else if(wordstem==""){
						allDocstopic.add(word);
					}
					*/
					
					//不仅词匹配,还要考虑每个topic的word匹配数量
					if (wordstem!=""&&!allDocstopic.contains(wordstem+":"+topicid)) //不考虑重复,文中一个词只出现1次
						allDocstopic.add(wordstem+":"+topicid);
					else if(wordstem==""){
						allDocstopic.add(word+":"+topicid);
					}
					
				}
				docwordsList.add(allDocstopic);//store words-topic mainly according to topic id 				
			}
			//System.out.println(docwordsList.size());
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void readassign2(String assignfile,ArrayList<HashMap<Integer,String>> hmdocwordtopic){
		//这种方法仅考虑在文档中出现了某个词(会出现多次),这个词出现过的topic(一个词可能会具有多个topic)
		//hmdocwordtopic外面对应每个doc,对应的是filelists.txt,因此用arraylist存储
		//每个doc对应一个hashmap,里面放置topicid:wordid_系列
		
		try{			
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			while((linestr=br.readLine())!=null){
				//one line means one doc , one column means one topic
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				HashMap<Integer,String> Hmdoctopic=new HashMap<Integer,String>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					String wordid=tempstrs[0];
					Integer topicid=Integer.parseInt(tempstrs[1]);
					
					if(!Hmdoctopic.containsKey(topicid)){
						Hmdoctopic.put(topicid, wordid);
					}
					else
					{
						String wordstrexists=Hmdoctopic.get(topicid);
						if (wordstrexists.indexOf(wordid)==-1){
							//一个词只放一次
							Hmdoctopic.remove(topicid);
							Hmdoctopic.put(topicid, wordstrexists+":"+wordid);
						}
					}
				}				
				hmdocwordtopic.add(Hmdoctopic);				 				
			}
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void readassign3(String assignfile,String wordmapfile,ArrayList<HashMap<Integer,ArrayList<String>>> hmdocwordtopic,boolean duplicateflag){
		//这种方法仅考虑在文档中出现了某个词(会出现多次),这个词出现过的topic(一个词可能会具有多个topic)
		//hmdocwordtopic外面对应每个doc,对应的是filelists.txt,因此用arraylist存储
		//每个doc对应一个hashmap,里面放置topicid:wordid_列表
		//duplicateflag false表示hmdocwordtopic里面不要放重复的词
		//***********标准答案,不需要重复;测试文档,需要重复
		try{	
			HashMap<Integer,String> wordhm=loadWordMap(wordmapfile);
			Morphology mp=new Morphology();
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			double avgtopnum=0.0;
			int docnum=0;
			while((linestr=br.readLine())!=null){
				//one line means one doc , one column means one topic
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				HashMap<Integer,ArrayList<String>> Hmdoctopic=new HashMap<Integer,ArrayList<String>>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					//先词
					String wordid=tempstrs[0];
					String wordstr=wordhm.get(Integer.valueOf(wordid));//把word编号换成是词
					try{
						wordstr=mp.stem(wordstr).toLowerCase();
					}catch(Exception ex){						
					}
					
					//然后topic
					Integer topicid=Integer.parseInt(tempstrs[1]);
					
										
					if(!Hmdoctopic.containsKey(topicid)){
						ArrayList<String> wordstrexists=new ArrayList<String>();
						wordstrexists.add(wordstr);
						Hmdoctopic.put(topicid,wordstrexists );
					}
					else
					{
						ArrayList<String> wordstrexists=Hmdoctopic.get(topicid);
						if(duplicateflag)
						{
							wordstrexists.add(wordstr);	//需要重复元素
							Hmdoctopic.put(topicid, wordstrexists);
						}
						else
						{
							//不需要重复元素,不需要放置,因为我标准答案不需要重复元素,而测试文档需要重复元素
							//do nothing
						}						
					}
				}
				avgtopnum+=Hmdoctopic.size();
			hmdocwordtopic.add(Hmdoctopic);	
			docnum++;
		}			 				
			System.out.println("avg topic num per doc:"+avgtopnum/docnum);
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	void eachdoc_compute_pw(String philefile,String thetafile,String assignfile,int topicnums){
		/*
		LoadPhileFile("D:\\perplexity_data\\150\\model-final.phi");
		LoadThetaFile("D:\\perplexity_data\\150\\model-final.theta");
		readassign("D:\\perplexity_data\\150\\model-final.tassign");	//hmdocwords
		Integer topicnums=150;
		*/
		LoadPhileFile(philefile);//hmwordstopics
		
		LoadThetaFile(thetafile);//hmdoctopics
		readassign(assignfile);	//hmdocwords
		//Integer topicnums=150;
	
		//start from assign		
		int doccount=hmdocwords.size();
		Double sumlogpwd=0.0;
		Integer sumNd=0;
		for(int i=0;i<doccount;i++){			
			ArrayList<Double> doctopicvalues=hmdoctopics.get(i);//doc-topics information
			ArrayList<Integer> allwordsforonedoc=hmdocwords.get(i);//get all words in doc i
			Integer Nd=allwordsforonedoc.size();//the total words in one document
			sumNd+=Nd;
			//Double Pwd=1.0;	
			Double Pwd=0.0;
			for(int j=0;j<Nd;j++){ 
				//j means all words
				Integer wordid=allwordsforonedoc.get(j); // word id
				Double pw_sumz=0.0;
				for(int k=0;k<topicnums;k++)//k means all topics
				{					
					Double wordtopicvalue=0.0;
					ArrayList<Double> wordtopicvalues=hmwordstopics.get(k);//一行表示一个topic
					wordtopicvalue=wordtopicvalues.get(wordid);
					Double doctopicvalue=0.0;
					doctopicvalue=doctopicvalues.get(k);				
					pw_sumz+=wordtopicvalue*doctopicvalue;	
					//pw_sumz+=Math.log(wordtopicvalue*doctopicvalue);	
				}
				//Pwd=Pwd*pw_sumz;			
				Pwd=Pwd+Math.log(pw_sumz);				
				//********我改的System.out.print("j="+j+" i="+i+" ");
				//********System.out.println(Pwd);
			}
			//System.out.println(Pwd);
			//sumlogpwd+=Math.log(Pwd);		
			sumlogpwd+=Pwd;	
		}		
		System.out.println("sumlogpwd:"+sumlogpwd);
		Double perplexity=0.0;
		perplexity=Math.exp((-1.0)*sumlogpwd/sumNd);
		System.out.println("perplexity:"+perplexity);		
		
	}	
	
	/*
	
	void readassign2(String assignfile){
		//这种方法仅考虑在文档中出现了某个词(会出现多次),这个词出现过的topic(一个词可能会具有多个topic),与eachdoc_computer_pw2相对应
		//在对训练模型的计算复杂度来看,它不具有降到升的趋势,因此我认为这种方法是错误的,因此这种方法丢弃不用了
		
		try{			
			//this file contains all word in one doc
			//one line means one doc
			InputStreamReader isr=new InputStreamReader(new FileInputStream(assignfile),"ASCII");
			BufferedReader br=new BufferedReader(isr);
			String linestr;
			//
			while((linestr=br.readLine())!=null){
				//one line means one doc , one column means one topic
				String[] strs=linestr.split(" ");	//word from 0 to the largest id
				HashMap<Integer,String> Hmdoctopic=new HashMap<Integer,String>();
				for(int i=0;i<strs.length;i++){
					String[] tempstrs=strs[i].split(":");//wordid:topic id, i only need wordid
					Integer wordid=Integer.parseInt(tempstrs[0]);
					String topicidstr=tempstrs[1];
					if(!Hmdoctopic.containsKey(wordid)){
						Hmdoctopic.put(wordid, topicidstr);
					}
					else
					{
						String topicstrexists=Hmdoctopic.get(wordid);
						Hmdoctopic.remove(wordid);
						Hmdoctopic.put(wordid, topicstrexists+":"+topicidstr);						
					}
				}				
				hmdocwordtopic.add(Hmdoctopic);				 				
			}
			//System.out.println(count);
			br.close();
			isr.close();			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	void eachdoc_compute_pw2(String philefile,String thetafile,String assignfile,int topicnums){
		//与readassign2相对应
		LoadPhileFile(philefile);//hmwordstopics
		
		LoadThetaFile(thetafile);//hmdoctopics
		readassign2(assignfile);	//hmdocwords
		//Integer topicnums=150;
	
		//start from assign		
		int doccount=hmdocwordtopic.size();
		Double sumlogpwd=0.0;// whole doc
		Integer sumNd=0;
		for(int i=0;i<doccount;i++){			
			ArrayList<Double> doctopicvalues=hmdoctopics.get(i);//doc-topics information
			HashMap<Integer,String> allwordtopicforonedoc=hmdocwordtopic.get(i);//get all words in doc i
			//Integer Nd=allwordtopicforonedoc.size();//the total words in one document
			//sumNd+=Nd;
			//Double Pwd=1.0;	
			Double PwdEachDoc=0.0;	//one doc	
			Iterator it=allwordtopicforonedoc.entrySet().iterator();
			while(it.hasNext()){ 
				//j means all words
				Map.Entry<Integer,String> tempit=(Map.Entry<Integer,String>)it.next();								
				Integer wordid=tempit.getKey(); // word id
				String topicidstr=tempit.getValue();
				String[] topicidstrs=topicidstr.split(":");
				sumNd+=topicidstrs.length;//一个词可能出现多次
				Double pw_sumz=0.0;	
				for(int j=0;j<topicidstrs.length;j++){
					Integer topicid=Integer.valueOf(topicidstrs[j]);
					Double wordtopicvalue=0.0;
					ArrayList<Double> wordtopicvalues=hmwordstopics.get(topicid);//一行表示一个topic
					wordtopicvalue=wordtopicvalues.get(wordid);
					Double doctopicvalue=0.0;					
					doctopicvalue=doctopicvalues.get(topicid);				
					pw_sumz+=wordtopicvalue*doctopicvalue;	
				}
				//pw_sumz+=Math.log(wordtopicvalue*doctopicvalue);	
				//System.out.println(pw_sumz);
				System.out.println(Math.log(pw_sumz));
				//Pwd=Pwd*pw_sumz;			
				PwdEachDoc=PwdEachDoc+Math.log(pw_sumz);	
			}
			System.out.println(PwdEachDoc);
			//sumlogpwd+=Math.log(Pwd);		
			sumlogpwd+=PwdEachDoc;	
		}		
		System.out.println("sumlogpwd:"+sumlogpwd);
		System.out.println("sumwordnums:"+sumNd);
		Double perplexity=0.0;
		perplexity=Math.exp((-1.0)*sumlogpwd/sumNd);
		System.out.println("perplexity:"+perplexity);		
		
	}	
	*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//计算困惑度
		//System.out.println("perplexity!");
		PerPlexity pp=new PerPlexity();
		//pp.loadWordMap("E:\\biyesheji\\data\\fbis\\corups_model\\corups_model60\\wordmap.txt", 3);
		
		//pp.LoadPhileFile("D:\\perplexity_data\\150\\model-final.phi");
		//pp.readassign3(assignfile, wordmapfile, hmdocwordtopic, duplicateflag)
		/*
		//这段代码测试一下大概每个文档平均有多少个topicid
		//String corpus="LDC2003T17\\mt_chinese_p2";
		String corpus="LDC2006T04\\mt_chinese_p4";
		ArrayList<HashMap<Integer,ArrayList<String>>>  wholeP=new ArrayList();//标准答案,不需要重复;测试文档,需要重复
		pp.readassign3(		
		"E:\\语料库\\"+corpus+"\\lda\\en_giga\\e01.dat.model-final.tassign",
		"E:\\语料库\\"+corpus+"\\lda\\en_giga\\wordmap.txt",
		wholeP,true
		);
		*/
		
//		String nums="120";
//		
//		pp.eachdoc_compute_pw("E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\model-final.phi"
//				,"E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\model-final.theta",
//				"E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\model-final.tassign",
//				Integer.valueOf(nums));
////		
//		String nums="200";
//		pp.eachdoc_compute_pw("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\model-final.phi"
//				,"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\model-final.theta",
//				"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\model-final.tassign",
//				Integer.valueOf(nums));
		
		String nums="66";
		pp.eachdoc_compute_pw("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\lda_en_all_fortrain.dat.model-final.phi"
				,"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\lda_en_all_fortrain.dat.model-final.theta",
				"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\lda_en_all_fortrain.dat.model-final.tassign",
				Integer.valueOf(nums));
		//后来的实验表明，只能通过测试训练语料的困惑度才能体现topic数目的变化
		
		/*
		pp.eachdoc_compute_pw("E:\\语料库\\languagemodel\\lda\\"+nums+"\\model-final.phi"
				,"E:\\语料库\\languagemodel\\lda\\"+nums+"\\model-final.theta",
				"E:\\语料库\\languagemodel\\lda\\"+nums+"\\model-final.tassign",
				Integer.valueOf(nums));
		*/
		/*
		pp.eachdoc_compute_pw("E:\\语料库\\languagemodel\\lda\\stem\\"+nums+"\\model-final.phi"
				,"E:\\语料库\\languagemodel\\lda\\stem\\"+nums+"\\model-final.theta",
				"E:\\语料库\\languagemodel\\lda\\stem\\"+nums+"\\model-final.tassign",
				Integer.valueOf(nums));
		*/
		/*	
		pp.eachdoc_compute_pw("E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.phi"
				,"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.theta",
				"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.tassign",
				Integer.valueOf(nums));
		*/
		/*
		 //test的效果似乎不能体现困惑度的变化
		pp.eachdoc_compute_pw("E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.phi"
				,"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.theta",
				"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.tassign",
				Integer.valueOf(nums));
		*/
		
		/*
		//可以测试一下inference中的test的困惑度，2003的是475 ，2006的是572，可见2006的数据与训练语料的数据差异比较大，所以KL的作用在2006中表现没有2003突出
		pp.eachdoc_compute_pw("E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga\\E01.dat.model-final.phi"
				,"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga\\E01.dat.model-final.theta",
				"E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga\\E01.dat.model-final.tassign",
				Integer.valueOf(nums));
		
		pp.eachdoc_compute_pw("E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga\\E01.dat.model-final.phi"
				,"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga\\E01.dat.model-final.theta",
				"E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga\\E01.dat.model-final.tassign",
				Integer.valueOf(nums));
		*/
		
		/*
		 //没有用
		pp.eachdoc_compute_pw2("E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.phi"
				,"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.theta",
				"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\lda_en_fortest.dat.model-final.tassign",
				Integer.valueOf(nums));
		
		pp.eachdoc_compute_pw2("E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.phi"
				,"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.theta",
				"E:\\语料库\\fbis\\lda\\en\\topicmodel\\"+nums+"\\model-final.tassign",
				Integer.valueOf(nums));
		*/
		
	}
}
