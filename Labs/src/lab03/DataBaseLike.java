package lab03;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataBaseLike {
  public static void main(String[] args) {
    DataOutputStream dos = null;

    try {
      dos = new DataOutputStream(new FileOutputStream("binary.dat"));

      dos.writeInt(1443);
      dos.writeDouble(456.89);
      dos.writeByte(125);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      if (dos != null) {
        try {
          dos.close();
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
}
