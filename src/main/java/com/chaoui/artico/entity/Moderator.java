package com.chaoui.artico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Moderator extends User {

    private String publicUsername;
    private boolean visible = true;

    @Override
    public String toString() {
        return "Moderator{" +
                super.toString() +
                ", publicUsername='" + publicUsername + '\'' +
                ", visible=" + visible +
                "} ";
    }
}

