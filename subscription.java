package subscription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class subscription {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		Scanner sc= new Scanner(System.in);  
		System.out.print("Enter amount to charge per invoice!");  
		double price= sc.nextDouble(); 
		
		
		Scanner category= new Scanner(System.in);  
		System.out.print("Enter the subscription type DAILY, WEEKLY or MONTHLY.!");  
		
		String startDate = getTimeStamp("yyyy-MM-dd");
		String endDate = getDatePlusMonth(startDate,"yyyy-MM-dd",+3,"yyyy-MM-dd");
		
		String sub = category.nextLine();
		
		  switch(sub)
	        {
	            case "DAILY":
	            	daily(startDate,endDate,price);
	                break;
	            case "WEEKLY":
	            	weekly(endDate,price);
	                break;
	            case "MONTHLY":
	            	monthly(startDate,endDate,price);
	                break;
	            default:
	                System.out.println("no match");
	        }
		
		
	}
	
	public static void weekly(String endDate,double price) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		 Date dt = c.getTime();
		 DateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
         String startDate = ndf.format(dt);
		
         long MAX_DAY = getDayDiff(startDate,endDate);
         
         int startDay = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "dd"));
 		 MAX_DAY = MAX_DAY + startDay;
 		 
 		 int counter=0;
 		 for (int days = startDay; days <= MAX_DAY; days++) {
			
 			 
				int month = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "MM"));
				int year = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "yyyy"));
								
			  // will auto adjust date example 2021-01-32 => 2021-02-01
		      String thisDate = formatDateString(year+"-"+month+"-"+days, "yyyy-MM-dd", "dd/MM/yyyy");

		      if(counter == 0) {
		    	  System.out.println("Dates " + thisDate + " " + price);
			  }
		      
		      counter++;
		      
		      if (counter == 7) {
		    	  counter=0;
		      }
		      
		 }
		
	}
	
	public static void monthly(String startDate,String endDate,double price) {
		
         long MAX_DAY = getDayDiff(startDate,endDate);
         
         int startMonth = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "MM"));
         int startYear = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "yyyy"));
  		
         int maxMonth = startMonth+2;
         
 		 for (int months = startMonth; months <= maxMonth; months++) {
			

			  // will auto adjust date example 2021-01-32 => 2021-02-01
		      String thisDate = formatDateString(startYear+"-"+months+"-"+"01", "yyyy-MM-dd", "yyyy-MM-dd");

				int totalDaysSelectedMonth = getTotalDaysInMonth(thisDate);

		      
		      String endOfMonth = formatDateString(startYear+"-"+months+"-"+totalDaysSelectedMonth, "yyyy-MM-dd", "dd/MM/yyyy");

		    	  System.out.println("Dates " + endOfMonth + " " + price);
			  
		     
		      
		 }
		
	}
	
	
	public static void daily(String startDate, String endDate, double price) {
		long MAX_DAY = getDayDiff(startDate,endDate);
		
		int startDay = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "dd"));
		
		MAX_DAY = MAX_DAY + startDay;
		
		 for (int days = startDay; days <= MAX_DAY; days++) {
				
				int month = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "MM"));
				int year = Integer.parseInt(formatDateString(startDate, "yyyy-MM-dd", "yyyy"));
								
			  // will auto adjust date example 2021-01-32 => 2021-02-01
		      String thisDate = formatDateString(year+"-"+month+"-"+days, "yyyy-MM-dd", "dd/MM/yyyy");

		      System.out.println("Dates " + thisDate + " " + price);
		      
		 }
		
	}
	
	 public static int getTotalDaysInMonth(String dateString) {
			java.util.Date date=new java.util.Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			try{
				date=df.parse(dateString);
			}catch(Exception e){}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			int datestr = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
	    return datestr;
	  }
	
	
	 public static String getTimeStamp(String format) {
		    Calendar calendar = Calendar.getInstance();
		    SimpleDateFormat df = new SimpleDateFormat(format);
		    df.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
				String date = df.format(calendar.getTime());
		    df=null;
		    return date;
		  }
	 
	 
	 public static String getDatePlus(String dateString,String currentFormat,int x,String expectedFormat) {
		    // for GMT+8:00
		      //gets date plus or minus x days
		      java.util.Date date=new java.util.Date();
		      SimpleDateFormat df=new SimpleDateFormat(currentFormat);
		      try{
		        date=df.parse(dateString);
		      }catch(Exception e){}

		      Calendar calendar = Calendar.getInstance();
		      calendar.setTime(date);
		      calendar.add(Calendar.DATE,x);
		      df = new SimpleDateFormat(expectedFormat);
		      String datestr = df.format(calendar.getTime());

		    return datestr;
		  }
	 
	 public static String getDatePlusMonth(String dateString,String currentFormat,int x,String expectedFormat) {
		    // for GMT+8:00
		      //gets date plus or minus x months
		      java.util.Date date=new java.util.Date();
		      SimpleDateFormat df=new SimpleDateFormat(currentFormat);
		      try{
		        date=df.parse(dateString);
		      }catch(Exception e){}

		      Calendar calendar = Calendar.getInstance();
		      calendar.setTime(date);
		      calendar.add(Calendar.MONTH,x);
		      df = new SimpleDateFormat(expectedFormat);
		      String datestr = df.format(calendar.getTime());

		    return datestr;
		  }
	
	 public static long getDayDiff(String dateStart, String dateStop) {

		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  

				Date d1 = null;
				Date d2 = null;
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
				} catch (Exception e) {
						System.out.println("Dates: getDayDiff: "+e);
				}    

				// Get msec from each, and subtract.
				long diff = d2.getTime() - d1.getTime();
				final int ONE_DAY = 1000 * 60 * 60 * 24;

		    long d = diff / ONE_DAY;
				return d;
			}
	 
	 public static String formatDateString(String dateString,String currentFormat,String newFormat){
		    Date date=new Date();
		    SimpleDateFormat df=new SimpleDateFormat(currentFormat);
		    String result="";

		    try{
		      date=df.parse(dateString);
		      df=new SimpleDateFormat(newFormat);
		      result=df.format(date);
		    }catch (Exception e){}

		    return result;
		  }
	

}
