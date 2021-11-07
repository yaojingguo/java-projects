package org.yao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.jdbc")
public class SpringJdbcConfig {
  @Bean
  public DataSource mysqlDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
    dataSource.setUsername("guest_user");
    dataSource.setPassword("guest_password");

    return dataSource;
  }
}