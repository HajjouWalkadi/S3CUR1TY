package youcode.security.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import youcode.security.domain.Authority;
import youcode.security.domain.Role;
import youcode.security.domain.enums.AuthorityEnum;
import youcode.security.repository.AuthorityRepository;
import youcode.security.repository.RoleRepository;
import youcode.security.service.AuthorityService;
import youcode.security.service.RoleService;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;


    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            seedRoles();
        }
    }

    private void seedRoles() {
        // create authorities
        Authority viewRoles = authorityService.getByName(AuthorityEnum.VIEW_ROLES)
                .orElseThrow(() -> new RuntimeException("VIEW_ROLES authority not found"));

        Authority createRole = authorityService.getByName(AuthorityEnum.CREATE_ROLE)
                .orElseThrow(() -> new RuntimeException("CREATE_ROLE authority not found"));

        Authority viewUsers = authorityService.getByName(AuthorityEnum.VIEW_USERS)
                .orElseThrow(() -> new RuntimeException("VIEW_USERS authority not found"));
        Authority grantAuthorityToRole = authorityService.getByName(AuthorityEnum.GRANT_AUTHORITY_TO_ROLE)
                .orElseThrow(() -> new RuntimeException("GRANT_AUTHORITY_TO_ROLE authority not found"));
        Authority revokeAuthorityFromRole = authorityService.getByName(AuthorityEnum.REVOKE_AUTHORITY_FROM_ROLE)
                .orElseThrow(() -> new RuntimeException("REVOKE_AUTHORITY_FROM_ROLE authority not found"));

        Authority deleteRole = authorityService.getByName(AuthorityEnum.DELETE_ROLE)
                .orElseThrow(() -> new RuntimeException("DELETE_ROLE authority not found"));

        // Create roles and associate authorities
        Role userRole = Role.builder()
                .name("USER")
                .isDefault(true)
                .build();

        Role adminRole = Role.builder()
                .name("ADMIN")
                .authorities(Arrays.asList(viewRoles, viewUsers))
                .build();

        Role superAdminRole = Role.builder()
                .name("SUPER_ADMIN")
                .authorities(authorityRepository.findAll())
                .build();

        roleService.save(userRole, true);
        roleService.save(adminRole, true);
        roleService.save(superAdminRole, true);
    }

}
