import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
  public static void main(String[] args) {
    int portNo = 5555;

    DatagramSocket socket = null;
    try {
      socket = new DatagramSocket(portNo);
    } catch (SocketException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    byte[] buffer = new byte[512];

    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
    try {
      socket.receive(request);
    } catch (IOException e1) {
      e1.printStackTrace();
      System.exit(-1);
    }

    System.out.println("[SERVER] Got request from " + request.getAddress() + ":" + request.getPort());

    Message toSend = new Message(1, "Hello, there!");
    
    // Serialize to a byte array
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    ObjectOutput oo;
    try {
      oo = new ObjectOutputStream(bStream);
      oo.writeObject(toSend);
      oo.close();
    } catch (IOException e1) {
      e1.printStackTrace();
      System.exit(-1);
    }

    byte[] buffer2 = bStream.toByteArray();

    DatagramPacket response = new DatagramPacket(buffer2, buffer2.length, request.getAddress(), request.getPort());

    try {
      socket.send(response);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    
    System.out.println("[SERVER] Sent response to " + request.getAddress() + ":" + request.getPort());

    socket.close();
    
  }
}