package com.eduardoinacio.SpringAWS_Gamehistory_Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringAwsGamehistoryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAwsGamehistoryMicroserviceApplication.class, args);
	}

}
