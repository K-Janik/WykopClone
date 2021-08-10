package pl.springboot2.karoljanik.wykopclone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/wykopclone.jks");
            keyStore.load(resourceAsStream, "key-storepassword".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new WykopCloneException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("wykopclone", "key-storepassword".toCharArray());
        } catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new WykopCloneException("Exception occurred");
        }
    }

    public boolean validateToken(String jwt) {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJwt(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("wykopclone").getPublicKey();
        } catch (KeyStoreException e) {
            throw new WykopCloneException("Exception occurred during retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return claims.getSubject();
    }
}
