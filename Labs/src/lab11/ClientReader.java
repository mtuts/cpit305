package lab11;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader extends Thread {
  private BufferedReader reader;

  public ClientReader(BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  public void run() {
    String line = "";
    while (!line.equalsIgnoreCase("bye")) {
      try {
        line = reader.readLine();
        System.out.println(line);
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
