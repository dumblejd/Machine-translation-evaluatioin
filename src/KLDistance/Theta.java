package KLDistance;

import java.util.ArrayList;

public class Theta 
{

	String sysid;
	ArrayList<double[]> data;
	double[] data_one_sys; 
	String rowandcolumn;  //66*100
	
	public String getrowandcolumn() {
		return rowandcolumn;
	}

	public void setrowandcolumn(String rowandcolumn) {
		this.rowandcolumn = rowandcolumn;
	}
	
	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public ArrayList<double[]> getData() {
		return data;
	}

	public void setData(ArrayList<double[]> data) {
		this.data = data;
	}

	public double[] getData_one_sys() {
		return data_one_sys;
	}

	public void setData_one_sys(double[] data_one_sys) {
		this.data_one_sys = data_one_sys;
	}
	public Theta(String sysid, ArrayList<double[]> data, double[] data_one_sys) {
		this.sysid = sysid;
		this.data = data;
		this.data_one_sys = data_one_sys;
	}
	public Theta() {
		this.sysid = null;
		this.data = null;
		this.data_one_sys = null;
	}

	public void print()
    {
  	  for(int i=0;i<this.data.size();i++)
  	  {
  		 for(int j=0;j<this.data.get(i).length;j++)
  		 {
  			 System.out.print(this.data.get(i)[j]+" ");
  			 
  		 }
  		 System.out.println();
  	  }
    }

	public void printdouble()
    {
  	  for(int i=0;i<this.data_one_sys.length;i++)
  	  {
  		
  			 System.out.println(data_one_sys[i]+" ");
  	  }
    }
 
}
