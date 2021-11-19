public class Counter extends Thread {
  public static int c = 0;

  @Override
  public void run() {
    for (int i = 0 ; i < 10000; i++) {
      c++;
    }
  }
}
