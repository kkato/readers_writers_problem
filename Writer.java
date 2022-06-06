public class Writer extends Thread {
  int myName; 
  RandomSleep rSleep;  .

  public Writer(int name) {
    myName = name; 
    rSleep = new RandomSleep(); 
  } 

  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Writer " + myName + " wants to write. " + "Beforehand, writecount is "  + Synch.writecount);
      try{
      	Synch.y.acquire();
      }
      catch(Exception e){}

      Synch.writecount++;

      if (Synch.writecount==1){
    	  try{
    		  Synch.rsem.acquire();
    	  }
    	  catch(Exception e){}
      }
      Synch.y.release();

      try {
		Synch.wsem.acquire();
      }
      catch (Exception e) {}

      System.out.println("Writer " + myName + " is now writing.  " + "Writecount is " + Synch.writecount);
      rSleep.doSleep(1, 200);

      Synch.wsem.release();
      try{
        	Synch.y.acquire();
        }
      catch(Exception e){}
	  Synch.writecount--;

	  System.out.println("Writer " + myName + " is finished writing.  " + "Writecount decremented to " + Synch.writecount);
	  if (Synch.readcount==0) Synch.rsem.release();
	  Synch.y.release();

      rSleep.doSleep(1, 1000);
    } 
  } 
}
