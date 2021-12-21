package br.com.coodesh.sfn.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coodesh.sfn.domain.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
