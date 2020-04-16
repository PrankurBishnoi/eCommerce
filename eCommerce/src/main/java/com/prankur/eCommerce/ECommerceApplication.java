package com.prankur.eCommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan
//@PropertySource("classpath:application.properties")
public class ECommerceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(ECommerceApplication.class, args);

	}

}
