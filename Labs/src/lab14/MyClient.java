import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 2000);
    System.out.printf("My Port is: %d\n\n", socket.getLocalPort());

    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    Scanner keyboard = new Scanner(System.in);

    String line = "";
    while (!line.equalsIgnoreCase("bye")) {
      System.out.print("Enter your id: ");
      line = keyboard.nextLine();
      out.println(line); // to be sent to server

      line = in.readLine();
      if (line == null || line.equalsIgnoreCase(":welcome:")) {
        break;
      }
      if (line.equalsIgnoreCase(":try-again:")) {
        System.out.println("ID already used, choose another one!");
      }
    }
    if (line != null && !line.equalsIgnoreCase("bye")) {
      ClientReader cr = new ClientReader(in);
      cr.start();
      while (line != null && !line.equalsIgnoreCase("bye")) {
        System.out.println("Choose from following: ");
        System.out.println("1. Send private message");
        System.out.println("2. Broadcast message");
        System.out.println("3. Exit");
        line = keyboard.nextLine();
        String msg;
        switch (line) {
          case "1":
            System.out.print("Enter client id:");
            String id = keyboard.nextLine();
            System.out.print("Enter your message:");
            msg = keyboard.nextLine();
            out.println(":private:");
            out.println(id);
            out.println(msg);
            break;
          case "2":
            System.out.print("Enter your message:");
            msg = keyboard.nextLine();
            out.println(msg);
            break;
          case "3":
            line = "bye";
            out.println(line); // to be sent to server
            break;
        }
      }
      try {
        cr.join();
        socket.close();
        keyboard.close();
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
