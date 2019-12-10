package com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author administered
 */
@SpringBootApplication
@EnableDiscoveryClient
public class JhAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JhAuthServerApplication.class, args);
	}
}
