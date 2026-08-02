package com.chaoui.artico.controller;

import com.chaoui.artico.dto.RoleDTO;
import com.chaoui.artico.dto.request.RegisterAdminDTORequest;
import com.chaoui.artico.dto.request.RegisterModeratorDTORequest;
import com.chaoui.artico.dto.request.UserRolesDTO;
import com.chaoui.artico.dto.response.AdminDTOResponse;
import com.chaoui.artico.dto.response.ModeratorDTOResponse;
import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.service.RoleService;
import com.chaoui.artico.service.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
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

    @PostMapping("/new-admin")
    public ResponseEntity<AdminDTOResponse> addAdmin(@RequestBody @NonNull RegisterAdminDTORequest adminDTORequest) {
        return ResponseEntity.ok(userService.addAdmin(adminDTORequest));
    }

    @PostMapping("/new-mod")
    public ResponseEntity<ModeratorDTOResponse> addModerator(@RequestBody @NonNull RegisterModeratorDTORequest moderatorDTORequest) {
        return ResponseEntity.ok(userService.addModerator(moderatorDTORequest));
    }

//    @PostMapping("/register/author")
//    public ResponseEntity<String> register(@RequestBody AuthorDTORequest registerAuthorDTO) {
//        try {
//            Credentials credentials = authService.registerAuthor(registerAuthorDTO);
//            return ResponseEntity.ok("Author with username: "+ credentials.getUsername() +" registered successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error registering author: "+ e.getMessage());
//        }
//    }
//
//    @PostMapping("/register/moderator")
//    public ResponseEntity<String> register(@RequestBody RegisterModeratorDTO registerModeratorDTO) {
//        try {
//            Credentials credentials = authService.registerModerator(registerModeratorDTO);
//            return ResponseEntity.ok("Moderator with username: "+ credentials.getUsername() +" registered successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error registering moderator: "+ e.getMessage());
//        }
//    }

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
