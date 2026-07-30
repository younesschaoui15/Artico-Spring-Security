package com.chaoui.artico.controller;

import com.chaoui.artico.dto.request.NewModeratorDTO;
import com.chaoui.artico.dto.request.RoleDTO;
import com.chaoui.artico.dto.request.UserRolesDTO;
import com.chaoui.artico.dto.response.ModeratorDTOResponse;
import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.service.RoleService;
import com.chaoui.artico.service.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test OK";
    }

    @PostMapping("/new-mod")
    public ResponseEntity<ModeratorDTOResponse> addModerator(@RequestBody @NonNull NewModeratorDTO newModeratorDTO) {
        return ResponseEntity.ok(userService.addModerator(newModeratorDTO.moderatorDTORequest(), newModeratorDTO.credentialsDTO()));
    }

    @PostMapping("/add-role")
    public ResponseEntity<UserRole> addRole(@RequestBody RoleDTO role) {
        return ResponseEntity.ok(roleService.addRole(role.name()));
    }

    @DeleteMapping("/remove-role/{id}")
    public ResponseEntity<Long> removeRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.removeRole(id));
    }

    @PostMapping("/user/add-roles")
    public ResponseEntity<Void> addRolesToUser(@RequestBody UserRolesDTO userRolesDTO) {
        userService.affectRolesToUser(userRolesDTO);
        return ResponseEntity.ok().build();
    }
}
