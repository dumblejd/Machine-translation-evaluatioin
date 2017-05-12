package BLEU;

import java.io.IOException;

public class testtt {
	public static void main(String[] args) throws IOException
	{
		LoadTransandAss a=new LoadTransandAss();
		//a.getallfilename("E://biyesheji//data//translation");
		a.read("E://biyesheji//data//translation");
		a.getAllData().get(0).printperData();
		DataprepareforBLEU da=new DataprepareforBLEU(9,22,1,4,a.getAllData());  //ÕýÈ·
		//da.Ngramdata(da.getAnswer(), 1);
		//String qqq=da.getAnswer().get(0).getNgramdata().get(1)[0];
		//System.out.println(qqq);
		//da.Createcontentfile(da.getTranslation(), "E:\\biyesheji\\data\\MT_content");
		da.Createcontentfile(da.Answer, "E:\\biyesheji\\data\\MT_content");
		
		
		
		
		
//		String aString="E08";
//		String[] a=aString.split("E");
//	    double b=Double.valueOf(a[1]);
//	    System.out.println(b);
	}
}
