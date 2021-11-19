public class Demo1 {
  public static void main(String[] args) throws InterruptedException {
    myThread t1 = new myThread(Thread.currentThread());
    System.out.println(t1.getName() + " " + t1.getState());
    t1.start();

    System.out.println(t1.getName() + " " + t1.getState());
    t1.join(5000);

    System.out.println(t1.getName() + " " + t1.getState());

  }
}

