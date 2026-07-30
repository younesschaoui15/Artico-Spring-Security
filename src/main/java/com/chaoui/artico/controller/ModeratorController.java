package com.chaoui.artico.controller;

import com.chaoui.artico.entity.Moderator;
import com.chaoui.artico.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Moderator>> getModerators() {
        return ResponseEntity.ok(moderatorService.getAllModerators());
    }

    @GetMapping("/{id}")
    public Moderator getModerators(@PathVariable Long id) {
        var mod = moderatorService.getModeratorById(id);
        System.out.println("# Moderator: " + mod);
        System.out.println("# Roles: " + mod.getRoles());

        return mod;
    }
}
