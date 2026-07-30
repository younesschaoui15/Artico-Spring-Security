package com.chaoui.artico.dto.response;

import com.chaoui.artico.enums.ArticleCategory;
import com.chaoui.artico.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ArticleDTO {

    private Long id;
    private String title;
    private ArticleCategory category;
    private String content;
    private ArticleStatus status;
    private Long authorID;

}
