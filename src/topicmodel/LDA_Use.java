package topicmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import topicmodel.jgibblda.LDA;
//import spider.util.Stemmer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LDA_Use {
	private String Encoding="gbk";

	
public String getEncoding() {
		return Encoding;
	}

	public void setEncoding(String encoding) {
		Encoding = encoding;
	}

public void useLDA(String filepath,String outputdir,int ntopic) throws IOException{
			
			//String aa="-est -alpha 0.5 -beta 0.1 -ntopics 10 -niters 200 "+ 
			//	       "-twords 20 -dfile "+filepath+ " -dir "+outputdir;
			String aa="-est -alpha 0.5 -beta 0.1 -ntopics "+Integer.toString(ntopic)+" -niters 1000 "+ 
		       "-twords 1000 -dfile "+filepath+ " -dir "+outputdir;
			String[] args=aa.split(" ");
			topicmodel.jgibblda.LDA.myLDA(args);	
}

public void inferTm(String modeldir,String modelname,String newdatafile){
	String aa="-inf -dir "+modeldir+" -model " +modelname +  
       " -niters 1000 -twords 100 -dfile "+newdatafile;//在infer的时候虽然传了iters 1000,但似乎无效,需要直接到它的inference.java中修改niters属性
	//-inf indicate:do inference
	//-dir indicate:the directory contain the previous estimated model
	//-model the name of the previously estimated model
	//-dfile the file containing new data
	String[] args=aa.split(" ");
	topicmodel.jgibblda.LDA.myLDA(args);	
}

