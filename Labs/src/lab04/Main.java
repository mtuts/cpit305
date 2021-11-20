package lab04;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

  private final static int NAME_SIZE = 15;
  private final static long RECORD_SIZE = 1 + 4 + NAME_SIZE + 4; // flag , id, name, gpa

  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);
    RandomAccessFile raf = new RandomAccessFile("mydb.db", "rw");
    while (true) {
      System.out.println("Welcome to our DB:");
      System.out.println("1. Add New");
      System.out.println("2. Edit by id");
      System.out.println("3. List all data");
      System.out.println("4. Search by record");
      System.out.println("5. Delete record");

      System.out.println("10. Exit");
      System.out.print("Your choice: ");
      String choice = scan.nextLine();

      if (choice.equals("1")) {
        // add new
        // move to the first deleted record or to the end of file
        raf.seek(0);
        long pos = 0;
        byte flag = 0;
        if (raf.length() > 1) {
          flag = raf.readByte();
          System.out.println(raf.length());
          while (flag != 0 && pos <= raf.length() - RECORD_SIZE) {
            pos += RECORD_SIZE;
            raf.seek(pos);
            flag = raf.readByte();
            System.out.printf("pos: %d, flag: %d\n", pos, flag);
          }
        }
        if (flag == 0 || pos >= raf.length() - RECORD_SIZE) {
          if (pos >= raf.length()) {
            raf.seek(raf.length());
          } else {
            raf.seek(pos);
          }
          System.out.printf("pos: %d, flag: %d, len: %d\n", pos, flag, raf.length());
          enterDataWithID(raf, scan);
        }
      } else if (choice.equals("2")) {
        long pos = 0;
        raf.seek(pos);
        System.out.print("Which ID you want to search for?");
        int search_id = Integer.parseInt(scan.nextLine());

        while (pos < raf.length()) {
          byte flag = raf.readByte();
          if (flag == 1) {
            int id = raf.readInt();
            if (id == search_id) {
              // update name and gpa
              enterData(raf, scan);
              break;
            }
          }
          // jump to next record
          pos = pos + RECORD_SIZE;
          raf.seek(pos);
        }
      } else if (choice.equals("3")) {
        long pos = 0;
        raf.seek(pos);
        while (pos < raf.length()) {
          byte flag = raf.readByte();
          if (flag == 1) {
            int id = raf.readInt();
            String name = readFixedString(raf, NAME_SIZE);
            float gpa = raf.readFloat();

            System.out.printf("%8d \t%-15s \t%.2f\n", id, name, gpa);
          } else {
            raf.skipBytes((int)(RECORD_SIZE - 1));
          }
          pos += RECORD_SIZE;
        }
      } else if (choice.equals("4")) {
        System.out.print("Which Record do you want to search for (Record starts from 1)? ");
        int rcd = Integer.parseInt(scan.nextLine());
        long pos = (rcd - 1) * RECORD_SIZE;
        if (pos < raf.length()) {
          raf.seek(pos);
          byte flag = raf.readByte();
          while (flag != 1 && pos < raf.length()) {
            raf.skipBytes((int) (RECORD_SIZE - 1));
            flag = raf.readByte();
            pos += RECORD_SIZE;
          }
          if (pos < raf.length()) {
            int id = raf.readInt();
            String name = readFixedString(raf, NAME_SIZE);
            float gpa = raf.readFloat();

            System.out.printf("%8d \t%-15s \t%.2f\n", id, name, gpa);
          } else {
            System.out.println("Record out of boundary.");
          }
        } else {
          System.out.println("Record out of boundary.");
        }

      } else if (choice.equals("5")) {
        System.out.print("Which Record do you want to delete (Record starts from 1)? ");
        int rcd = Integer.parseInt(scan.nextLine());
        long pos = (rcd - 1) * RECORD_SIZE;
        raf.seek(pos);
        byte flag = raf.readByte();
        while (flag != 1 && pos < raf.length()) {
          raf.skipBytes((int) (RECORD_SIZE - 1));
          flag = raf.readByte();
          pos += RECORD_SIZE;
        }
        if (pos < raf.length()) {
          raf.seek(pos);
          raf.writeByte(0);
          int id = raf.readInt();
          String name = readFixedString(raf, NAME_SIZE);
          float gpa = raf.readFloat();

          System.out.printf("%8d \t%-15s \t%.2f\n", id, name, gpa);
          System.out.println("Record has been removed.");
        }
      } else if (choice.equals("10")) {
        break;
      }

    }

    raf.close();



  }

  private static String readFixedString(RandomAccessFile raf, int size) throws IOException {
    byte[] buffer = new byte[size];
    raf.read(buffer);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      if (buffer[i] == 0) break;
      builder.append((char) buffer[i]);
    }
    return builder.toString();
  }

  static void enterDataWithID(RandomAccessFile raf, Scanner scan) throws IOException {
    System.out.print("Enter Student ID: ");
    int id = Integer.parseInt(scan.nextLine());
    raf.writeByte(1); // not deleted
    raf.writeInt(id);

    enterData(raf, scan);
  }

  static void enterData(RandomAccessFile raf, Scanner scan) throws IOException {
    System.out.print("Enter Student Name: ");
    String name = scan.nextLine();

    System.out.print("Enter Student GPA: ");
    float gpa = Float.parseFloat(scan.nextLine());

    raf.write(getFixedString(name, NAME_SIZE));
    raf.writeFloat(gpa);
  }

  static byte[] getFixedString(String str, int size) {
    byte[] data = new byte[size];
    if (str.length() > size) {
      str = str.substring(0, size - 1);
    }

    for (int i = 0; i < size; i++) {
      if (str.length() <= i) {
        data[i] = 0;
      } else {
        data[i] = (byte) str.charAt(i);
      }
    }

    return data;
  }
}
