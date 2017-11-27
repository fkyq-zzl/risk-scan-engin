package com.cjhx.risk.system.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@PropertySource("classpath:dataSource.properties")
@MapperScan(sqlSessionFactoryRef = "riskSqlSessionFactory",basePackages = {"com.cjhx.risk.scan.dao"})
public class RiskDataSourceConfig {
	
	@Bean(name = "riskDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.risk")
	@Primary
	public DataSource riskDataSource() 
	{
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "riskSqlSessionFactory")
	@Primary
	public SqlSessionFactory riskSqlSessionFactory(@Qualifier("riskDataSource") DataSource dataSource)throws Exception
	{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		Resource[] resources=new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*Mapper.xml");
		bean.setMapperLocations(resources);
		return bean.getObject();
	}

	@Bean(name = "riskTransactionManager")
	@Primary
	public DataSourceTransactionManager riskTransactionManager(@Qualifier("riskDataSource") DataSource dataSource) 
	{
		return new DataSourceTransactionManager(dataSource);
	}

}
