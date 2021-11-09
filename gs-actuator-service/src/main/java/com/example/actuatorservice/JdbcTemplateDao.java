package com.example.actuatorservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
                  throw new RuntimeException(ex);
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
