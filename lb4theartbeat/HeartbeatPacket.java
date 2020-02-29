package lb4theartbeat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Packet for the lb4t Heartbeat Protocol
 * @author Brandon Ingli
 * @version 24 Feb 2020
 */

public class HeartbeatPacket implements Serializable{

  /** Version number (in a format for Serializable) */
  private static final long serialVersionUID = 1L;

  private boolean isClientServer;
  private boolean isSummary;
  private String senderIP;
  private ArrayList<Heartbeat> heartbeats;

  public HeartbeatPacket(boolean isClientServer, String senderIP, ArrayList<Heartbeat> heartbeats){
    this.isClientServer = isClientServer;
    this.isSummary = (heartbeats.size() > 1);
    this.senderIP = senderIP;
    this.heartbeats = heartbeats;
  }

  /**
   * Get the version of the protocol
   * @return version of the protocol
   */
  public long getVersion(){
    return serialVersionUID;
  }

  /**
   * Determine if this packet is in Client Server or P2P mode
   * @return true if in Client Server mode, false if P2P mode
   */
  public boolean isClientServer() {
    return this.isClientServer;
  }

  /**
   * Determine if this packet is a summary or an individual beat
   * @return true if more than one beat is contained, false otherwise
   */
  public boolean isSummary() {
    return this.isSummary;
  }

  /**
   * Get the IP sending this packet
   * @return IP sending this packet
   */
  public String getSenderIP() {
    return this.senderIP;
  }

  /**
   * Get a reference to the ArrayList of Heartbeats
   * @return reference to the ArrayList of Heartbeats
   */
  public ArrayList<Heartbeat> getHeartbeats() {
    return this.heartbeats;
  }

  /**
   * Get String representation of this Heartbeat
   * @return String representation of this Heartbeat
   */
  public String toString() {
    String toReturn = "{" +
      " isClientServer='" + isClientServer() + "'" +
      ", isSummary='" + isSummary() + "'" +
      ", senderIP='" + getSenderIP() + "'" +
      ", heartbeats='";
      for(Heartbeat h : getHeartbeats()){
        toReturn += "{" + h.toString() + "}, ";
      }
      toReturn += "'}";
      return toReturn;
  }
  
  
}
