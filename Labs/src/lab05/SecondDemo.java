package lab05;

public class SecondDemo extends Thread {
  String s;

  public SecondDemo(String name) {
    s = name;
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
//    while (!Thread.currentThread().isInterrupted()) {
      System.out.println(s);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

    }

    if (Thread.currentThread().isInterrupted()) {
      System.out.println(getName() + " terminated");
    } else {
      System.out.println(getState());
    }
  }
}
