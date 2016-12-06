import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket client;
        client = serverSocket.accept();
        System.out.println("Connected to :" + client.getLocalSocketAddress());
        String inputString;
        BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter os = new PrintWriter(client.getOutputStream());
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Received client: " + is.readLine());
        inputString = sin.readLine();
        while (inputString != null && !inputString.trim().equals("quit")) {
            os.println(inputString);
            os.flush();
            System.out.println("Server send: " + inputString);
            System.out.println("Client send: " + is.readLine());
            inputString = sin.readLine();
            os.close();
            is.close();
        }
        client.close();
        System.out.println("Chat over");
    }
}
