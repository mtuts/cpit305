package lab03;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CopyText {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java CopyText <source-file> <target-file>");
      return;
    }

    FileReader source = null;
    PrintWriter target = null;

    try {
      source = new FileReader(args[0]);
      target = new PrintWriter(args[1]);

      while (source.ready()) {
        target.write(source.read());
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Something went wrong while read/write from/on file " + e.getMessage());
    } finally {
      try {
        if (source != null) source.close();
      } catch (IOException e) {
        System.err.println("Can't close the stream");
      }
      if (target != null) target.close();
    }
  }
}
