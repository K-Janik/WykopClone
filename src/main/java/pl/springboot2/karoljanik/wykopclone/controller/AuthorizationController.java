package pl.springboot2.karoljanik.wykopclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.springboot2.karoljanik.wykopclone.dto.AuthenticationResponse;
import pl.springboot2.karoljanik.wykopclone.dto.LoginRequest;
import pl.springboot2.karoljanik.wykopclone.dto.RefreshTokenRequest;
import pl.springboot2.karoljanik.wykopclone.dto.RegisterRequest;
import pl.springboot2.karoljanik.wykopclone.service.AuthorizationService;
import pl.springboot2.karoljanik.wykopclone.service.RefreshTokenService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService, RefreshTokenService refreshTokenService) {
        this.authorizationService = authorizationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authorizationService.registerAccount(registerRequest);
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

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authorizationService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK)
                .body("Refresh Token Deleted Successfully!");
    }
}
