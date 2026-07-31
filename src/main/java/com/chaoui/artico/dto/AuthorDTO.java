package com.chaoui.artico.dto;

import com.chaoui.artico.entity.Article;
import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public non-sealed abstract class AuthorDTO extends UserDTO {

    private String nickname;
    private List<Long> articles = new ArrayList<>();

    @Override
    public void mapFromEntity(User entity) {
        Author author = (Author) entity;
        super.mapFromEntity(author);

        nickname = author.getNickname();
        articles = author.getArticles().stream().map(Article::getId).toList();
    }

    @Override
    public Author mapToEntity() {
        Author author = new Author();
        entityMapping(author);

        author.setNickname(nickname);

        return author;
    }
}