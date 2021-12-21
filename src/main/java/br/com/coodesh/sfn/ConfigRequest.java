package br.com.coodesh.sfn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "request")
@Data
@AllArgsConstructor
@Builder
public class ConfigRequest {

	private String uriNewArticles;
	private String uriAllArticles;
	private String cronExpression;

	public ConfigRequest() {

	}

}
