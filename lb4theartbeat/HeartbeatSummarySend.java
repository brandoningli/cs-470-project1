package lb4theartbeat;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.Random;

/**
 * Sends a Summary of this node's local cache to all other nodes
 * @author Chase Anderson
 * @version 1.0
 */
public class HeartbeatSummarySend implements Runnable {

  private HeartbeatSharedData data;
  private Random random = new Random();

  public HeartbeatSummarySend(HeartbeatSharedData data) {
    this.data = data;
  }

  public void run() {
    while (!Thread.interrupted()) {
      ArrayList<Heartbeat> beats = new ArrayList<Heartbeat>(data.getHeartHash().values());

      // Create a HeartbeatPacket
      HeartbeatPacket packet = new HeartbeatPacket(data.isClientServerMode(), data.getIP(), beats);
      
      try {
        // Serialize the packet
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput byteOutput = new ObjectOutputStream(bStream);
        byteOutput.writeObject(packet);
        byteOutput.close();
        byte[] buffer = bStream.toByteArray();
        
        // Try to send it to everyone but ourself
        for (String ip : data.getIPList()) { 
          if (!ip.equals(data.getIP())) {
            // Send the packet to its destination!
            InetAddress receiverAddress = InetAddress.getByName(ip);
            DatagramPacket dPac = new DatagramPacket(buffer, buffer.length, receiverAddress, data.getPort());
            data.getSocket().send(dPac);
          }
        }
      } catch(Exception e){
        // Don't exit quite yet. We'll try again next time. 
        System.err.println("[HeartbeatSummarySend] Error serializing and sending the HeartbeatPacket.");
        e.printStackTrace();
      }

      // Wait to send the next one
      try {
        Thread.sleep(waitTime() * 1000); 
        
      } catch (InterruptedException e) {
        // The thread was interrupted while asleep. Just return.
        return;
      }

    }
  }

  /**
   * Generates a random next heartbeat time from maxWait 
   * @return random interval until next heartbeat
   */
  private int waitTime() {
    return random.nextInt(data.getMaxWait() / 2 - 1) + 1;
  }
}
