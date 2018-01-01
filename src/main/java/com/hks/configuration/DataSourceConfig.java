package com.hks.configuration;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置类
 * @author Huangkunsong
 */
@Configuration
@PropertySource("classpath:/jdbc.property")
public class DataSourceConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${datasource.url}")
    private String dbUrl;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driverClassName}")
    private String driverClassName;

    @Value("${datasource.initialSize}")
    private int initialSize;

    @Value("${datasource.minIdle}")
    private int minIdle;

    @Value("${datasource.maxActive}")
    private int maxActive;

    @Value("${datasource.maxWait}")
    private int maxWait;

    @Value("${datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${datasource.validationQuery}")
    private String validationQuery;

    @Value("${datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${datasource.filters}")
    private String filters;

    @Value("{datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 阿里数据源配置
     * @return
     */
    @Bean
    public DataSource druidDataSource(){
        LOGGER.info("Initialize the data source...");
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(this.username);
        datasource.setPassword(this.password);
        datasource.setDriverClassName(this.driverClassName);

        //com.hks.configuration
        datasource.setInitialSize(this.initialSize);
        datasource.setMinIdle(this.minIdle);
        datasource.setMaxActive(this.maxActive);
        datasource.setMaxWait(this.maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        datasource.setValidationQuery(this.validationQuery);
        datasource.setTestWhileIdle(this.testWhileIdle);
        datasource.setTestOnBorrow(this.testOnBorrow);
        datasource.setTestOnReturn(this.testOnReturn);
        datasource.setPoolPreparedStatements(this.poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(this.filters);
        } catch (SQLException e) {
            LOGGER.error("druid com.hks.configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }

    /**
     * 阿里数据源打印SQL配置
     * @return
     */
    public Slf4jLogFilter buildSlf4jLogFilter(){
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setConnectionLogEnabled(false);
        slf4jLogFilter.setStatementLogEnabled(false);
        //表示是否显示结果集。
        slf4jLogFilter.setResultSetLogEnabled(true);
        //表示是否显示SQL语句。
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
    }

    /**
     * 直接通过JDBC设置datasource
     * @return
     */
    public DataSource jdbcDataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&ssl=true");
        ds.setUsername("root");
        ds.setPassword("huangkunsong");
        return ds;
    }

    /**
     * JNDI配置datasource
     * @return
     */
    public JndiObjectFactoryBean dataSource2(){
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("jndi/database");
        jndi.setResourceRef(true);
        jndi.setProxyInterface(javax.sql.DataSource.class);
        return jndi;
    }

    /**
     * DBCP配置datasource
     * @return
     */
    public BasicDataSource dataSource3(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("huangkunsong");
        ds.setInitialSize(5);
        return ds;
    }
}
