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
@MapperScan(sqlSessionFactoryRef = "prodSqlSessionFactory", basePackages = { "com.cjhx.risk.o32prod.dao" })
public class O32ProdDataSourceConfig {

	@Bean(name = "prodDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.prod")
	public DataSource prodDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "prodSqlSessionFactory")
	public SqlSessionFactory prodSqlSessionFactory(@Qualifier("prodDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources("classpath*:o32prodMappers/*Mapper.xml");
		bean.setMapperLocations(resources);
		return bean.getObject();
	}

	@Bean(name = "prodTransactionManager")
	public DataSourceTransactionManager prodTransactionManager(@Qualifier("prodDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
