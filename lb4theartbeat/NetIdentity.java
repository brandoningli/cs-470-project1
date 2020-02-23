package lb4theartbeat;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Class that allows a device to identify itself on the INTRANET.
 * 
 * @author Decoded4620 2016 <https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java>
 * @author Brandon Ingli 2020
 */
public class NetIdentity {

    private String loopbackHost = "";
    private String host = "";

    private String loopbackIp = "";
    private String ip = "";

    private String ipPrefix;

    /**
     * Default constructor assumes a 192.168 Network
     */
    public NetIdentity(){
      this.ipPrefix = "192.168";
      setIPs();
    }

    /**
     * Constructor that specifies a network prefix
     * @param prefix the network prefix to look for. i.e. "192.168" or "150.243"
     */
    public NetIdentity(String prefix){
      this.ipPrefix = prefix;
      setIPs();
    }

    /**
     * Helper function to set the loopback and network addresses.
     */
    private void setIPs(){
      try{
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while(interfaces.hasMoreElements()){
            NetworkInterface i = interfaces.nextElement();
            if(i != null){
                Enumeration<InetAddress> addresses = i.getInetAddresses();
                // System.out.println(i.getDisplayName());
                while(addresses.hasMoreElements()){
                    InetAddress address = addresses.nextElement();
                    String hostAddr = address.getHostAddress();

                    // // local loopback
                    // if(hostAddr.indexOf("127.") == 0 ){
                    //     this.loopbackIp = address.getHostAddress();
                    //     this.loopbackHost = address.getHostName();
                    // }

                    // internal ip addresses (behind this router)
                    if( hostAddr.indexOf(ipPrefix) == 0){
                        this.host = address.getHostName();
                        this.ip = address.getHostAddress();
                    }


                    // System.out.println("\t\t-" + address.getHostName() + ":" + address.getHostAddress() + " - "+ address.getAddress());
                }
            }
        }
      }
      catch(SocketException e){

      }
      try{
          InetAddress loopbackIpAddress = InetAddress.getLocalHost();
          this.loopbackHost = loopbackIpAddress.getHostName();
          this.loopbackIp = loopbackIpAddress.getHostAddress();
          // System.out.println("LOCALHOST: " + loopbackIp);
      }
      catch(UnknownHostException e){
          System.err.println("ERR: " + e.toString());
      }
    }

    /**
     * Get the Loopback Hostname
     * @return Loopback Hostname
     */
    public String getLoopbackHost(){
        return loopbackHost;
    }

    /**
     * Get the Machine's Hostname
     * @return Machine Hostname
     */
    public String getHost(){
        return host;
    }

    /**
     * Get the Machine's network IP
     * @return IP
     */
    public String getIp(){
        return ip;
    }

    /**
     * Get the machine's loopback IP
     * @return Loopback IP
     */
    public String getLoopbackIp(){
        return loopbackIp;
    }

    /**
     * Get string representation of the NetIdentity
     * @return String representation of this NetIdentity
     */
    public String toString(){
        return "Network: " + getHost() + " <" + getIp() + ">; Loopback: " + getLoopbackHost() + " <" + getLoopbackIp() + ">";
    }
}