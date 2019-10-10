import java.io.*;
import java.net.*;
import java.util.*;

/**
* ClientOutputThread prints messages received from InputStream
* Gets InputStream and OutputStream
* Prints messages receiver in InputStream
*
* @author Carryn Joseph
*/

public class ClientOutputThread extends Thread
{
    public Socket clientSocket;
    public String username;
    public Scanner inputStream;
    public PrintWriter outputStream;

    /**
    * ClientOutputThread Constructor
    *
    * @param clientSocket - socket of client
    * @param username - name provided by user as type String
    *
    */
    public ClientOutputThread(Socket clientSocket, String username)
    {
        this.clientSocket = clientSocket;
        this.username = username;
    }

    /**
     * Run() method for ClientOutputThread object created
     * Creates inputStream Scanner and outputStream printWriter
     * Listens for messages received by InputStream
     * Prints for messages received by InputStream
     * 
     */
    @Override
    public void run()
    {
        try {
            inputStream = new Scanner(clientSocket.getInputStream());
            outputStream = new PrintWriter(clientSocket.getOutputStream());
            
            while (true)
            {
                if (inputStream.hasNext())
                {
                    String msg = inputStream.nextLine();
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("ClientOutputThread error");
        } 
        
    }
}