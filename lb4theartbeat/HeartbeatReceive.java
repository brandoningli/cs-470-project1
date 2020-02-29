package lb4theartbeat;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class HeartbeatReceive implements Runnable{

  private HeartbeatSharedData data;
  private HeartbeatSummarySend hss;
  private Thread t;

  public HeartbeatReceive(HeartbeatSharedData data) {
    this.data = data;
    this.hss = new HeartbeatSummarySend(data);
    this.t = new Thread(hss);
  }


  public void run(){
    while(!Thread.interrupted()){
      byte[]  buffer = new byte[65508];

      DatagramPacket received = new DatagramPacket(buffer, buffer.length);
      try {
        // Get the packet
        data.getSocket().receive(received);
        ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
        HeartbeatPacket packet = (HeartbeatPacket) iStream.readObject();
        iStream.close();

        // Update the cache if the new one is newer
        for (Heartbeat heartbeat : packet.getHeartbeats()) {
          Heartbeat cached = data.getHeartHash().get(heartbeat.getIP());
          if (cached == null || (cached != null && cached.getTimestamp().before(heartbeat.getTimestamp()))){
            data.getHeartHash().put(heartbeat.getIP(), heartbeat);
          }
        }

        // IF CS and Ind'l and HSS not running, start one.
        if(data.isClientServerMode() && !packet.isSummary() && !t.isAlive()){
          data.setIsServer(true);
          t = new Thread(hss);
          t.start();
        }

        // IF CS and Summary, and HSS running, kill it
        if(data.isClientServerMode() && packet.isSummary() && t.isAlive()){
          data.setIsServer(false);
          t.interrupt();
        }  
              
      } catch (Exception e) {
        System.err.print("[HeartbeatReceive]");
        e.printStackTrace();
        continue;
      }

      

    }
  }
  
}