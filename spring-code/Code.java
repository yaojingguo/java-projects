@Configuration
public class ServiceConfig {
  @Autowired
  private RepositoryConfig repositoryConfig;

  @Bean
  public TransferService transferService() {
    return new TransferServiceImpl(repositoryConfig.accountRepository());
  }
}

@Configuration
public interface RepositoryConfig {

  @Bean
  AccountRepository accountRepository();
}

@Configuration
public class DefaultRepositoryConfig implements RepositoryConfig {

  @Bean
  public AccountRepository accountRepository() {
    return new JdbcAccountRepository(...);
  }
}

@Configuration
@Import({ServiceConfig.class, DefaultRepositoryConfig.class})  // import the concrete config!
public class SystemTestConfig {

  @Bean
  public DataSource dataSource() {
    // return DataSource
  }

}

public static void main(String[] args) {
  ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
  TransferService transferService = ctx.getBean(TransferService.class);
  transferService.transfer(100.00, "A123", "C456");
}
