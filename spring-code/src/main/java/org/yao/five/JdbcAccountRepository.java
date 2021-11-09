package org.yao.five;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;

public class JdbcAccountRepository implements AccountRepository {
  private DataSource dataSource;

  public JdbcAccountRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
