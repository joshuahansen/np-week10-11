import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.zip.*;

/** Server Class.
* Gets input from client, converts to uppercase and sends back.
* blocking server using serversocketchannel and buffers.
*/
class Task1Server
{
    public static void main(String[] args) throws IOException
    {
        try {
            //port for server client to transmit data
            int serverPort = 10007;
            //create ServerSocket connections
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(serverPort));
            serverSocket.configureBlocking(true);
            
            System.out.println("Server waiting for connection");
            SocketChannel clientSocket = serverSocket.accept();
            System.out.println("Server connected to Client " + clientSocket);
            System.out.println("Incoming connection from: " + clientSocket.socket().getRemoteSocketAddress());
            
            ByteBuffer outputBuffer = ByteBuffer.allocate(100);
            outputBuffer.clear();
            ByteBuffer inputBuffer = ByteBuffer.allocate(100);
            inputBuffer.clear();
            
            System.out.println("Get input from client");
            String inputLine;
            //while there is input from client loop
            while (clientSocket.isConnected())
            {
                int bytesRead = clientSocket.read(inputBuffer);
                inputBuffer.flip();
                
                byte[] temp = new byte[bytesRead];
                System.arraycopy(inputBuffer.array(), 0 , temp, 0, bytesRead);
                inputLine = new String(temp);
                //empty buffer and reset to start
                Arrays.fill(inputBuffer.array(), (byte) 0);
                inputBuffer.clear(); 
                System.out.println("Server: " + inputLine);
                String upperCase = inputLine.toUpperCase();
                outputBuffer.put(upperCase.getBytes());
                outputBuffer.flip();
                //if input is terminating 'X' character reply to client and break loop
                if(inputLine.equalsIgnoreCase("X"))
                {
                    clientSocket.write(outputBuffer);
                    break;
                }
                //send reply to client
                while(outputBuffer.hasRemaining())
                {
                    clientSocket.write(outputBuffer);
                }
                //empty buffer and reset to start
                Arrays.fill(outputBuffer.array(), (byte) 0);
                outputBuffer.clear();
            }
            //close connection with client
            System.out.println("Connection Closed");
            //out.close();
            //in.close();
            clientSocket.close();
            serverSocket.close();
        }catch(IOException ex)
        {
            System.out.println("Error with Input Output: " + ex);
        }
    }
}
