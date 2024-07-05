import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private PrintStream out;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            Scanner in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Welcome to the chat!");

            String message;
            while (true) {
                message = in.nextLine();
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
                ChatServer.broadcastMessage(message, this);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ChatServer.removeClient(this);
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
}
