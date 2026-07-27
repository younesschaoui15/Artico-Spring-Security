package com.chaoui.artico.dto.request;

import com.chaoui.artico.enums.ArticleCategory;
import com.chaoui.artico.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ArticleDTO {

    private Long id = null;
    private String title;
    private ArticleCategory category = ArticleCategory.GENERAL;
    private String content;
    private ArticleStatus status = ArticleStatus.ON_PROGRESS;
    private Long authorID;

}
