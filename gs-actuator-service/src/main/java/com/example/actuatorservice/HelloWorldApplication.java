package com.example.actuatorservice;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
			context.getBean(Sender.class).send("test", 42);
		}
	}

	@Bean
	public DataSourcePoolMetadataProvider druidPoolDataSourceMetadataProvider() {
		return (dataSource) -> {
			DruidDataSource ds = DataSourceUnwrapper.unwrap(dataSource,
																											DruidDataSource.class);
			if (ds != null) {
				return new DruidDataSourcePoolMetadata(ds);
			}
			return null;
		};
	}
	/**
	 * 参考 org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata
	 */
	private class DruidDataSourcePoolMetadata extends AbstractDataSourcePoolMetadata<DruidDataSource> {
		/**
		 * Create an instance with the data source to use.
		 *
		 * @param dataSource the data source
		 */
		DruidDataSourcePoolMetadata(DruidDataSource dataSource) {
			super(dataSource);
		}

		@Override
		public Integer getActive() {
			return getDataSource().getActiveCount();
		}

		@Override
		public Integer getMax() {
			return getDataSource().getMaxActive();
		}

		@Override
		public Integer getMin() {
			return getDataSource().getMinIdle();
		}

		@Override
		public String getValidationQuery() {
			return getDataSource().getValidationQuery();
		}

		@Override
		public Boolean getDefaultAutoCommit() {
			return getDataSource().isDefaultAutoCommit();
		}
	}

}
