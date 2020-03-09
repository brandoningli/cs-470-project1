package lb4theartbeat;

import java.io.File;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Driver for a Peer-to-Peer implementation of the heartbeat protocol
 * @author Brandon Ingli
 * @version 29 Feb 2020
 */
public class HeartbeatDriverP2P{

  private static final int PORT_NUMBER = 5555;
  private static final int TTL = 30;
  private static final int TTL_MULTIPLIER = 3;

  public static void main(String[] args) {

    // Print usage information if improper args provided
    if(args.length != 2){
      System.out.println("Usage: HeartbeatDriverP2P <IP file> <IP Prefix>");
      System.out.println("<IP Prefix> is the first part of the IP address for the network interface you want to use.");
      System.exit(-1);
    }

    // Initialize the IP list
    ArrayList<String> ipList = new ArrayList<String>();
    try {
      File ipFile = new File(args[0]);
      Scanner fileScanner = new Scanner(ipFile);
      while (fileScanner.hasNextLine()){
        ipList.add(fileScanner.nextLine());
      }
      fileScanner.close();
    } catch (Exception e) {
      System.err.println("[HeartbeatDriverP2P] Oops. An exception occurred while reading the IPs.");
      e.printStackTrace();
      System.exit(-1);
    }

    // Get this machine's IP
    NetIdentity nid = new NetIdentity(args[1]);

    // Initialize the Server list
    // In P2P, it's just this machine
    ArrayList<String> serverList = new ArrayList<String>();
    serverList.add(nid.getIp());

    // Secure our socket
    DatagramSocket socket = null;
    try {
      socket = new DatagramSocket(PORT_NUMBER);
    } catch (Exception e) {
      System.err.println("[HeartbeatDriverP2P] Oops. An exception occurred while opening the socket.");
      e.printStackTrace();
      System.exit(-1);
    }

    // Create a HashTable for the cache
    Hashtable<String, Heartbeat> localCache = new Hashtable<String, Heartbeat>();
  
    // Create the shared data
    HeartbeatSharedData data = new HeartbeatSharedData(
      socket,
      ipList, // IP List
      serverList, // Server List
      nid.getIp(),
      localCache,
      TTL,
      TTL_MULTIPLIER,
      false, // is Server
      PORT_NUMBER,
      false // is Client Server Mode
    );

    // Initialize a heartbeat for this machine
    Heartbeat thisMachine = new Heartbeat(
      nid.getIp(), // This machine's IP
      0, // Beat number
      TTL,
      data.getMaxWait() // Time until next beat expected
    );
    localCache.put(nid.getIp(), thisMachine);

    // Start up the receive thread
    HeartbeatReceive receiveFrame = new HeartbeatReceive(data);
    Thread receiveThread = new Thread(receiveFrame);
    receiveThread.start();

    // Start up the Printer thread
    HeartbeatStatusPrinter printerFrame = new HeartbeatStatusPrinter(data);
    Thread printerThread = new Thread(printerFrame);
    printerThread.start();

    // Start the Heartbeat Summary send thread
    HeartbeatSummarySend summarySendFrame = new HeartbeatSummarySend(data);
    Thread summarySendThread = new Thread(summarySendFrame);
    summarySendThread.start();

    // Start the Heartbeat send thread
    HeartbeatSend sendFrame = new HeartbeatSend(data);
    Thread sendThread = new Thread(sendFrame);
    sendThread.start();

    // This thread is done. We'll let it gracefully exit.

  }
}