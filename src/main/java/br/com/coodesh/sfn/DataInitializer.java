package br.com.coodesh.sfn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.coodesh.sfn.service.ArticleService;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	ArticleService articleService;

	@Value("${request.uriAllArticles}")
	private String uriAllArticles;

	@Override
	public void run(String... args) throws Exception {
		articleService.populatingDatabase(uriAllArticles, false);
	}

}
