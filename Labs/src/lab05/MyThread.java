package lab05;

public class MyThread extends Thread {
  Thread q;
  public MyThread(Thread t) {
    q = t;
  }

  @Override
  public void run() {
    int x = 0;
    for (int i = 0; i < 10000; i++) {
      System.out.println("Marwan");
    }
    System.out.println(q.getName() + " - " + q.getState());
  }
}