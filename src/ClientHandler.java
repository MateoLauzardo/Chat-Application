import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    Socket socket;
    chatServer chatServer;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public ClientHandler(Socket socket, chatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Continuously reads messages from client and broadcasts them
    @Override
    public void run() {
        try {
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                chatServer.broadcastMessage(message, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            chatServer.removeClient(this);
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}