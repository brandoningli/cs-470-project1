import java.io.IOException;
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

    String data = "Hello!";
    buffer = data.getBytes();

    DatagramPacket response = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());

    try {
      socket.send(response);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    
    System.out.println("[SERVER] Set response to " + request.getAddress() + ":" + request.getPort());

    socket.close();
    
  }
}