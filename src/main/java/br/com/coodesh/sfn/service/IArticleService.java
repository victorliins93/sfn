package br.com.coodesh.sfn.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.coodesh.sfn.controller.dto.article.ArticleRequest;
import br.com.coodesh.sfn.controller.dto.article.ArticleResponse;

public interface IArticleService {

	ArticleResponse postArticle(ArticleRequest articleRequest);

	Page<ArticleResponse> getAll(Pageable pageable);

	Optional<ArticleResponse> update(Long id, ArticleRequest articleRequest);

	Optional<ArticleResponse> getById(Long id);

	boolean delete(Long id);

	public String getResource(String uri) throws IOException, URISyntaxException;

	public Boolean populatingDatabase(String uri, Boolean newArticles) throws Exception;
}
