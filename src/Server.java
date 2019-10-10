import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server class is the control class for Server
 * Listens and accepts socket requests 
 * Creates socket threads for each accepted socket connection
 * controls socket threads
 * 
 * @author Carryn Joseph
 */
public class Server
{
    public static ArrayList <Socket> clients = new ArrayList <Socket>();
    public static ArrayList <String> usernames = new ArrayList <String> ();
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12048);
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket + " connected to server");
            //Scanner inputScanner = new Scanner(clientSocket.getInputStream());
            //String username = inputScanner.nextLine();
            InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String username = br.readLine();

            clients.add(clientSocket);
            usernames.add(username);

            ServerThread serverThread = new ServerThread(clientSocket, username);
            serverThread.start();

        }
    }
}
