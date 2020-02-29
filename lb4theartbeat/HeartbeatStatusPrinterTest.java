package lb4theartbeat;

import java.util.Hashtable;
import java.util.Date;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.net.SocketException;

public class HeartbeatStatusPrinterTest{
  public static void main(String[] args) throws SocketException {
    NetIdentity nident = new NetIdentity("150.243.");
    Heartbeat test1 = new Heartbeat(
      nident.getIp(),
      1,
      30,
      29
    );
    Heartbeat test2 = new Heartbeat(
      "150.143.0.0",
      1,
      30,
      15
    );

    Hashtable<String, Heartbeat> hh = new Hashtable<String, Heartbeat>();
    hh.put(test1.getIP(), test1);
    hh.put(test2.getIP(), test2);

    HeartbeatSharedData data = new HeartbeatSharedData(
      new DatagramSocket(5555), //Socket
      new ArrayList<String>(), //ipList
      new ArrayList<String>(), //Server List
      nident.getIp(),
      hh,
      30,
      3,
      false,
      5555
    );

    HeartbeatStatusPrinter print = new HeartbeatStatusPrinter(data);
    Thread t = new Thread(print);
    t.run();
  }
}