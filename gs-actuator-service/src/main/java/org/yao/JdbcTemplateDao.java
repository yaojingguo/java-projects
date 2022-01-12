package org.yao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcTemplateDao {
  @Autowired private JdbcTemplate template;

  private Random random = new Random();

  @Autowired private DataSource dataSource;

  public List<Map<String, Object>> classes() {
    System.out.printf("dataSource: %s\n", dataSource);
    List<Map<String, Object>> result = template.queryForList("select * from school_class limit 2");
    return result;
  }

  public String holdConnections() {
    new Thread(() -> {
      template.execute(new MyCallback());
    }).start();
    return "OK";
  }

  private static class MyCallback implements ConnectionCallback {

    @Override
    public Object doInConnection(final Connection con) throws SQLException, DataAccessException {
      Statement stmt = con.createStatement();
      ResultSet rs =
        stmt.executeQuery("select * from school_class limit 2");
      System.out.printf("result set:\n");
      while (rs.next()) {
        System.out.println("  school_id: " + rs.getInt(1) + ", class_code: " + rs.getString(2));
      }
      int millis = 2000;
      System.out.printf("sleeping %d milliseconds...\n", millis);
      Util.snap(millis);
      return null;
    }
  }
  public String start() {
    new Thread() {
      @Override
      public void run() {
        execute();
      }
    }.start();
    return "started";
  }

  public void execute() {
    for (; ; ) {
      for (int i = 0; i < 20; i++) {
        new Thread() {
          @Override
          public void run() {
            classes();
          }
        }.start();
      }
      Util.snap((random.nextInt(10) + 1) * 100);
    }
  }

  public String stress() {
    new Thread() {
      @Override
      public void run() {
        for (; ; ) {
          for (int i = 0; i < 20; i++) {
            new Thread() {
              @Override
              public void run() {
                Connection conn = null;
                try {
                  conn = dataSource.getConnection();
                  System.out.printf("data source: %s\n", dataSource);
                  Util.snap(5000);
                } catch (Exception ex) {
                  ex.printStackTrace();
                  System.exit(10);
                  // throw new RuntimeException(ex);
                } finally {
                  if (conn != null) {
                    try {
                      conn.close();
                    } catch (SQLException e) {
                      e.printStackTrace();
                    }
                  }
                }
              }
            }.start();
          }

          long millis = (random.nextInt(10) + 1) * 200;
          System.out.printf("Sleeping %d ms...\n", millis);
          Util.snap(millis);
        }
      }
    }.start();
    return "stressed";
  }

}
