package BLEU;

import java.util.ArrayList;

public class DataTransandAsss 
{
	String Systemid;
	String Docid;
	ArrayList<String> Seg;
	ArrayList<String[]> Seperate=new ArrayList<String[]>();
	ArrayList<String[]> Ngramdata=new ArrayList<String[]>();   //放n元分割好的单词组合0
	
	public ArrayList<String[]> getNgramdata() {
		return Ngramdata;
	}
	public void setNgramdata(ArrayList<String[]> ngramdata) {
		Ngramdata = ngramdata;
	}

	
	public ArrayList<String[]> getSeperate() {
		return Seperate;
	}
	public void setSeperate(ArrayList<String[]> seperate) {
		Seperate = seperate;
	}
	public String getSystemid() {
		return Systemid;
	}
	public void setSystemid(String systemid) {
		Systemid = systemid;
	}
	public String getDocid() {
		return Docid;
	}
	public void setDocid(String docid) {
		Docid = docid;
	}
	public ArrayList<String> getSeg() {
		return Seg;
	}
	public void setSeg(ArrayList<String> seg) {
		Seg = seg;
	}
	public DataTransandAsss(String systemid, String docid, ArrayList<String> seg) {
		Systemid = systemid;
		Docid = docid;
		Seg = seg;
		ArrayList<String[]> Seperate=new ArrayList<String[]>();
		ArrayList<String[]> Ngramdata=new ArrayList<String[]>();
	}
	public void printperData()
	{
		System.out.println("systemid:"+this.Systemid+"  docid:"+this.Docid);
		for(int i=0;i<this.Seg.size();i++)
		{
			System.out.println("Seg "+(i+1)+""+this.Seg.get(i));
		}
	}

	
}
