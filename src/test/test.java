package test;

import java.util.ArrayList;

import ReadfromPeople.Correlation;
import ReadfromPeople.LoadAssessment;
import ReadfromPeople.LoadScoresofMachineAssessment;
import ReadfromPeople.ScoresforPassage;

public class test {

	public static void main(String[] args)
	{
		LoadAssessment la=new LoadAssessment("E://biyesheji//data//mt_chinese_easy.txt");
		la.Load();
//		la.Printarrylist();
//		la.PrintJudgelist();
		ScoresforPassage sp = new ScoresforPassage();
		sp.cal_Score(la.getAss_list(), la.getJudge_list());  //测试成功  能够把同一文章的所有评价拉出
		sp.sort();
		//sp.PrintPassagelist();
		//sp.saveinfile("E://biyesheji//data//result.txt");
		LoadScoresofMachineAssessment lsfma=new LoadScoresofMachineAssessment("E://biyesheji//BLEU-doc.scr");
		lsfma.Load();
		Correlation c=new Correlation(0);
		//la.PrintJudgelist();
		c.cal_correlation(sp.getPassage_list(), lsfma.getPassage_list());
		System.out.println("correlation:"+c.getCorrelation());
	}
}
