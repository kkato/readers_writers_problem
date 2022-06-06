// The main method for the readers and writers program.
// Several readers can access the shared data structure simultaneously, or
// one writer can have exclusive access.
//
// There are three types of solutions exist for readers/writers problem:
// (1) Readers get priority.  This means that if there is a constant stream
//     of readers, then a writer can wait indefinitely.  (This is called
//     "starvation" of the writer.)
// (2) Writers get priority.  This means that if there is a constant stream
//     of writers, then a reader can wait indefinitley.
// (3) A starvation-free solution, in which neither readers nor writers
//     wait indefinitely.
// The code given here is for solution (1).

// This code uses
//     class "Reader" from file Reader.java
//     class "Writer" from file Writer.java
//     class "Synch" from file Synch.java

import java.util.concurrent.*;

public class MainMethod {
  public static void main (String argv[]) {

	  Synch.rsem = new Semaphore(1, true);
	  Synch.wsem = new Semaphore(1, true);
	  Synch.x = new Semaphore(1, true);
	  Synch.y = new Semaphore(1, true);
	  Synch.z = new Semaphore(1, true);

    Reader R; 
    Writer W; 

    for (int i=1; i<=8; i++) {
      W = new Writer(i);
            W.start();
      R = new Reader(i);
            R.start();
    }

    System.out.println("This is main speaking");
  } 
}
