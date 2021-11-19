import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

  public static void main(String[] args) {
    BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);

    Consumer consumer = new Consumer(queue);
    Producer producer = new Producer(queue);

    consumer.start();
    producer.start();
  }
}
