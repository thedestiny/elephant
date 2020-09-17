package com.destiny.elephant.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {
	
	/**
	 * mybatis-plus分页插件
	 */
	@Bean
	public PaginationInnerInterceptor paginationInterceptor() {
		PaginationInnerInterceptor page = new PaginationInnerInterceptor(DbType.MYSQL);
		return page;
	}
	
	
}
