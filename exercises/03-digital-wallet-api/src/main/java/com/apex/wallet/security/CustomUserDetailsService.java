package com.apex.wallet.security;

import com.apex.wallet.model.User;
import com.apex.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with : " + username)));
        return new SecurityUser(user.get());
    }
}
