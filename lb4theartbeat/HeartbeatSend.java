package lb4theartbeat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * Sends this machine's heartbeat to the highest priority server that's alive
 * @author Brandon Ingli 2020
 * @version 1.0
 */
public class HeartbeatSend implements Runnable{

  private HeartbeatSharedData data;
  private int beatNumber = 0;
  private Random random = new Random();

  public HeartbeatSend(HeartbeatSharedData data){
    this.data = data;
  }

  public void run(){

    while(!Thread.interrupted()){ // If this thread is interrupted, we should probably stop
      int waitTime = randomTime(); // How long we'll wait after sending this
      
      // Generate a Heartbeat and a Packet to wrap it in
      Heartbeat beat = new Heartbeat(data.getIP(), ++beatNumber, data.getTTL(), waitTime);
      ArrayList<Heartbeat> beats = new ArrayList<Heartbeat>(2);
      beats.add(beat);
      HeartbeatPacket packet = new HeartbeatPacket(data.isClientServerMode(), data.getIP(), beats);

      // Update our local cache with this beat so we know we're stayin' alive
      data.getHeartHash().put(data.getIP(), beat);

      // Determine the highest priority alive server
      String serverIP = data.getServerList().get(data.getServerList().size() - 1);
      for(String ip : data.getServerList()){
        Heartbeat serverBeat = data.getHeartHash().get(ip);
        if(serverBeat != null && !serverBeat.isDead()){
          serverIP = ip;
          break;
        }
      }
      
      // Send the packet
      try {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        InetAddress serverInet = InetAddress.getByName(serverIP);
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(packet);
        oo.close();

        byte[] buffer = bStream.toByteArray();
        DatagramPacket beatPacket = new DatagramPacket(buffer, buffer.length, serverInet, data.getPort());

        data.getSocket().send(beatPacket);

      } catch (Exception e){
        System.err.print("[HeartbeatSend] ");
        e.printStackTrace();
      }

      try {
        Thread.sleep(waitTime * 1000);
      } catch (InterruptedException e) {
        return;
      }

    }
    
  }

  /** 
   * Generates a random next heartbeat time from 1 to maxWait inclusive 
   * @return random interval until next heartbeat
  */
  private int randomTime(){
    return random.nextInt(data.getMaxWait() - 1) + 1;
  }
  
}