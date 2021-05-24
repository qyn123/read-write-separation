package com.spring.aop.readwriteseparation.config;

import com.spring.aop.readwriteseparation.constant.DBTypeEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yn.qiao
 * @version 1.0
 * @create 2021-05-04 22:37
 **/
@Configuration
public class DataSourceConfig {
    private final static long timeOUnt = 60000L;
    private final static long vaildTime = 3000L;
    private final static int poolSize = 80;
    private final static String driver = "com.mysql.cj.jdbc.Driver";

    @Value("${spring.datasource.master.url}")
    private String urlMa;
    @Value("${spring.datasource.master.username}")
    private String usernameMa;
    @Value("${spring.datasource.master.password}")
    private String passwordMa;

    @Value("${spring.datasource.slave1.url}")
    private String urlS1;
    @Value("${spring.datasource.slave1.username}")
    private String usernameS1;
    @Value("${spring.datasource.slave1.password}")
    private String passwordS1;

    @Value("${spring.datasource.slave2.url}")
    private String urlS2;
    @Value("${spring.datasource.slave2.username}")
    private String usernameS2;
    @Value("${spring.datasource.slave2.password}")
    private String passwordS2;


    @Bean
    public DataSource masterDataSource() {

        HikariConfig configuration = new HikariConfig();
        configuration.setPoolName("MASTER");
        configuration.setJdbcUrl(urlMa);
        configuration.setUsername(usernameMa);
        configuration.setPassword(passwordMa);
        configuration.setDriverClassName(driver);
        configuration.setMinimumIdle(poolSize);
        configuration.setMaximumPoolSize(poolSize);
        configuration.setConnectionTimeout(timeOUnt);
        configuration.setReadOnly(false);
        configuration.setValidationTimeout(vaildTime);
        return new HikariDataSource(configuration);
    }

    @Bean
    public DataSource slave1DataSource() {
        HikariConfig configuration = new HikariConfig();

        configuration.setJdbcUrl(urlS1);
        configuration.setUsername(usernameS1);
        configuration.setPassword(passwordS1);
        configuration.setPoolName("SLAVE1");
        configuration.setMinimumIdle(poolSize);
        configuration.setMaximumPoolSize(poolSize);
        configuration.setConnectionTimeout(timeOUnt);
        configuration.setDriverClassName(driver);
        configuration.setReadOnly(true);
        configuration.setValidationTimeout(vaildTime);
        return new HikariDataSource(configuration);
    }


    @Bean
    public DataSource slave2DataSource() {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(urlS2);
        configuration.setUsername(usernameS2);
        configuration.setPassword(passwordS2);
        configuration.setPoolName("SLAVE2");
        configuration.setMinimumIdle(poolSize);
        configuration.setMaximumPoolSize(poolSize);
        configuration.setConnectionTimeout(timeOUnt);
        configuration.setDriverClassName(driver);
        configuration.setReadOnly(true);
        configuration.setValidationTimeout(vaildTime);
        return new HikariDataSource(configuration);
    }


    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                          @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(16);
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }


}
