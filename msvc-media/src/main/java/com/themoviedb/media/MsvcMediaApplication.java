package com.themoviedb.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMediaApplication.class, args);
	}

}
