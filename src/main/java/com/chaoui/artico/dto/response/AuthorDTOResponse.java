package com.chaoui.artico.dto.response;

import com.chaoui.artico.dto.AuthorDTO;
import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthorDTOResponse extends AuthorDTO {

    private Long id;

    @Override
    public void mapFromEntity(User entity) {
        Author author = (Author) entity;
        super.mapFromEntity(author);

        id = author.getId();
    }

    @Override
    public Author mapToEntity() {
        Author author = super.mapToEntity();

        if (id != null)
            author.setId(id);

        return author;
    }
}