package exampleprogram;
import java.io.Serializable;

public class Message implements Serializable {
  /**
   * Serial Version ID Required by the JVM to be happy.
   */
  private static final long serialVersionUID = 1L;

  private int messageNo;
  private String messageText;

  public Message(int no, String text){
    messageNo = no;
    messageText = text;
  }

  public String toString(){
    return "[#" + messageNo + "] " + messageText;
  }
}