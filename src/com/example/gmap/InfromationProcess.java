
package com.example.gmap;
import java.util.ArrayList;
public class InfromationProcess{

	/**
	 * @param args
	 */
	public String sendinfor=null;
	String receiveinfro=null;
	objectinfo objectinfor1,objectinfor2,objectinfor3;
	
	public ArrayList<objectinfo> objectlist= new ArrayList<objectinfo>();
	
	public InfromationProcess(String send_infor) throws Exception {
	
	 sendinfor=send_infor;	
	System.out.println("myclient");
     MyClient myClient=new MyClient(sendinfor);
     receiveinfro=myClient.receiveinfor;
     
	
	
     ProcessInfor(receiveinfro);
     

	}
	
	
	 public void ProcessInfor(String Infromation) {
		// TODO Auto-generated constructor stub
		String inforString=Infromation;
		String inforArrayString[]=inforString.split(",");
		String description=inforArrayString[0];
		
		
		
		if(inforArrayString.length==1){
			
			System.out.println("No Valid Helper Around");
		}
		
		if(inforArrayString.length==7){
			
		    objectinfor1=new objectinfo(inforArrayString[1],inforArrayString[2],
		    		inforArrayString[3],inforArrayString[4],
		    		inforArrayString[5],inforArrayString[6]
		    		);
		
		  objectlist.add(objectinfor1);
		  System.out.println("arraylist 1");
		
		}
		
	   if(inforArrayString.length==13){
		   
			
		   objectinfor1=new objectinfo(inforArrayString[1],inforArrayString[2],
		    		inforArrayString[3],inforArrayString[4],
		    		inforArrayString[5],inforArrayString[6]
		    		);
		   objectinfor2=new objectinfo(inforArrayString[7],inforArrayString[8],
		    		inforArrayString[9],inforArrayString[10],
		    		inforArrayString[11],inforArrayString[12]
		    				
		    		);
		
		   System.out.println("arraylist 2");
		  objectlist.add(objectinfor1);
		  objectlist.add(objectinfor2);
					   
	   }
	   
	   if(inforArrayString.length==19){
		   
		   objectinfor1=new objectinfo(inforArrayString[1],inforArrayString[2],
		    		inforArrayString[3],inforArrayString[4],
		    		inforArrayString[5],inforArrayString[6]
		    		);
		   objectinfor2=new objectinfo(inforArrayString[7],inforArrayString[8],
		    		inforArrayString[9],inforArrayString[10],
		    		inforArrayString[11],inforArrayString[12]
		    		);
		   objectinfor3=new objectinfo(inforArrayString[13],inforArrayString[14],
		    		inforArrayString[15],inforArrayString[16],
		    		inforArrayString[17],inforArrayString[18]
		    		);
		   System.out.println("arraylist 3");
		  objectlist.add(objectinfor1);
		  objectlist.add(objectinfor2);
		  objectlist.add(objectinfor3);
		   
	   }

		
	  
	}

}
