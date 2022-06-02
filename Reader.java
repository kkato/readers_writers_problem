// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"



  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + " wants to read.  "
                         + "Beforehand, readcount is "  + Synch.readcount);
      // Get permission to read
      try{
      	Synch.z.acquire();
      	try {
      		Synch.rsem.acquire();
      		try {
      			Synch.x.acquire();
      		}
      		catch(Exception e) {}
      		// If a writer is active, no reader may start.
      		// Readers wait for "rsem" until writer finishes.
      		// If a new reader arrives when a writer is waiting or a writer is active,
      		// the reader must wait.
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
      // Now we have permission to start reading.
      // Print a message and release z.
      System.out.println("Reader " + myName + " is now reading.  "
                         + "Readcount is " + Synch.readcount);
      Synch.z.release();

      // Simulate the time taken for reading
      rSleep.doSleep(1, 200);

      // We're finished reading.
      // Decrement readcount.
      // If we are the last reader, then signal "wsem".
      // The signal to "wsem" will wake up a waiting writer.
      // If reader(s) are active and no writer is waiting,
      // a new reader is allowed to start
      try{
      	Synch.x.acquire();
      }
      catch(Exception e){}
      Synch.readcount--;
      System.out.println("Reader " + myName + " is finished reading.  "
                         + "Readcount decremented to " + Synch.readcount);
      if (Synch.readcount==0) Synch.wsem.release();
      Synch.x.release();


      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"
