package ReadfromPeople;

public class Passage 
{
	String SystemID; //系统编号
	String DocID;//译文文档编号
	double Score;   //计算过程中 存放的是
	
	public Passage(String systemID, String docID, double score) {
		super();
		SystemID = systemID;
		DocID = docID;
		Score = score;
	}
	public Passage(Passage p)
	{
		this.SystemID=p.getSystemID();
		this.DocID=p.getDocID();
		this.Score=p.getScore();
	}
	public String getSystemID() {
		return SystemID;
	}
	public void setSystemID(String systemID) {
		SystemID = systemID;
	}
	public String getDocID() {
		return DocID;
	}
	public void setDocID(String docID) {
		DocID = docID;
	}
	public double getScore() {
		return Score;
	}
	public void setScore(double score) {
		Score = score;
	}

	public void print ()
	{
		System.out.println(SystemID+" "+DocID+" "+Score);
	}

	
	

}
