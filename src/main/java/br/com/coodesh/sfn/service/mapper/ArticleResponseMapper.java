package br.com.coodesh.sfn.service.mapper;

import org.springframework.stereotype.Component;

import br.com.coodesh.sfn.controller.dto.article.ArticleResponse;
import br.com.coodesh.sfn.domain.model.Article;

@Component
public class ArticleResponseMapper implements Mapper<Article, ArticleResponse> {

	@Override
	public ArticleResponse map(Article input) {
		if(input == null) {			
			return null;
		}
		
		ArticleResponse articleResponse = new ArticleResponse();	
		
		articleResponse.setId(input.getId());
		articleResponse.setEvents(input.getEvents());
		articleResponse.setFeatured(input.getFeatured());
		articleResponse.setImageUrl(input.getImageUrl());
		articleResponse.setLaunches(input.getLaunches());
		articleResponse.setNewsSite(input.getNewsSite());
		articleResponse.setPublishedAt(input.getPublishedAt());
		articleResponse.setUpdatedAt(input.getUpdatedAt());
		articleResponse.setSummary(input.getSummary());
		articleResponse.setTitle(input.getTitle());
		articleResponse.setUrl(input.getUrl());
		
		return articleResponse;
	}

}
