import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket server;
        String inputString;
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input server IP address:");
        String serverAddress = sin.readLine();
        server = new Socket(serverAddress.equals("") ? serverAddress : "localhost", 9999);
        System.out.println("Connected to " + serverAddress);
        PrintWriter os = new PrintWriter(server.getOutputStream());
        BufferedReader is = new BufferedReader(new InputStreamReader(server.getInputStream()));
        inputString = sin.readLine();
        while (inputString != null && !inputString.trim().equals("quit")) {
            os.println(inputString);
            os.flush();
            System.out.println("Client Send: " + inputString);
            System.out.println("Server Response: " + is.readLine());
            inputString = sin.readLine();
        }
        os.close();
        is.close();
        server.close();
        System.out.println("Chat over");
    }
}
