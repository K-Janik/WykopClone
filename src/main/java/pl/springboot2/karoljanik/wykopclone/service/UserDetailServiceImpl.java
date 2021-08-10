package pl.springboot2.karoljanik.wykopclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user with " + username + " found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, getAuthorities("USER"));
    }
        private Collection<? extends GrantedAuthority> getAuthorities(String role){
            return singletonList(new SimpleGrantedAuthority(role));
        }
    }

