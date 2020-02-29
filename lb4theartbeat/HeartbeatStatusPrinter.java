package lb4theartbeat;
import java.util.ArrayList;
import java.util.Date;
public class HeartbeatStatusPrinter implements Runnable{

  private HeartbeatSharedData sharedData;

  public HeartbeatStatusPrinter(HeartbeatSharedData sharedData){
    this.sharedData = sharedData;
  }

  public void run(){
    while(!Thread.interrupted()){
      ArrayList<Heartbeat> heartbeats = new ArrayList<Heartbeat>(sharedData.getHeartHash().values());
      Date currentTime = new Date(System.currentTimeMillis());
      System.out.println("Heartbeats as of " + currentTime);
      System.out.println("================");
      for(Heartbeat heartbeat : heartbeats){
        System.out.println(heartbeat);
      }
      System.out.println();

      try{
        Thread.sleep(sharedData.getMaxWait()*1000);
      } catch (InterruptedException e){
        break;
      }
    }
  }
  
}