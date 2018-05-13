import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.zip.*;

class task2Client
{
    public static void main(String[] args)
    {
        //server Host name
        String serverHostname = "127.0.0.1";
        //server ports
        int serverPort = 10007;
        //display details for each server port
        displayDetails(serverPort);
        try {
            //create ServerSocket connections
            SocketChannel echoSocket = new SocketChannel.open();
            echoSocket.connect(new InetSocketAddress(serverHostname, serverPort));
            echoSocket.configureBlocking(false);

            //create buffer to send data to server
            ByteBuffer outputBuffer = ByteBuffer.allocate(100);
            buffer.clear();

            //create buffers for keyboard and server inputs
            ByteBuffer inputBuffer = ByteBuffer.allocate(100);
            InputStream keyboardInputStream = System.in;
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(keyboardInputStream));
            String userInput;
            //while client inputs data loop
            while ((userInput = keyboardInput.readLine()) != null)
            {
                //print clients input
                System.out.println("Client: " + userInput);
                //send data to server
                outputBuffer.put(userInput.getBytes())l
                outputBuffer.flip();
                while(outputBuffer.hasRemaining())
                {
                    echoSocket.write(outputBuffer);
                }
                echoScoket.read(inputBuffer)
                //if server responds with terminating 'X' character break loop 
                if(server.equals("X"))
                {
                    System.out.println("Server: " + server);
                    System.out.println("Close Connection");
                    break;
                }
                else
                    System.out.println("Server: " + server);
            }
            //close connection to server
            output.close();
            input.close();
            keyboardInput.close();
            echoSocket.close();

        }catch(IOException ex)
        {
            System.out.println("Error with input/output: " + ex);
        }
    }
    //display details for a port number
    public static void displayDetails(int port)
    {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface netIf : Collections.list(nets))
            {
                displayAddress(netIf);
                System.out.println("Port: " + port);
            }
        }catch(SocketException ex)
        {
            System.out.println("Falled to get Network Interface details: " + ex);
        }
    }
    //display host name and address
    public static void displayAddress(NetworkInterface netIf)
    {
            Enumeration<InetAddress> inetAddresses = netIf.getInetAddresses();
            for(InetAddress address : Collections.list(inetAddresses))
            {
                try {
                    System.out.println("Host Name: " + address.getLocalHost().getHostName());
                }
                catch(UnknownHostException ex)
                {
                    System.out.println("Unknown Host: " + ex);
                }
                System.out.println("Host Address: " + address);
            }
    }
}
