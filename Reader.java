public class Reader extends Thread {
  int myName; 
  RandomSleep rSleep; 

  public Reader(int name) {
    myName = name; 
    rSleep = new RandomSleep();
  }

  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + " wants to read.  " + "Beforehand, readcount is "  + Synch.readcount);

      try{
      	Synch.z.acquire();
      	try {
      		Synch.rsem.acquire();
      		try {
      			Synch.x.acquire();
      		}
      		catch(Exception e) {}

            Synch.readcount++;

            if (Synch.readcount==1){
          	  try{
          		  Synch.wsem.acquire();
          	  }
          	  catch(Exception e){}
            }
            Synch.x.release();
      	}
      	catch(Exception e){}
        Synch.rsem.release();
      }
      catch(Exception e){}

      System.out.println("Reader " + myName + " is now reading.  " + "Readcount is " + Synch.readcount);
      Synch.z.release();
      rSleep.doSleep(1, 200);

      try{
      	Synch.x.acquire();
      }
      catch(Exception e){}
      Synch.readcount--;

      System.out.println("Reader " + myName + " is finished reading.  " + "Readcount decremented to " + Synch.readcount);
      if (Synch.readcount==0) Synch.wsem.release();
      Synch.x.release();

      rSleep.doSleep(1, 1000);
    } 
  } 
}
