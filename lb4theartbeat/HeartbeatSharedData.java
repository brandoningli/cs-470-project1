package lb4theartbeat;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Declares all variables for HeartbeatSharedData
 * @author Chase Anderson
 * @version 1.0
 */
public class HeartbeatSharedData {
  private DatagramSocket socket;
  private ArrayList<String> ipList;
  private ArrayList<String> serverList;
  private String IP;
  private Hashtable<String, Heartbeat> heartHash;
  private int TTL;
  private int TTLMultiplier;
  private boolean isServer;
  private int port;
  private boolean isClientServerMode;


  /**
   * Constructor for HeartbeatSharedData
   * @param socket
   * @param ipList
   * @param serverList
   * @param IP
   * @param heartHash
   * @param TTL
   * @param TTLMultiplier
   * @param isServer
   * @param port
   * @param isClientServerMode
   */
  public HeartbeatSharedData(DatagramSocket socket, ArrayList<String> ipList, ArrayList<String> serverList, String IP, Hashtable<String,Heartbeat> heartHash, int TTL, int TTLMultiplier, boolean isServer, int port, boolean isClientServerMode) {
    this.socket = socket;
    this.ipList = ipList;
    this.serverList = serverList;
    this.IP = IP;
    this.heartHash = heartHash;
    this.TTL = TTL;
    this.TTLMultiplier = TTLMultiplier;
    this.isServer = isServer;
    this.port = port;
    this.isClientServerMode = isClientServerMode;
  }
  

  /**
   * Gets the Socket
   * @return socket
   */
  public DatagramSocket getSocket() {
    return this.socket;
  }

  /**
   * Sets the socket reference
   * @param socket Socket reference
   */
  public void setSocket(DatagramSocket socket) {
    this.socket = socket;
  }
  /**
   * gets
   * @return The array list of strings
   */
  public ArrayList<String> getIPList() {
    return this.ipList;
  }
  /**
   * Setter for IPlist 
   * @param AR
   */
  public void setIPList(ArrayList<String> AR) {
    this.ipList = AR;
  }

  /**
   * Gets the list of servers in priority order 
   * @return serverList
   */
  public ArrayList<String> getServerList() {
    return this.serverList;
  }
  /**
   * Sets the list of servers in priority order
   * @param serverList
   */
  public void setServerList(ArrayList<String> serverList) {
    this.serverList = serverList;
  }

  /**
   * Gets the IP of the current machine
   * @return IP
   */
  public String getIP() {
    return this.IP;
  }
  /**
   * Sets the IP of the current machine
   * @param IP
   */
  public void setIP(String IP) {
    this.IP = IP;
  }
  /**
   * Gets the Hashtable of cached Heartbeats, mapped by IP address
   * @return heartHash
   */
  public Hashtable<String,Heartbeat> getHeartHash() {
    return this.heartHash;
  }
  /**
   * Sets reference of current Heartbeat cache
   * @param heartHash
   */
  public void setHeartHash(Hashtable<String,Heartbeat> heartHash) {
    this.heartHash = heartHash;
  }
  /**
   * Gets TTL
   * @return TTL times the multiplier if this is the server, or just TTL otherwise
   */
  public int getTTL() {
    
    if (isServer()){
     return this.TTL * this.TTLMultiplier;
    }
    else{
    return this.TTL;
    }
  }
  /**
   * Sets TTL
   * @param TTL
   */
  public void setTTL(int TTL) {
    this.TTL = TTL;
  }
  /**
   * Gets the multiplier for TTL if this machine is the server
   * @return TTLMultiplier
   */
  public int getTTLMultiplier() {
    return this.TTLMultiplier;
  }
  /**
   * sets the multiplier for TTL if this machine is the server 
   * @param TTLx
   */
  public void setTTLMultiplier(int TTLx) {
    this.TTLMultiplier = TTLx;
  }
  /**
   * Gets if this machine is the server
   * @return isServer boolean
   */
  public boolean isServer() {
    return this.isServer;
  }
  /**
   * Sets the current boolean value for isServer
   * @param isServer
   */
  public void setIsServer(boolean isServer) {
    this.isServer = isServer;
  }
   /**
   * Gets the port
   * @return port
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Sets the port
   * @param port
   */
  public void setPort(int port) {
    this.port = port;
  }
  /**
   * Gets the max wait time until another Heartbeat is expected
   * @return TTL - 1
   */
  public int getMaxWait()
  {
    return TTL - 1;
  }

  /**
   * Gets if this is in client server mode
   * @return True if C/S, False if P2P
   */
  public boolean isClientServerMode(){
    return isClientServerMode;
  }

  /**
   * Sets/Clears the Client/Server mode flag
   */
  public void setClientServerMode(boolean csm){
    isClientServerMode = csm;
  }
  
  /**
   * String representation of HeartbeatSharedData
   * @return a String of HeartbeatSharedData
   */

  @Override
  public String toString() {
    return "{" +
      " socket='" + getSocket() + "'" +
      ", ipList='" + getIPList() + "'" +
      ", serverList='" + getServerList() + "'" +
      ", IP='" + getIP() + "'" +
      ", heartHash='" + getHeartHash() + "'" +
      ", TTL='" + getTTL() + "'" +
      ", TTLMultiplier='" + getTTLMultiplier() + "'" +
      ", isServer='" + isServer() + "'" +
      ", port='" + getPort() + "'" +
      ", isClientServerMode='" + isClientServerMode() + "'" +
      "}";
  }
  

}
