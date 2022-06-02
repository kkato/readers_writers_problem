// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.

import java.util.concurrent.*;

public class Synch {

  public static Semaphore rsem;
  public static Semaphore wsem;
  public static Semaphore x;
  public static Semaphore y;
  public static Semaphore z;
  public static int readcount = 0; //Read Queue
  public static int writecount = 0; //Write Queue

}  // end of class "Synch"
