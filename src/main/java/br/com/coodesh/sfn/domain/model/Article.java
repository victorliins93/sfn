package br.com.coodesh.sfn.domain.model;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name= "article")
@Data
@AllArgsConstructor
@Builder
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column
    private Boolean featured;
	@Column(length = 1024)
    private String title;
	@Column(length = 1024)
    private String url;
	@Column(length = 1024)
    private String imageUrl;
	@Column(length = 1024)
    private String newsSite;
	@Column(length = 1024)
    private String summary;
	@Column
    private String publishedAt;
	@Column
    private String updatedAt;
	@OneToMany(targetEntity=Launche.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Builder.Default
    private List<Launche> launches = new ArrayList<>();
	@OneToMany(targetEntity=Event.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Builder.Default
    private List<Event> events = new ArrayList<>();
	
	public <R> R map(Function<Article, R> func) {
		return func.apply(this);
	}
	
	public Article() {
		
	}

}
