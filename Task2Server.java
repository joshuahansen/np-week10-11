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
* non-blocking server using serversocketchannel and buffers.
* use selector to know when operation has finished.
*/
class Task2Server
{
    public static void main(String[] args) throws IOException
    {
        try {
            //port for server client to transmit data
            int serverPort = 10007;
            //create ServerSocket connections and open selector
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(serverPort));
            serverSocket.configureBlocking(false);
            //register selector with socket
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            
            System.out.println("Server waiting for connection");
            
            //create bytebuffers
            ByteBuffer outputBuffer = ByteBuffer.allocate(100);
            outputBuffer.clear();
            ByteBuffer inputBuffer = ByteBuffer.allocate(100);
            inputBuffer.clear();

            boolean exit = false;
            //loop while not exit
            while(!exit)
            {
                //select selector and get iterator
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                //loop while there are more keys
                while(iter.hasNext()) 
                {
                    SelectionKey key = iter.next();
                    //if theres a connection register client to selector and print client details
                    if(key.isAcceptable())
                    {
                        SocketChannel clientSocket = serverSocket.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                        System.out.println("Server connected to Client " + clientSocket);
                        System.out.println("Incoming connection from: " + clientSocket.socket().getRemoteSocketAddress());
                    }
                    //if theres input from the client modify and echo back
                    if(key.isReadable())
                    {
                        //get socketChannel from key
                        SocketChannel clientSocket = (SocketChannel) key.channel();
                        int bytesRead = clientSocket.read(inputBuffer);
                        inputBuffer.flip();
                        
                        byte[] temp = new byte[bytesRead];
                        System.arraycopy(inputBuffer.array(), 0, temp, 0, bytesRead);
                        String inputLine = new String(temp);
                        //empty buffer and reset to start
                        Arrays.fill(inputBuffer.array(), (byte) 0);
                        inputBuffer.clear(); 
                        System.out.println("Server: " + inputLine);
                        String upperCase = inputLine.toUpperCase();
                        outputBuffer.put(upperCase.getBytes());
                        outputBuffer.flip();
                        //if input is terminating 'X' character reply to client and break loop
                        if(inputLine.equalsIgnoreCase("x"))
                        {
                            clientSocket.write(outputBuffer);
                            exit = true;
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
                    //remove current key from set
                    iter.remove();
                }
            } 
            //close connection with client
            System.out.println("Connection Closed");
            //out.close();
            //in.close();
            serverSocket.close();
        }catch(IOException ex)
        {
            System.out.println("Error with Input Output: " + ex);
        }
    }
}
