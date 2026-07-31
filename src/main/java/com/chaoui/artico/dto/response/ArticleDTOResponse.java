package com.chaoui.artico.dto.response;

import com.chaoui.artico.dto.abstraction.EntityMappable;
import com.chaoui.artico.entity.Article;
import com.chaoui.artico.enums.ArticleCategory;
import com.chaoui.artico.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ArticleDTOResponse implements EntityMappable<Article> {

    private Long id;
    private String title;
    private ArticleCategory category;
    private String content;
    private ArticleStatus status;
    private Long authorID;

    @Override
    public void mapFromEntity(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.content = entity.getContent();
        this.status = entity.getArticleStatus();
        this.authorID = entity.getAuthor().getId();
    }

    @Override
    public Article mapToEntity() {
        Article article = new Article();
        if(id != null)
            article.setId(id);
        article.setTitle(title);
        article.setCategory(category);
        article.setContent(content);
        article.setArticleStatus(status);

        return article;
    }
}
