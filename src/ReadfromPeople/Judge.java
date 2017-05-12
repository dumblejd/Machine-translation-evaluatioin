package ReadfromPeople;

public class Judge  	//评委类
{
	String Name;
	int [] Count_vote=new int[5];
	double [] Score_forcount=new double[6];    //0-4 放的是 M<x次数  5放的是评分次数总数
	
	
	

	
	public double[] getScore_forcount() {
		return Score_forcount;
	}

	public void setScore_forcount(double[] score_forcount) {
		Score_forcount = score_forcount;
	}

	public Judge(String name, int[] count_vote) {
		Name = name;
		Count_vote = count_vote;
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int[] getCount_vote() {
		return Count_vote;
	}
	public void setCount_vote(int[] count_vote) {
		Count_vote = count_vote;
	}
	
	public void print_count()
	{
		 System.out.print(Name+"	Points 1:"+Count_vote[0]+"	Points 2:"+Count_vote[1]+"	Points 3:"+Count_vote[2]+"	Points 4:"+Count_vote[3]+"	Points 5:"+Count_vote[4]);
		 System.out.println();
		 System.out.print("all:"+Score_forcount[5]);
		 System.out.println();
	}
	
	public void cal_Score_forcount()
	{
		Score_forcount[0]=0;
		Score_forcount[1]=Count_vote[0];
		Score_forcount[2]=Count_vote[0]+Count_vote[1];
		Score_forcount[3]=Count_vote[0]+Count_vote[1]+Count_vote[2];
		Score_forcount[4]=Count_vote[0]+Count_vote[1]+Count_vote[2]+Count_vote[3];
		Score_forcount[5]=Count_vote[0]+Count_vote[1]+Count_vote[2]+Count_vote[3]+Count_vote[4];
	}
	
}
