package ReadfromPeople;

public class Passage 
{
	String SystemID; //ϵͳ���
	String DocID;//�����ĵ����
	double Score;   //��������� ��ŵ���
	
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
