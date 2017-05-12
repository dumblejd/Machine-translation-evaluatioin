package ReadfromPeople;

public class Assessment    //��ί�����ļ�������
{
	String SystemID; //ϵͳid
	String DocID;//�����ĵ����
	String Judge;//������
	String GoldStandardID;//�����õı�׼���ı��
	String SegID;//�����
	double FluencyJudge;//������
	double AdequacyJudge;//׼ȷ��
	String Correction;//��������
	String JudgeTime;//����ʱ��
	
	public Assessment(String systemID, String docID,String Judge, String goldStandardID, String segID, double fluencyJudge,
			double adequacyJudge, String Correction, String judgeTime) {
		this.SystemID = systemID;
		this.DocID = docID;
		this.Judge=Judge;
		this.GoldStandardID = goldStandardID;
		this.SegID = segID;
		this.FluencyJudge = fluencyJudge;
		this.AdequacyJudge = adequacyJudge;
		this.Correction = Correction;
		this.JudgeTime = judgeTime;
	}
	
	public void print()
	{
		System.out.print(SystemID+",");
		System.out.print(DocID+",");
		System.out.print(Judge+",");
		System.out.print(GoldStandardID+",");
		System.out.print(SegID+",");
		System.out.print(FluencyJudge+",");
		System.out.print(AdequacyJudge+",");
		System.out.print(Correction+",");
		System.out.println(JudgeTime);
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
	public String getJudge() {
		return Judge;
	}
	public void setDocID(String docID) {
		DocID = docID;
	}
	public String getGoldStandardID() {
		return GoldStandardID;
	}
	public void setGoldStandardID(String goldStandardID) {
		GoldStandardID = goldStandardID;
	}
	public void setJudge(String judge) {
		Judge =  judge;
	}
	public String getSegID() {
		return SegID;
	}
	public void setSegID(String segID) {
		SegID = segID;
	}
	public double getFluencyJudge() {
		return FluencyJudge;
	}
	public void setFluencyJudge(double fluencyJudge) {
		FluencyJudge = fluencyJudge;
	}
	public double getAdequacyJudge() {
		return AdequacyJudge;
	}
	public void setAdequacyJudge(double adequacyJudge) {
		AdequacyJudge = adequacyJudge;
	}
	public String getCorrection() {
		return Correction;
	}
	public void setCommentonForA(String correction) {
		Correction = correction;
	}
	public String getJudgeTime() {
		return JudgeTime;
	}
	public void setJudgeTime(String judgeTime) {
		JudgeTime = judgeTime;
	}
	

	
}
