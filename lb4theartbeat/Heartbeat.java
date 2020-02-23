package lb4theartbeat;

import java.io.Serializable;

public class Heartbeat implements Serializable{

  /** Version number (in a format for Serializable) */
  private static final long serialVersionUID = 1L;

  public long getVersion(){
    return serialVersionUID;
  }

}