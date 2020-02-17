import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
  public static void main(String[] args) {
    String hostname = "localhost";
    int portNo = 5555;

    InetAddress toAddress = null;
    try {
      toAddress = InetAddress.getByName(hostname);
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    DatagramSocket socket = null;
    try {
      socket = new DatagramSocket();
    } catch (SocketException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    byte[] buffer = new byte[512];

    DatagramPacket request = new DatagramPacket(buffer, buffer.length, toAddress, portNo);

    try {
      socket.send(request);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    System.out.println("[CLIENT] Sent request to " + hostname + ":" + portNo);

    DatagramPacket response = new DatagramPacket(buffer, buffer.length);
    try {
      socket.receive(response);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    String responseData = new String(buffer);

    System.out.println("[CLIENT] Received response from " + hostname + ":" + portNo + " - " + responseData);

    socket.close();

  }
}