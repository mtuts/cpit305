package lab03;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteTextFile {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter("out.txt"));
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
        writer.write(line + "\n");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (writer != null) writer.close();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      scan.close();
    }
  }
}
