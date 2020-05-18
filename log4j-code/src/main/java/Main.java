import org.apache.log4j.Logger;

public class Main {
  private static Logger log = Logger.getLogger(Main.class);

  public static void main(String[] args) {
    System.out.printf("main start\n");
    log.info("a log message");
    System.out.printf("main end\n");
  }
}
