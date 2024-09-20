package org.chuan.woj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.chuan.woj.mapper")
@SpringBootApplication
public class WojApplication {

	public static void main(String[] args) {
		SpringApplication.run(WojApplication.class, args);
	}

}
