package br.com.coodesh.sfn.service.mapper;

import org.springframework.stereotype.Component;

import br.com.coodesh.sfn.controller.dto.article.ArticleRequest;
import br.com.coodesh.sfn.domain.model.Article;

@Component
public class ArticleRequestMapper implements Mapper<ArticleRequest, Article> {

	@Override
	public Article map(ArticleRequest input) {
		if(input == null) {			
			return null;
		}
		
		Article article = new Article();
		
		article.setEvents(input.getEvents());
		article.setFeatured(input.getFeatured());
		article.setImageUrl(input.getImageUrl());
		article.setUpdatedAt(input.getUpdatedAt());
		article.setLaunches(input.getLaunches());
		article.setNewsSite(input.getNewsSite());
		article.setPublishedAt(input.getPublishedAt());
		article.setSummary(input.getSummary());
		article.setTitle(input.getTitle());
		article.setUrl(input.getUrl());
		
		return article;
	}

}
