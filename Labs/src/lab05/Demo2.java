package lab05;

import java.util.Scanner;

public class Demo2 {
  public static void main(String[] args) {
    SecondDemo t1 = new SecondDemo("T1");
    SecondDemo t2 = new SecondDemo(".....T2");

    t1.start();
    t2.start();
    Scanner scan = new Scanner(System.in);
    scan.nextLine(); // when user press enter thread 1 will be interrupted
    t1.interrupt();
    scan.nextLine();
    t2.interrupt();
    scan.close();
  }
}
