package com.wn.signin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wn.signin.mapper")
public class SigninApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigninApplication.class, args);
	}

}
