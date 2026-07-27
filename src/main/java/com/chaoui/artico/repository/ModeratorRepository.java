package com.chaoui.artico.repository;

import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {

}
