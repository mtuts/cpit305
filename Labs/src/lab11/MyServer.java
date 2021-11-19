package lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {
  private static ArrayList<ServerClient> clients;

  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(2000);

    clients = new ArrayList<>();

    while (true) {
      System.out.printf("Waiting for clients on port %d...\n", server.getLocalPort());
      Socket client = server.accept();

      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
      String id = "";
      while (!id.equalsIgnoreCase("bye")) {
        id = reader.readLine();
        if (!idExists(id)) {
          break;
        }
        writer.println(":try-again:");
      }
      if (!id.equalsIgnoreCase("bye")) {
        writer.println(":welcome:");

        ServerClient c = new ServerClient(id, reader, writer, client);
        clients.add(c);
        c.start();
      } else {
        client.close();
      }
    }
  }

  private static boolean idExists(String id) {
    for (int i = 0; i < clients.size(); i++) {
      if (clients.get(i).getClientId().equalsIgnoreCase(id)) {
        return true;
      }
    }
    return false;
  }

  public static synchronized void  removeClient(ServerClient client) {
    clients.remove(client);
  }

  public static void sendMessage(String from, String cid, String msg) {
    for (int i = 0; i < clients.size(); i++) {
      if (clients.get(i).getClientId().equalsIgnoreCase(cid)) {
        clients.get(i).sendMessage("Private message from [" + from + "]: " + msg);
        break;
      }
    }
  }

  public static void broadcast(String from, String msg) {
    for (int i = 0; i < clients.size(); i++) {
      if (!clients.get(i).getClientId().equalsIgnoreCase(from))
        clients.get(i).sendMessage("from[ " + from + "] \t" + msg);
    }
  }
}
