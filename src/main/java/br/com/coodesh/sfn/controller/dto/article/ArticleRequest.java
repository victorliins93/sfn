package br.com.coodesh.sfn.controller.dto.article;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.coodesh.sfn.domain.model.Event;
import br.com.coodesh.sfn.domain.model.Launche;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArticleRequest {
	private Long id;
	@NotNull
    private Boolean featured;
	@NotBlank
	@NotNull
    private String title;
	@NotBlank
	@NotNull
    private String url;
	@NotBlank
	@NotNull
    private String imageUrl;
	@NotBlank
	@NotNull
    private String newsSite;
	@NotBlank
	@NotNull
    private String summary;
	@NotBlank
	@NotNull
    private String publishedAt;
	
    private String updatedAt;
	@NotNull
    private List<Launche> launches;
	@NotNull
    private List<Event> events;
	
	public ArticleRequest() {
	}
	
}
