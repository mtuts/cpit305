package lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ServerClient extends Thread {
    private Socket client;

    private String id;
    private BufferedReader reader;
    private PrintWriter writer;

    public ServerClient(String id, BufferedReader reader, PrintWriter writer, Socket client) {
        this.client = client;
        this.id = id;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {

        System.out.printf("Client at %s:%d\n\n", client.getInetAddress().getHostAddress(), client.getPort());

        try {
            String line = "";
            while (!line.equalsIgnoreCase("bye")) {
                line = reader.readLine();
                if (line.startsWith(":private:")) {
                    String cid = reader.readLine(); // client id
                    String msg = reader.readLine(); // private message

                    MyServer.sendMessage(id, cid, msg);
                } else if (!line.equalsIgnoreCase("bye")) {
                    MyServer.broadcast(id, line);
                } else {
                    writer.println("bye");
                }
            }

            client.close();
        }catch (NoSuchElementException e) {
            System.err.println("Client left!!!");
        }catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            MyServer.removeClient(this);
        }
    }

    public String getClientId() {
        return id;
    }

    public void sendMessage(String msg) {
        writer.println(msg);
    }
}
