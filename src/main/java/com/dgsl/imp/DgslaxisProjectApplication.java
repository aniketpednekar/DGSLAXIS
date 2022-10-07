package com.dgsl.imp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.dgsl.dwp.bean"})
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.dgsl.imp","com.dgsl.imp.controller","com.dgsl.dwp.controller","com.dgsl.dwp.service","com.dgsl.dwp.repository"} )
public class DgslaxisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgslaxisProjectApplication.class, args);
	}

}
