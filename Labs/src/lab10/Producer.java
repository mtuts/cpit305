package lab10;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

  private BlockingQueue<Object> queue;

  public Producer(BlockingQueue<Object> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      Date d = new Date();
      System.out.printf("Producer produce %s, \t %d\n", d.toString(), queue.size());
      try {
        queue.put(d);
//        Thread.sleep(100);
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
