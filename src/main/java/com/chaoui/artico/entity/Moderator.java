package com.chaoui.artico.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public non-sealed class Moderator extends User {

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

