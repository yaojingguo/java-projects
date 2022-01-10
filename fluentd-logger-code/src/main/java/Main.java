import java.util.HashMap;
import java.util.Map;
import org.fluentd.logger.FluentLogger;

public class Main {
  private static FluentLogger LOG = FluentLogger.getLogger("debug.access");

  public static void doApplicationLogic() {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("from", "userA");
    data.put("to", "userB");
    LOG.log("follow", data);
  }

  public static void main(String[] args) {
    doApplicationLogic();
  }
}