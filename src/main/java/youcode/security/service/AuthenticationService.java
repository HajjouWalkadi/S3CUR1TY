package youcode.security.service;

import org.springframework.stereotype.Component;
import youcode.security.dto.request.AuthenticationRequest;
import youcode.security.dto.request.RegisterRequest;
import youcode.security.dto.response.AuthenticationResponse;


@Component
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest user);

    AuthenticationResponse authenticate(AuthenticationRequest user);

}

