package tp.eni_store.api;

import org.springframework.web.bind.annotation.*;
import tp.eni_store.service.AuthService;

@RestController
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("api/create-token")
    public String createToken(@RequestBody LoginRequest loginRequest) {

        return authService.createToken(loginRequest.email, loginRequest.password).data;
    }

    @GetMapping("api/check-token")
    public String checkToken(@RequestHeader(value = "Authorization", required = true) String token) {

        return authService.checkToken(token).message;
    }
}
