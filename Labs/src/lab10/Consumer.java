package lab10;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

  private BlockingQueue<Object> queue;

  public Consumer(BlockingQueue<Object> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      try {
        //Thread.sleep(100);
        Date d = (Date) queue.take();

        System.out.printf("Consumer consumes %s \t %d\n", d.toString(), queue.size());
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
