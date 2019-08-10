package com.destiny.elephant;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.destiny.elephant.mapper")
public class ElephantApplication  {

	private static Logger logger = LoggerFactory.getLogger(ElephantApplication.class);

	public static void main(String[] args) {

		logger.info(" start elephant ----> ");
		SpringApplication.run(ElephantApplication.class, args);
	}
}
