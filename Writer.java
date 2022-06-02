// This file defines class "writer".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Writer extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Writer.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Writer(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();   // Create and instance of RandomSleep.
  }  // end of the constructor for class "Writer"



  public void run () {
    for (int I = 0;  I < 5; I++) {
      // Get permission to write
      System.out.println("Writer " + myName + " wants to write. "
    		  			 + "Beforehand, writecount is "  + Synch.writecount);
      try{
      	Synch.y.acquire();
      }
      catch(Exception e){}
      // If a reader is active, there is no way to interrupt active readers.
      // Writer(s) have to wait for "wsem" until all these readers finish.

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
      System.out.println("Writer " + myName + " is now writing.  "
                         + "Writecount is " + Synch.writecount);
      rSleep.doSleep(1, 200);
      // We're finished writing.
      // Signal the "rsem" semaphore.
      // When a writer finishes,it wakes up the next waiting writer (if there is one),
      // or it wakes up all the waiting readers (if there are any)
      Synch.wsem.release();
      try{
        	Synch.y.acquire();
        }
      catch(Exception e){}
	  Synch.writecount--;
	  System.out.println("Writer " + myName + " is finished writing.  "
                         + "Writecount decremented to " + Synch.writecount);
	  if (Synch.readcount==0) Synch.rsem.release();
	  Synch.y.release();

      // Simulate "doing something else"
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"
