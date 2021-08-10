package pl.springboot2.karoljanik.wykopclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.springboot2.karoljanik.wykopclone.dto.AuthenticationResponse;
import pl.springboot2.karoljanik.wykopclone.dto.LoginRequest;
import pl.springboot2.karoljanik.wykopclone.dto.RegisterRequest;
import pl.springboot2.karoljanik.wykopclone.service.AuthorizationService;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/singup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authorizationService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authorizationService.verifyAccount(token);
        return new ResponseEntity<>("Account activated!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authorizationService.login(loginRequest);
    }
}
