package lb4theartbeat;

import java.io.Serializable;
import java.util.Date;

/**
 * A Heartbeat of an individual system
 * @author Brandon Ingli
 * @version 24 Feb 2020
 */

public class Heartbeat implements Serializable{

  /** Version number (in a format for Serializable) */
  private static final long serialVersionUID = 1L;

  private String IP;
  private int beatNumber;
  private Date timestamp;
  private int TTL;
  private int nextBeatTime;


  public Heartbeat(String IP, int beatNumber, int TTL, int nextBeatTime) {
    this.IP = IP;
    this.beatNumber = beatNumber;
    this.timestamp = new Date(System.currentTimeMillis());
    this.TTL = TTL;
    this.nextBeatTime = nextBeatTime;
  }

  public Heartbeat(String IP, int beatNumber, Date timestamp, int TTL, int nextBeatTime) {
    this.IP = IP;
    this.beatNumber = beatNumber;
    this.timestamp = timestamp;
    this.TTL = TTL;
    this.nextBeatTime = nextBeatTime;
  }

  /**
   * Get the version of the protocol
   * @return version of the protocol
   */
  public long getVersion(){
    return serialVersionUID;
  }

  /**
   * Get the IP associated with this heartbeat
   * @return IP associated with this heartbeat
   */
  public String getIP() {
    return this.IP;
  }

  /**
   * Get the beat number of this heartbeat
   * @return beat number
   */
  public int getBeatNumber() {
    return this.beatNumber;
  }

  /**
   * Get the timestamp of this Heartbeat
   * @return timestamp of this Heartbeat
   */
  public Date getTimestamp() {
    return this.timestamp;
  }

  /**
   * Get the TTL of this Heartbeat
   * @return TTL of this Heartbeat
   */
  public int getTTL() {
    return this.TTL;
  }

  /**
   * Get the interval until the next expected beat
   * @return interval until the next expected beat
   */
  public int getNextBeatTime() {
    return this.nextBeatTime;
  }

  /**
   * Determine if this Heartbeat should be considered dead
   * @return true if this Heartbeat is dead, false otherwise
   */
  public boolean isDead(){
    Date deadTime = new Date(getTimestamp().getTime() + (getTTL()*1000));
    return deadTime.before(new Date(System.currentTimeMillis()));
  }

  /**
   * Get String representation of this Heartbeat
   * @return String representation of this Heartbeat
   */
  public String toString(){
    return "Beat #" + getBeatNumber() + " from " + getIP() +
            " at " + getTimestamp() + ". TTL: " + getTTL() +
            " seconds. Next Beat in " + getNextBeatTime() + 
            " seconds. Beat is " + (isDead()?"Dead.":"Alive.");
  }


}