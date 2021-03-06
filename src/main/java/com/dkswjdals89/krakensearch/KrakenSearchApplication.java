package com.dkswjdals89.krakensearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@EnableCaching
@SpringBootApplication
public class KrakenSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(KrakenSearchApplication.class, args);
	}
}
