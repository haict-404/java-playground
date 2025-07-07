package com.example.persistence.config;


import java.util.HashMap;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.envers.configuration.EnversSettings;
import org.hibernate.envers.strategy.internal.ValidityAuditStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
public class PersistenceConfig {
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String userName;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.hikari.catalog}")
  private String datasourceName;
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Bean(name = "dataSource")
  @Primary
  public HikariDataSource provideDataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(userName);
    dataSource.setPassword(password);
    dataSource.setCatalog(datasourceName);
    dataSource.addDataSourceProperty("cachePrepStmts", true);
    dataSource.addDataSourceProperty("prepStmtCacheSize", 25000);
    dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 20048);
    dataSource.addDataSourceProperty("useServerPrepStmts", true);
    dataSource.addDataSourceProperty("initializationFailFast", true);
    dataSource.setMinimumIdle(5);
    dataSource.setMaximumPoolSize(100);
    dataSource.setIdleTimeout(30000);
    dataSource.setMaxLifetime(1800000);
    dataSource.setConnectionTimeout(30000);
    dataSource.setPoolName("hikari-" + datasourceName);
    dataSource.setConnectionInitSql("SET search_path TO " + datasourceName);
    return dataSource;
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.example.persistence.entity");
    em.setPersistenceUnitName("em-" + datasourceName);
    em.setPersistenceProviderClass(HibernatePersistenceProvider.class);

    HashMap<String, Object> properties = getEnverSetting();
    em.setJpaPropertyMap(properties);
    return em;
  }


  @Bean(name = "transactionManager")
  @Primary
  public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    return transactionManager;
  }


  private HashMap<String, Object> getEnverSetting() {
    HashMap<String, Object> properties = new HashMap<>();
    properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
    properties.put(EnversSettings.AUDIT_STRATEGY, ValidityAuditStrategy.class.getName());
    properties.put(EnversSettings.AUDIT_TABLE_SUFFIX, "_HISTORY");
    properties.put(EnversSettings.REVISION_ON_COLLECTION_CHANGE, true);
    properties.put(EnversSettings.DO_NOT_AUDIT_OPTIMISTIC_LOCKING_FIELD, false);
    properties.put(EnversSettings.STORE_DATA_AT_DELETE, true);
    properties.put(EnversSettings.AUDIT_STRATEGY_VALIDITY_END_REV_FIELD_NAME, "REVISION_END");
    return properties;
  }
}
