package com.dgsl.imp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.dgsl.imp","com.dgsl.imp.controller"} )
public class DgslaxisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgslaxisProjectApplication.class, args);
	}

}
