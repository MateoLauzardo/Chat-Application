public class ClientMain {
    public static void main(String[] args) {
        chatClient client = new chatClient();
        try {
            client.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}