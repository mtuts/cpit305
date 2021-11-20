package lab03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class UsingPrintWriter {
  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("out2.txt");
      while (true) {
        System.out.println("Choose form following: ");
        System.out.println("1. write more line");
        System.out.println("2. exit");
        System.out.print("Enter your choice: ");
        String line = scan.nextLine();
        if (line.equals("2")) {
          break;
        } else if (!line.equals("1")) {
          System.out.println("Wrong choice, try again");
          continue;
        }

        System.out.print("Type your entry: ");
        line = scan.nextLine();
        writer.println(line);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      if (writer != null) writer.close();
      scan.close();
    }
  }
}
