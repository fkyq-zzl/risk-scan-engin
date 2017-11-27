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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@PropertySource("classpath:dataSource.properties")
@MapperScan(sqlSessionFactoryRef = "scanSqlSessionFactory", basePackages = { "com.cjhx.risk.o32scan.dao" })
public class O32ScanDataSourceConfig {

	@Bean(name = "scanDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.scan")
	public DataSource scanDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "scanSqlSessionFactory")
	public SqlSessionFactory riskSqlSessionFactory(@Qualifier("scanDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources("classpath*:o32scanMappers/*Mapper.xml");
		bean.setMapperLocations(resources);
		return bean.getObject();
	}

	@Bean(name = "scanTransactionManager")
	public DataSourceTransactionManager riskTransactionManager(@Qualifier("scanDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
