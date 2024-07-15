package edu.baylor.sprinklerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SprinklerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprinklerServiceApplication.class, args);
	}

}
