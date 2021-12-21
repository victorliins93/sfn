package br.com.coodesh.sfn.controller.dto.article;

import java.util.List;

import br.com.coodesh.sfn.domain.model.Event;
import br.com.coodesh.sfn.domain.model.Launche;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ArticleResponse {

	private Long id;

    private Boolean featured;

    private String title;

    private String url;

    private String imageUrl;

    private String newsSite;

    private String summary;

    private String publishedAt;

    private String updatedAt;

    private List<Launche> launches;

    private List<Event> events;
    
    public ArticleResponse() {
    	
    }
	
}
