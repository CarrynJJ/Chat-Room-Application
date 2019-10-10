import java.io.*;
import java.net.*;
import java.util.*;

/**
* ServerThread handles socket connection for specific client
* Gets InputStream and OutputStream
* Prints messages received from sender client InputStream to recipient client OutputStreams
*
* @author Carryn Joseph
*/

public class ServerThread extends Thread
{
    public Socket clientSocket;
    public String username;
    public Scanner inputStream;
    public PrintWriter outputStream;

     /**
    * ServerThread Constructor
    *
    * @param clientSocket - socket of client
    * @param username - name provided by user as type String
    *
    */
    public ServerThread(Socket clientSocket, String username)
    {
        this.clientSocket = clientSocket;
        this.username = username;
    }

    /**
     * Run() method for ServerThread object created
     * Creates inputStream Scanner and outputStream printWriter
     * Prints client joining message to already connected clients
     * Prints client logout message to connect clients
     * Broadcasts messages from client inputStream to recipient client outputStreams
     * 
     */
    @Override
    public void run()
    {
        try {
            inputStream = new Scanner(clientSocket.getInputStream());
            outputStream = new PrintWriter(clientSocket.getOutputStream());
            
            //Notify clients of new user
            for (Socket sckt: Server.clients)
            {
                if (sckt != clientSocket)
                {
                    PrintWriter writer = new PrintWriter(sckt.getOutputStream());
                    writer.println(username + " has joined the chat room");
                    writer.flush();
                }
            }

            while(true)
            {
                if (inputStream.hasNext())
                {
                    String msg = inputStream.nextLine();

                    //notify clients of user leaving chat
                    if (msg.equalsIgnoreCase("logout"))
                    {
                        for (Socket sckt: Server.clients)
                        {
                            if (sckt != clientSocket)
                            {
                                PrintWriter writer = new PrintWriter(sckt.getOutputStream());
                                writer.println(username + " has left the chat room");
                                writer.flush();
                            }
                        }
                        
                        System.out.println(clientSocket + " has disconnected from server");
                        Server.clients.remove(clientSocket);
                        Server.usernames.remove(username);
                        clientSocket.close();
                    }
                    //directed message (unicast and multicast)
                    else if (msg.contains("@"))
                    {
                        String [] msgSplit = msg.split(" ");
                        ArrayList <String> recipients  = new ArrayList <String>();
                        for (String str: msgSplit)
                        {
                            if (str.startsWith("@"))
                            {
                                str = str.replace("@", "");
                                recipients.add(str);
                            }
                        }
                        for (String r: recipients)
                        {
                            if (Server.usernames.contains(r))
                            {
                                PrintWriter writer = new PrintWriter(Server.clients.get(Server.usernames.indexOf(r)).getOutputStream());
                                msg = msg.replace("@" + r+" ", "");
                                writer.println(username + ": " + msg);
                                writer.flush();
                            }
                            else{
                                outputStream.println(r + " is not logged into chat room.");
                                outputStream.flush();
                            }
                        }
                        // for (int i = 0 ; i < Server.clients.size(); i++)
                        // {
                        //     if (msg.contains("@"+Server.usernames.get(i)))
                        //     {
                        //         PrintWriter writer = new PrintWriter(Server.clients.get(i).getOutputStream());
                        //         msg = msg.replace("@"+Server.usernames.get(i), "");
                        //         writer.println(username + ": " + msg);
                        //         writer.flush();
                        //     }
                        // } 
                    }
                    //broadcast message
                    else
                    {
                        for (Socket sckt: Server.clients)
                        {
                            if (sckt != clientSocket)
                            {
                                PrintWriter writer = new PrintWriter(sckt.getOutputStream());
                                writer.println(username + ": " + msg);
                                writer.flush();
                            }
                        }
                    }
                    
                }
            }
        } catch (IOException e) {
            System.out.println("ServerThread error");
        }
    }
}