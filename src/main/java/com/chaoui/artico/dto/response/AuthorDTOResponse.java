package com.chaoui.artico.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthorDTOResponse extends UserDTOResponse {
    private String nickname;
    private List<Long> articles = new ArrayList<>();
}