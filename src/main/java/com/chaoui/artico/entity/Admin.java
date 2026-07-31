package com.chaoui.artico.entity;

import com.chaoui.artico.enums.AdminLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    private AdminLevel level;
    private Long createdBy; //Other super Admin ID

    @Override
    public String toString() {
        return "Admin {" +
                super.toString() +
                ", level =' " + level + '\'' +
                ", created by = " + createdBy +
                "} ";
    }
}

