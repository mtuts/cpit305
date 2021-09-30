package lab03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReadTextFile {
  public static void main(String[] args) {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader("e:/file1.txt", StandardCharsets.UTF_16));

      String line, content = "";

      while ((line = reader.readLine()) != null) {
        content += line + "\n";
      }

      System.out.println(content);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (reader != null) reader.close();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
