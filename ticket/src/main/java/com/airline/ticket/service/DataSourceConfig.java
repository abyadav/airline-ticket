package com.airline.ticket.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;



@Configuration
@EnableTransactionManagement

public class DataSourceConfig {

	@Bean("platformHikariConfig")
	@ConfigurationProperties(prefix = "spring.datasource.platform")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).properties(hibernateProperties()).packages("com.airline.ticket.entity").build();
	}
	
	@Bean(name = "transactionManager")
	public PlatformTransactionManager mysqlTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	private Map<String, Object> hibernateProperties() {
		Resource resource = new ClassPathResource("hibernate.properties");
		try {
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			return properties.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
		} catch (IOException e) {
			return new HashMap<>();
		}
	}
	

	
	private HikariConfig updateHikariConfig() {
		HikariConfig hikariConfig = hikariConfig();
//		if (Boolean.valueOf(enabled) && MapUtils.isNotEmpty(secretsLoader.getSecretsMap())) {
			hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/airline_ticket");
			hikariConfig.setUsername("root");
			hikariConfig.setPassword("");
//		}
		return hikariConfig;
	}

	@Bean("platformDatasource")
	public DataSource dataSource() {
		return new HikariDataSource(updateHikariConfig());
	}
}
