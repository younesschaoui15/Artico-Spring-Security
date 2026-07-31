package com.chaoui.artico.dto.request;

import com.chaoui.artico.dto.abstraction.EntityMappable;
import com.chaoui.artico.entity.Article;
import com.chaoui.artico.enums.ArticleCategory;
import com.chaoui.artico.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ArticleDTORequest implements EntityMappable<Article> {

    private String title;
    private ArticleCategory category = ArticleCategory.GENERAL;
    private String content;
    private ArticleStatus status = ArticleStatus.ON_PROGRESS;
    private Long authorID;

    @Override
    public void mapFromEntity(Article entity) {
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.content = entity.getContent();
        this.status = entity.getArticleStatus();
        this.authorID = entity.getAuthor().getId();
    }

    @Override
    public Article mapToEntity() {
        Article article = new Article();
        article.setTitle(this.getTitle());
        article.setCategory(this.getCategory());
        article.setContent(this.getContent());
        article.setArticleStatus(this.getStatus());

        return article;
    }
}
