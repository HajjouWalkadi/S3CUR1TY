package youcode.security.dto.request;

import youcode.security.domain.Authority;
import youcode.security.domain.Role;

import java.util.List;

public record RoleRequestDTO(
        String name,
        List<Authority> authorities,
        boolean isDefault
){
    public Role toRole(){
        return Role.builder()
                .name(name)
                .isDefault(isDefault)
                .authorities(authorities)
                .build();
    }
}