public static void main(String[] args)  throws IOException{	
	
	LDA_Use ldaf=new LDA_Use();
	LDA_PrepareLDADataTxt prepare=new LDA_PrepareLDADataTxt();
	ldaf.Encoding="ASCII";
	//不用这个 ldaf.inferTm("H:\\LDA_dealt_results\\LDA_NIST2005_infer\\model_15\\", "model-final", "nist2005.lda.txt");
	// ldaf.inferTm("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem66\\", "model-final", "999lda_en_all_E11.dat");

	 
	 
//	 File file=new File("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem66");
//		File[] tempList = file.listFiles();
//		
//		for(int i=0;i<tempList.length;i++)
//		{
//			   if (tempList[i].isDirectory()) {
//			    System.out.println("文件夹："+tempList[i]);
//				ldaf.inferTm("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem66\\", "model-final", "\\"+tempList[i].getName()+"\\lda_en_all_fortrain.dat");
//			   }
//		}
	
	 
	 File file=new File("C:\\Users\\JinDi\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\corups_model_withstem66");
		File[] tempList = file.listFiles();
		
		for(int i=0;i<tempList.length;i++)
		{
			   if (tempList[i].isDirectory()) {
			    System.out.println("文件夹："+tempList[i]);
				ldaf.inferTm("C:\\Users\\JinDi\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\\corups_model_withstem66\\", "model-final", "\\"+tempList[i].getName()+"\\lda_en_all_fortrain.dat");
			   }
		}
	
	
	//bginto use lda
	//英文的topic model
	//ldaf.useLDA("G:\\语料库\\nist\\nist2005\\ref\\refnostop\\XIN.lda.txt","G:\\语料库\\nist\\nist2005\\ref\\refnostop\\XIN_topicmodel");
	//中文的topic model
	//ldaf.useLDA("G:\\语料库\\nist\\nist2005\\src\\srcnostop\\XIN.lda.txt","G:\\语料库\\nist\\nist2005\\src\\srcnostop\\XIN_topicmodel");
	
	//生成中文和英文的要建立模型的准备数据lda文件
	
	//ldaf.Encoding="GBK";
	//ldaf.prePareLDA(10354,"E:\\corpus_data\\deal\\cn\\no_stop\\","E:\\corpus_data\\deal\\cn\\tp\\","lda.cn.txt");
	
	/*
	 2017年4月25日19:47:32
	ldaf.Encoding="ASCII";
	//ldaf.isdigit("3088-2504-1628");//3088-2504-1628
	prepare.prePareLDA(5498,"E:\\biyesheji\\data\\fbis\\fbis_content_nostop" , "E:\\biyesheji\\data\\fbis\\fbis_content_nostop_filelist","passageoneline.nostop");
	prepare.prePareLDAByFilelists(5498, "E:\\biyesheji\\data\\fbis\\fbis_content_nostop\\","E:\\biyesheji\\data\\fbis\\fbis_content_nostop_filelist\\filelists.txt","E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt");
      金帝测试用的
    */


	//prepare.prePareLDA(10354,"E:\\corpus_data\\deal\\en\\no_stop\\","E:\\corpus_data\\deal\\en\\tp\\","lda.en.txt");
	
	
	/*
	String inputfile=null;
	String outputdir=null;
	int ntopics=0;
	if (args.length<3)
	{	
		System.out.print("argument error,-in inputfilename -out outputdir -ntopics x");
		return;
	}
	else
	{
		if (args[0].compareTo("-in")==0)
			inputfile=args[1];
		if (args[2].compareTo("-out")==0)
			outputdir=args[3];
		if (args[4].compareTo("-ntopics")==0)
			ntopics=Integer.parseInt(args[5]);
	}
		
	ldaf.useLDA(inputfile,outputdir,ntopics);
	*/
	
	//ldaf.useLDA("E:\\corpus_data\\deal\\cn\\tp\\lda.cn.txt","E:\\corpus_data\\deal\\cn\\tp\\150_topics\\",150);
	//ldaf.prePareLDA(10354,"E:\\corpus_data\\deal\\en\\no_stop\\","E:\\corpus_data\\deal\\en\\tp\\lda.en.txt");
	//ldaf.useLDA("E:\\corpus_data\\deal\\en\\tp\\lda.en.txt","E:\\corpus_data\\deal\\en\\tp\\");
	//ldaf.prePareLDA(5,"f:\\tpdir\\en\\seg\\no_stop\\","f:\\tpdir\\en\\seg\\tp\\lda.en.txt");
	//ldaf.useLDA("f:\\tpdir\\en\\seg\\tp\\lda.en.txt","f:\\tpdir\\en\\seg\\tp\\");
	
//	for (int i = 61; i < 70; i ++){
//        String fileName = "corups_model_withstem"+i;
//        File file = new File("E:\\biyesheji\\data\\fbis\\corups_model_withstem\\" + fileName);
//        file.mkdir();
//  }
	
//	String nums="10";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="20";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="30";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	nums="40";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="50";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="60";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="70";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="80";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="90";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="100";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="110";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	 nums="120";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_lda\\lda.en.txt","E:\\biyesheji\\data\\fbis\\corups_model\\corups_model"+nums+"\\",Integer.valueOf(nums));
//	
	
//	String nums="10";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="20";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="30";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	nums="40";
//	    ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="50";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="60";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="70";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="80";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="90";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="100";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="110";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	 nums="120";
//		ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//	
//	String nums="59";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//
//nums="61";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="62";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="63";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//nums="64";
//    ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="65";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="66";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//    nums="67";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="68";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="69";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="71";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
	
//	nums="51";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="52";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="53";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//nums="54";
//    ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="55";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="56";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
//    nums="57";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="58";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="69";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
// nums="71";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));
	
//	 nums="200";
//	ldaf.useLDA("E:\\biyesheji\\data\\fbis\\fbis_content_nostop_stem_lda\\lda_en_all_fortrain.dat","E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem"+nums+"\\",Integer.valueOf(nums));

	//ldaf.useLDA("E:\\语料库\\fbis\\lda\\en\\lda_en_all_fortrain_withstem.dat","E:\\语料库\\fbis\\lda\\en\\topicmodel_stem\\"+nums+"\\",Integer.valueOf(nums));
	//ldaf.useLDA("E:\\语料库\\languagemodel\\lda\\en2004.ldatrain.dat","E:\\语料库\\languagemodel\\lda\\"+nums+"\\",Integer.valueOf(nums));
	//ldaf.useLDA("E:\\语料库\\languagemodel\\lda\\en2004_stem.ldatrain.dat","E:\\语料库\\languagemodel\\lda\\stem\\"+nums+"\\",Integer.valueOf(nums));
	//ldaf.useLDA("E:\\语料库\\languagemodel\\lda\\en_train.lda.dat","E:\\语料库\\languagemodel\\lda\\",Integer.valueOf(nums));
	//String sys="E14";
	//ldaf.inferTm("E:\\语料库\\fbis\\lda\\en\\topicmodel\\30\\", "model-final", "lda_en_fortest.dat");
	//ldaf.inferTm("E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga\\", "model-final", sys+".dat");
	//ldaf.inferTm("E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga\\", "model-final", sys+".dat");
	/*
	for(int tempk=1;tempk<23;tempk++){
		if (((tempk>=5) && (tempk<9))||(tempk==10)||(tempk==13)||(tempk>=16) && (tempk<22)) continue;
		String sys="E";
		if (tempk<10)
			sys=sys+"0"+tempk;
		else
			sys=sys+tempk;				
		ldaf.inferTm("E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_giga_stem\\", "model-final", sys+".dat");
		//ldaf.inferTm("E:\\语料库\\LDC2006T04\\mt_chinese_p4\\lda\\en_fbis_stem\\", "model-final", sys+".dat");
	    
	}
	*/
	
	/*
	for(int tempk=1;tempk<15;tempk++){
		if (((tempk>=6) && (tempk<=8))||((tempk>=10) && (tempk<14))) continue;
		String sys="E";
		if (tempk<10)
			sys=sys+"0"+tempk;
		else
			sys=sys+tempk;				
		//ldaf.inferTm("E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_giga_stem\\", "model-final", sys+".dat");
		ldaf.inferTm("E:\\语料库\\LDC2003T17\\mt_chinese_p2\\lda\\en_fbis_stem\\", "model-final", sys+".dat");
	    
	}
	*/
	
}
}
