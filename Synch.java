import java.util.concurrent.*;

public class Synch {

  public static Semaphore rsem;
  public static Semaphore wsem;
  public static Semaphore x;
  public static Semaphore y;
  public static Semaphore z;
  public static int readcount = 0;
  public static int writecount = 0;

}
