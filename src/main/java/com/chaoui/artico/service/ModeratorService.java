package com.chaoui.artico.service;

import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.Moderator;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.repository.AuthorRepository;
import com.chaoui.artico.repository.ModeratorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeratorService {

    private final ModeratorRepository moderatorRepository;

    public ModeratorService(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    public List<Moderator> getAllModerators() {
        return moderatorRepository.findAll();
    }

    public Moderator getModeratorById(Long id) {
        return moderatorRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

//    public Moderator getModeratorByUsername(String username) {
//        return
//    }

    public List<UserRole> getAllUserRoles(Long id) {
        return moderatorRepository.findById(id)
            .map(User::getRoles)
            .map(ArrayList::new)
            .orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
            );
    }
}
