package lab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {

  public static void main(String[] args)  {
    if (args.length != 2) {
      System.err.println("Usage: java CopyBytes <source-file> <target-file>");
      return;
    }

    FileInputStream source = null;
    FileOutputStream target = null;

    try {
      source = new FileInputStream(args[0]);
      target = new FileOutputStream(args[1]);
      int c;
      while ((c = source.read()) != -1) {
        target.write(c);
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
      try {
        if (target != null) target.close();
      } catch (IOException e) {
        System.err.println("Can't close the stream");
      }
    }

  }
}
