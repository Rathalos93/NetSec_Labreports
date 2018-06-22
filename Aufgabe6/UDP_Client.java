import java.io.*;
import java.net.*;
import java.util.*;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {

      DatagramSocket clientSocket = new DatagramSocket(9999);
      InetAddress IPAddress = InetAddress.getByName("10.1.1.1");
      
      ArrayList<String> alreadyReceived = new ArrayList<>();
      
      int c = 0;
      while(c < 200)
      {
         byte[] receiveData = new byte[2048];
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(receivePacket);
         String modifiedSentence = new String(receivePacket.getData());
      
         c++;
         
         if(!alreadyReceived.contains(modifiedSentence))
         {
           System.out.println("FROM SERVER:" + modifiedSentence);
           alreadyReceived.add(modifiedSentence);
         }
      }
      clientSocket.close(); 
   }
}
