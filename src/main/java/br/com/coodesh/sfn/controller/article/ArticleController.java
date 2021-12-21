package br.com.coodesh.sfn.controller.article;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.coodesh.sfn.controller.dto.article.ArticleRequest;
import br.com.coodesh.sfn.controller.dto.article.ArticleResponse;
import br.com.coodesh.sfn.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	@GetMapping
	public ResponseEntity<Page<ArticleResponse>> findAll(Pageable pageable) {
		Page<ArticleResponse> articles = articleService.getAll(pageable);
		return ResponseEntity.ok(articles);
	}

	@GetMapping("/{articleId}")
	public ResponseEntity<ArticleResponse> findArticleById(@PathVariable("articleId") Long articleId) {
		Optional<ArticleResponse> articleUpdated = this.articleService.getById(articleId);
		if (!articleUpdated.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(articleUpdated.get());
	}

	@PostMapping
	public ResponseEntity<ArticleResponse> postArticle(@Valid @RequestBody ArticleRequest articleDTO) {
		return ResponseEntity.ok(articleService.postArticle(articleDTO));
	}

	@PutMapping("/{articleId}")
	public ResponseEntity<ArticleResponse> putArticle(@PathVariable("articleId") Long id,
			@Valid @RequestBody ArticleRequest articleDTO) {

		Optional<ArticleResponse> articleUpdated = this.articleService.update(id, articleDTO);
		if (!articleUpdated.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(articleUpdated.get());
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("articleId") Long id) {

		if (articleService.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

}
