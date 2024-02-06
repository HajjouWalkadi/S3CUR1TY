package youcode.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.security.domain.Role;
import youcode.security.dto.request.GrantAuthoritiesRequestDto;
import youcode.security.dto.request.GrantRoleToUserRequestDto;
import youcode.security.dto.request.RoleRequestDTO;
import youcode.security.dto.response.RoleResponseDTO;
import youcode.security.service.RoleService;
import youcode.security.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {
        List<Role> roles = roleService.getAll();
        if (roles.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(roles.stream().map(RoleResponseDTO::fromRole).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> save(@RequestBody RoleRequestDTO roleToSave) {
        Role role = roleService.save(roleToSave.toRole(), false);
        if (role == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/grant_authorities")
    public ResponseEntity<RoleResponseDTO> grantAuthorities(@RequestBody GrantAuthoritiesRequestDto rolesAuthorities) {
        Role role = roleService.grantAuthorities(rolesAuthorities.getAuthorityId(), rolesAuthorities.getRoleId());
        if (role == null) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/revoke_authorities")
    public ResponseEntity<RoleResponseDTO> revokeAuthorities(@RequestBody GrantAuthoritiesRequestDto rolesAuthorities) {
        Role role = roleService.revokeAuthorities(rolesAuthorities.getAuthorityId(), rolesAuthorities.getRoleId());
        if (role == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    //grant role to user
    @PostMapping("/grant_role_to_user")
    public ResponseEntity<RoleResponseDTO> grantRoleToUser(@RequestBody GrantRoleToUserRequestDto grantRoleToUserRequestDto) {
        Role role = userService.grantRoleToUser(grantRoleToUserRequestDto.getUserId(), grantRoleToUserRequestDto.getRoleId());

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (roleService.getById(id).isPresent()) {
            roleService.delete(id);
            return ResponseEntity.ok().build();
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

