import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class chatClient {

    Socket socket;
    BufferedReader inFromServer;
    PrintWriter printWriter;

    public void startClient() {
        try {
            socket = new Socket("localhost", 12345);
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);


            // Thread for receiving messages
            new Thread(() -> {
                try {
                    String message;
                    while ((message = inFromServer.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


            // Thread for sending messages
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String message = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(message)) {
                        socket.close();
                        break;
                    }
                    printWriter.println(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to connect to server.");
            e.printStackTrace();
        }
    }//end of method
}