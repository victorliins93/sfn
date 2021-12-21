package br.com.coodesh.sfn.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.coodesh.sfn.controller.dto.article.ArticleRequest;
import br.com.coodesh.sfn.controller.dto.article.ArticleResponse;
import br.com.coodesh.sfn.domain.model.Article;
import br.com.coodesh.sfn.domain.repository.ArticleRepository;
import br.com.coodesh.sfn.service.mapper.Mapper;
import br.com.coodesh.sfn.util.Util;

@Service
public class ArticleService implements IArticleService {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ArticleService.class);

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	private Mapper<ArticleRequest, Article> requestMapper;

	@Autowired
	private Mapper<Article, ArticleResponse> responseMapper;

	@Value("${request.uriNewArticles}")
	private String uriNewArticles;

	@Transactional
	@Scheduled(cron = "${request.cronExpression}")
	public void getNewArticles() {
		LOGGER.info("Checking for new articles");
		try {
			this.populatingDatabase(uriNewArticles + Util.getDateToRequest(), true);
		} catch (Exception e) {
			LOGGER.info("Error synchronizing data");
		}
	}

	@Transactional
	@Override
	public ArticleResponse postArticle(ArticleRequest articleRequest) {
		LOGGER.info("Creating an article");
		Assert.notNull(articleRequest, "Invalid request");
		Article article = this.requestMapper.map(articleRequest);
		return this.articleRepository.save(article).map((Article input) -> this.responseMapper.map(input));
	}

	@Transactional
	@Override
	public Page<ArticleResponse> getAll(Pageable pageable) {
		LOGGER.info("Searching for articles");
		Assert.notNull(pageable, "Invalid page");
		return this.articleRepository.findAll(pageable).map(article -> this.responseMapper.map(article));
	}

	@Transactional
	@Override
	public Optional<ArticleResponse> update(Long id, ArticleRequest articleRequest) {

		LOGGER.info("Updating article");
		Assert.notNull(id, "Invalid ID");
		Article articleUpdate = this.requestMapper.map(articleRequest);
		Optional<Article> existingArticle = this.articleRepository.findById(id);
		return existingArticle.map(article -> {
			article.setEvents(articleUpdate.getEvents());
			article.setFeatured(articleUpdate.getFeatured());
			article.setUpdatedAt(articleUpdate.getUpdatedAt());
			article.setImageUrl(articleUpdate.getImageUrl());
			article.setLaunches(articleUpdate.getLaunches());
			article.setNewsSite(articleUpdate.getNewsSite());
			article.setPublishedAt(articleUpdate.getPublishedAt());
			article.setSummary(articleUpdate.getSummary());
			article.setTitle(articleUpdate.getTitle());
			article.setUrl(articleUpdate.getUrl());
			return this.responseMapper.map(articleRepository.saveAndFlush(article));
		});
	}

	@Transactional
	@Override
	public Optional<ArticleResponse> getById(Long id) {
		LOGGER.info("Get article");
		Assert.notNull(id, "Invalid ID");
		Optional<Article> existingArticle = articleRepository.findById(id);
		return existingArticle.map(this.responseMapper::map);
	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		LOGGER.info("Removing article");
		Assert.notNull(id, "Invalid ID");
		try {
			articleRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			LOGGER.warn("Could not remove article");
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean populatingDatabase(String uri, Boolean newArticles) throws Exception {
		LOGGER.info("Checking database");
		if (this.getAll(PageRequest.ofSize(1)).isEmpty() || newArticles) {
			LOGGER.info("Populating database");
			ObjectMapper objectMapper = new ObjectMapper();
			LOGGER.info("Creating articles");
			List<Article> articles = objectMapper.readValue(this.getResource(uri), new TypeReference<List<Article>>() {
			});
			LOGGER.info("Persisting new articles");
			articleRepository.saveAll(articles);
			LOGGER.info("Finished");
			return true;
		} else {
			LOGGER.info("Populated database");
		}
		return false;

	}

	@Override
	public String getResource(String uri) throws IOException, URISyntaxException {

		HttpClient httpClient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(uri);
		HttpUriRequest request = RequestBuilder.get().setUri(uriBuilder.build()).build();
		HttpResponse response = httpClient.execute(request);
		HttpEntity responseEntity = response.getEntity();
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			String errorMessage = String.format("Exception retrieving FHIR resource: %s\n",
					response.getStatusLine().toString());
			System.err.print(errorMessage);
			responseEntity.writeTo(System.err);
			throw new ResponseStatusException(
					org.springframework.http.HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
		}
		OutputStream os = new ByteArrayOutputStream();
		responseEntity.writeTo(os);
		String responseJson = os.toString().replaceAll("\"" + "id" + "\"[ ]*:[^,}\\]]*[,]?", "");
		os.close();
		return responseJson;
	}

}
