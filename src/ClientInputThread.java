import java.io.*;
import java.net.*;
import java.util.*;

/**
* ClientInputThread handles user inputs
* Gets InputStream and OutputStream
* Prints user input to outputStream
*
* @author Carryn Joseph
*/

public class ClientInputThread extends Thread
{
    public Socket clientSocket;
    public String username;
    public Scanner inputStream;
    public PrintWriter outputStream;

    /**
    * ClientInputThread Constructor
    *
    * @param clientSocket - socket of client
    * @param username - name provided by user as type String
    *
    */

    public ClientInputThread(Socket clientSocket, String username)
    {
        this.clientSocket = clientSocket;
        this.username = username;
    }


    /**
     * Run() method for ClientInputThread object created
     * Creates inputStream Scanner and outputStream printWriter
     * Scans for user input and prints to OutputStream
     * 
     */
    @Override
    public void run()
    { 

        try {
            
            inputStream = new Scanner(clientSocket.getInputStream());
            outputStream = new PrintWriter(clientSocket.getOutputStream());

            Scanner sc = new Scanner(System.in);
            String inputmsg = sc.nextLine();

            
            while (true)
            {
                    outputStream.println(inputmsg);
                    outputStream.flush();
                    inputmsg = sc.nextLine();

                    //logout process
                    if (inputmsg.equalsIgnoreCase("logout"))
                    {
                        outputStream.println(inputmsg);
                        outputStream.flush();
                        System.out.println("Log out successful");
                        System.exit(0);
                    }                
            }
        } catch (IOException e) {
            System.out.println("ClientInputThread error");
        }
    }
}