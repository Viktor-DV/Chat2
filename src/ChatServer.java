import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ChatServer {
    private static Vector<Client> clients = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            Client client = new Client(socket);
            clients.add(client);
            Thread thread = new Thread(client);
            thread.start();
        }
    }

    static void broadcastMessage(String message, Client sender) {
        for (Client client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(Client client) {
        clients.remove(client);
    }
}
