import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class chatServer {

    //Thread-safe list of all connected clients
    List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    ServerSocket serverSocket;
    int port = 12345; // Server listens on this port

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running...");

            while (true) {
                Socket socket = serverSocket.accept(); //accepts incoming clients
                handleClient(socket);
            }

        } catch (Exception e) {
            System.out.println("Server is not running...");
            e.printStackTrace();
        }
    }

    // Creates a handler for each new client
    public void handleClient(Socket socket) {
        ClientHandler clientHandler = new ClientHandler(socket, this);
        clients.add(clientHandler); // Track the new client
        new Thread(clientHandler).start(); //allows to run multiple of the code at the same time
    }




    // Sends a message to all clients except the sender
    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.printWriter.println(message);
            }
        }
    }

    // Removes a client when they disconnect
    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}