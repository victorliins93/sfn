package br.com.coodesh.sfn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ConfigRequest.class)
public class SfnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SfnApplication.class, args);
	}

}
