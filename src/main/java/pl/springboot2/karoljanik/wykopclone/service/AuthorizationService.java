package pl.springboot2.karoljanik.wykopclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.springboot2.karoljanik.wykopclone.dto.AuthenticationResponse;
import pl.springboot2.karoljanik.wykopclone.dto.LoginRequest;
import pl.springboot2.karoljanik.wykopclone.dto.RegisterRequest;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.AuthorizationMail;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.model.VerificationToken;
import pl.springboot2.karoljanik.wykopclone.repository.UserRepository;
import pl.springboot2.karoljanik.wykopclone.repository.VerificationTokenRepository;
import pl.springboot2.karoljanik.wykopclone.security.JwtProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorizationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreateDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new AuthorizationMail(user.getEmail(),"Pleas activate your account","Thank you for joining to Wykop Clone community," +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new WykopCloneException("Invalid token!"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username =verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new WykopCloneException("Invalid username! User " + username + " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
