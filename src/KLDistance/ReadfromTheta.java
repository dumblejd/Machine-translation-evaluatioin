package KLDistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.experimental.theories.Theories;
import org.omg.CORBA.PUBLIC_MEMBER;

import BLEU.DataTransandAsss;

public class ReadfromTheta 
{


	ArrayList<Theta> Translation;
	ArrayList<Theta> Answer;
	
	public ArrayList<Theta> getTranslation() {
		return Translation;
	}

	public void setTranslation(ArrayList<Theta> translation) {
		Translation = translation;
	}

	public ArrayList<Theta> getAnswer() {
		return Answer;
	}

	public void setAnswer(ArrayList<Theta> answer) {
		Answer = answer;
	}

	
	public ReadfromTheta(int Trans_start_index,int Trans_end_index,int Answer_start_index,int Answer_end_index,String filepath) throws IOException
	{
		//读取某个目录�? �?有目录下的文件（E01，Exx···�?
		Theta tempthe =null;
		
		this.Translation=new ArrayList<Theta>();
		this.Answer=new ArrayList<Theta>();
  		
  		File dir = new File(filepath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        for (int i = 0; i < files.length; i++) 
        {
        	if(files[i].isDirectory())
          {
            String fileName = files[i].getName();
            String[] tempstring= fileName.split("E");
            int tempint=Integer.parseInt(tempstring[1]);
            
            if ((tempint>=Trans_start_index)&&(tempint<=Trans_end_index))
            {
            	tempthe=new Theta();
            	File dir2 = new File(files[i].getAbsolutePath());
                File[] theta = dir2.listFiles(); // 该文件目录下文件全部放入数组
                for(int j=0;j<theta.length;j++)
                {
                	if(theta[j].getName().endsWith("theta"))
                	{
                		ArrayList<double[]> ad=new ArrayList<double[]>();
                		ad=ReadThetafromonefile(theta[j].getAbsolutePath());
                		 tempthe.setData(ad);
	                	 tempthe.setSysid(fileName);
                	}
                }
               this.Translation.add(tempthe);
            }
          
            if ((tempint>=Answer_start_index)&&(tempint<=Answer_end_index))
            {
            	tempthe=new Theta();
            	File dir2 = new File(files[i].getAbsolutePath());
                File[] theta = dir2.listFiles(); // 该文件目录下文件全部放入数组
                for(int j=0;j<theta.length;j++)
                {
                	if(theta[j].getName().endsWith("theta"))
                	{
                		ArrayList<double[]> ad=new ArrayList<double[]>();
                		ad=ReadThetafromonefile(theta[j].getAbsolutePath());
                		 tempthe.setData(ad);
	                	 tempthe.setSysid(fileName);
                	}
                }
               this.Answer.add(tempthe);
            }
          }
        }
		
        prepareforKLD(this.Answer);
        prepareforKLD(this.Translation);
		
    }
	
      public ArrayList<double[]> ReadThetafromonefile(String filepath) throws IOException
      {
    	//读取某个目录下的文件（E01，Exx···�?
  		ArrayList<double[]> templist=new ArrayList<double[]>();


    	InputStreamReader isrr=new InputStreamReader(new FileInputStream(filepath));//这里我去掉了encoding
		BufferedReader bfr = new BufferedReader(isrr);
		String temp=null;
		temp=bfr.readLine();
		while(temp!=null)
		{
			String [] listforline=temp.split(" ");
			double[] numforline=new double[listforline.length];
			for(int q=0;q<listforline.length;q++)
			{
				numforline[q]=Double.valueOf(listforline[q]);
			}
			templist.add(numforline);
			temp=bfr.readLine();
        }
		return templist;
	
	}
      public void prepareforKLD(ArrayList<Theta> transoransw)
      {
    	  //这个函数 set�? Theta类里的double数组  存放�?有数�?
    	  String rowandclumn=null;
    	  int count=0;
    	  double [] tempdouble=null;
    	  for(int i=0;i<transoransw.size();i++)
    	  {
    		  int index=0;
    		  
    		  for(int j=0;j<transoransw.get(i).getData().size();j++)
    		  {
    			  for(int k=0;k<transoransw.get(i).getData().get(j).length;k++)
				  {
    				  if(count==0)
    				  {
			         tempdouble=new double[transoransw.get(i).getData().size()*transoransw.get(i).getData().get(j).length];
			         count++;
		    		  rowandclumn=Integer.toString(transoransw.get(i).getData().size())+"*"+Integer.toString(transoransw.get(i).getData().get(j).length);
    				  }
			          tempdouble[index]=transoransw.get(i).getData().get(j)[k];
			          index++;
				  }
    		  }
    		  count=0;
    		  transoransw.get(i).setData_one_sys(tempdouble);
    		  transoransw.get(i).setrowandcolumn(rowandclumn);
    	  }
      }
	
      
	public static void main(String[] args) throws IOException 
	{
		
		ReadfromTheta r=new ReadfromTheta(9,22,1,4,"E:\\biyesheji\\data\\fbis\\corups_model_withstem\\corups_model_withstem66");
       // System.out.println(r.getAnswer().get(0).getData().get(0)[3]);
		System.out.println(r.getAnswer().size());
		System.out.println(r.getAnswer().get(0).getData().size());
		System.out.println(r.getAnswer().get(0).getData().get(0).length);
		//r.getAnswer().get(0).printdouble();
		System.out.println(r.getAnswer().get(0).getrowandcolumn());
		
	}

}
