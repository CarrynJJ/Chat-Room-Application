
import java.io.*;
import java.net.*;
import java.util.*;

/**
* Client class is the control class for the client
* Requests connection to server
* Requests username from user and sends to server via OutputStream
* Initiates ClientInputThread and ClientOutputThread

* @author Carryn Joseph
*/

public class Client
{
    public static Socket clientSocket;
    public static String username;
    public static void main(String[] args) throws IOException {
        try {
            //Connection request to server
            clientSocket = new Socket("localhost", 12048);

            System.out.println("Welcome to the Chat Room Application\nEnter username:");
            
            //Create username and sned to server
            Scanner sc = new Scanner(System.in);
            username = sc.nextLine();
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println(username);
            pw.flush();
            
            ClientInputThread clientInputThread = new ClientInputThread(clientSocket, username);
            clientInputThread.start();
            ClientOutputThread clientOutputThread = new ClientOutputThread(clientSocket, username);
            clientOutputThread.start();

        } catch (ConnectException e) {
            System.out.println("Server not found\nTerminating program");
            System.exit(0);
        }
    }
}
