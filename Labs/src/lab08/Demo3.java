public class Demo3 {
  public static void main(String[] args) throws InterruptedException {
    Counter counter1 = new Counter();
    Counter counter2 = new Counter();
    counter1.start();
    counter2.start();

    counter1.join();
    counter2.join();

    System.out.println(Counter.c);
  }
}
