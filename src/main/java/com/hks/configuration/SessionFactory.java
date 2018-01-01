package com.hks.configuration;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Huangkunsong
 */
@Configuration
public class SessionFactory {

    /**
     * hibernate
     * @param dataSource
     * @return
     */
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource){
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[]{"com.hks.eneity"});
        Properties props = new Properties();
        props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "create");
        sfb.setHibernateProperties(props);
        return sfb;
    }

    /**
     * mybatis
     * @param dataSource
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
            resourcePatternResolver.getResources(
                "classpath:com/hks/dao/*.xml"));
        //别名，让*Mpper.xml实体类映射可以不加上具体包名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.hks.entity");
        Resource[] myBatisConfig = resourcePatternResolver.
            getResources("classpath:mybatis-config.xml");
        if (null != myBatisConfig && myBatisConfig.length > 0) {
            sqlSessionFactoryBean.setConfigLocation(myBatisConfig[0]);
        }
        return sqlSessionFactoryBean;
    }

}
