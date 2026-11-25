package com.apex.wallet.service;

import com.apex.wallet.dto.AuthResponse;
import com.apex.wallet.dto.LoginRequest;
import com.apex.wallet.dto.RegisterRequest;
import com.apex.wallet.exception.UserAlreadyExists;
import com.apex.wallet.exception.UserNotFoundException;
import com.apex.wallet.model.User;
import com.apex.wallet.repository.UserRepository;
import com.apex.wallet.security.JwtService;
import com.apex.wallet.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public AuthResponse register(RegisterRequest req){
        if(userRepository.existsByEmail(req.email())){
            throw new UserAlreadyExists("User already exists with the email : " + req.email());
        }
        String passwordHash = passwordEncoder.encode(req.password());
        User newUser = new User(req.email(),req.fullName(),passwordHash);
        SecurityUser savedUser =  new SecurityUser(userRepository.save(newUser));
        String token = jwtService.generateToken(savedUser);
        return new AuthResponse(token,req.email(),savedUser.getRole().name());
    }

    public AuthResponse login(LoginRequest req){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(),req.password()));
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        if(user == null){
            throw new UserNotFoundException("User not found with the email: " + req.email());
        }
        String token = jwtService.generateToken(user);
        return new AuthResponse(token,req.email(),user.getRole().name());
    }
}
