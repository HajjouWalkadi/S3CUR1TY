package youcode.security.dto.response;

import java.util.List;
import youcode.security.domain.Role;
import youcode.security.domain.Authority;
import youcode.security.domain.enums.AuthorityEnum;


public record RoleResponseDTO(
        String name,
        List<AuthorityEnum> authorities,
        boolean isDefault
) {
    public static RoleResponseDTO fromRole(Role role){
        return new RoleResponseDTO(
                role.getName(),
                role.getAuthorities().stream().map(Authority::getName).toList(),
                role.isDefault()
        );
    }
}

