package youcode.security.service;

import org.springframework.stereotype.Component;
import youcode.security.domain.Role;
import youcode.security.domain.User;

import java.util.Optional;

@Component
public interface UserService {
    Optional<User> getById(Long id);

    Role grantRoleToUser(Long userId, Long roleId);
}
