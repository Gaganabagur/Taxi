

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

      Taxi[] ta=new Taxi[4];// array of taxis with number of taxy is 4
      ta[0]=new Taxi();
      ta[1]=new Taxi();
      ta[2]=new Taxi();
      ta[3]=new Taxi();
      Booking[] b=new Booking[10];// array of bookings
      int i=0,j=0;
      Scanner sc=new Scanner(System.in);
      while(true)
      {
      System.out.println("____________CALL TAXI BOOKING_____________");
      System.out.println("1)Booking");
      System.out.println("2)Display");
      System.out.println("3)exit");
      System.out.println("Enter your choice");
      int ch=sc.nextInt();
      
      if(ch>3 || ch<1){System.out.println("Invalid Input");return;}
      if(ch==1)
      {
         System.out.println("Input "+(i+1)+":");
         
         System.out.println("Customer Id");
         int id=sc.nextInt();
         
         System.out.println("Pickup Point:");
         sc.nextLine();
         char pick=sc.nextLine().charAt(0);
         
         System.out.println("Drop Point:");
         char drop=sc.nextLine().charAt(0);
         
         System.out.println("Pickup Time:");
         int PickTime=sc.nextInt();
         
        b[i]=new Booking(id,pick,drop,PickTime);// create a booking objec and send these items in to  it
        // as soon as previous line is executed booking constrctor is called
        
        	int a;
         a = b[i].isAvailable(ta);// is available is called b[i] is 
         
        System.out.println("ouput "+(i+1));
        if(a!=-1)
        {
               System.out.println("Taxi-"+(a+1)+" is alloted");
               
               ta[a].calculateEarnings(b[i].pickupPoint,b[i].dropPoint);
               
               b[i].dropTime();
               b[i].calculateEarnings();
        }
        else System.out.println("Booking is rejected");
        i++;
      }
      else if(ch==2)
      {
          System.out.format("%-10s%-10s\n","Taxi No:","Total Earnings:");
          System.out.format("%-10s%-13s%-10s%-10s%-13s%-10s%-10s\n","BookingID","CustomerId","From","To","PickupTime","DropTime","Amount");
          System.out.println("output");
          for(int k=0;k<4;k++)
          {
              if(ta[k].earnings!=0)
              {
                  System.out.println("Taxi-"+(k+1)+"    "+"Total Earnings:"+"Rs . "+ta[k].earnings);
                  for(j=0;j<i;j++)
                  {
                      if(b[j].taxino==k)
                      {                       
                          System.out.format("%-10d%-10d%-10c%-10c%-10d%-10d%-10d\n",(j+1),b[j].customerId,
                            b[j].pickupPoint,b[j].dropPoint,b[j].pickupTime,b[j].dropTime,b[j].earnings);
                      }                     
                  }
              }
          }
      }
      else if(ch==3)
      return;
      }
     
}
    
}

 class Taxi {
    char initialPoint;
    int destinationTime;
    int earnings;
    public Taxi()
    {
        initialPoint='A';
    }
    
    public void departureTime(int pickTime,char pick,char drop)
    {
        this.destinationTime=(pickTime+(Math.abs(pick-drop)));
    }
    
    public void calculateEarnings(char pick,char drop)
    {
        int dist=(Math.abs(pick-drop)*15);
        int amount=((dist-5)*10)+100;
        this.earnings=earnings+amount;
    }
       
  
}
 class Booking {
   int customerId;
   char pickupPoint;
   char dropPoint;
   int pickupTime;
   int dropTime;
   int earnings;
   int taxino;
   Taxi[] t;
  

    Booking(int id, char pick, char drop, int PickTime) {
       customerId=id;
       pickupPoint=pick;
       dropPoint=drop;
       pickupTime=PickTime;
    }
    
    public void dropTime()
    {
        this.dropTime=(pickupTime+(Math.abs(pickupPoint-dropPoint))); 
    } 
    
    public void calculateEarnings()
    {
        int dist=(Math.abs(pickupPoint-dropPoint)*15); 
        this.earnings=((dist-5)*10)+100;     
    }

   public int isAvailable(Taxi[] t) 
   {
       int j,min=6,temp=-1;// 6 loc 
       for(j=0;j<4;j++)// iterate through each taxi and check if it is available or not
       {
          if(Math.abs(pickupPoint-t[j].initialPoint)<=min && t[j].destinationTime<=pickupTime)
          {
        	  
        	  // value of j means 1 taxi at a time
              if(temp==-1 || Math.abs(pickupPoint-t[j].initialPoint)<min ) // temp==--1 checked in only 1 stj loop in 
            	 // next j reast of the OR condition will e checked
                 temp=j;
             if(Math.abs(pickupPoint-t[j].initialPoint)==min && t[j].earnings!=0) 
             {
            	 
                 if(t[temp].earnings>t[j].earnings) 
                 temp=j;
             }
             min= Math.abs(pickupPoint-t[j].initialPoint);
             
          }
       }
       if(min!=6){
           t[temp].departureTime(pickupTime,pickupPoint,dropPoint);
           t[temp].initialPoint=dropPoint;
           taxino=temp;
           return temp;} 
       return -1;
   }
} 