package com.apex.wallet.service;

import com.apex.wallet.dto.CreateUserRequest;
import com.apex.wallet.dto.UserSummaryResponse;
import com.apex.wallet.exception.UserAlreadyExists;
import com.apex.wallet.exception.UserNotFoundException;
import com.apex.wallet.model.User;
import com.apex.wallet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(UUID userId){
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with ID" + userId));
    }

    @Transactional
    public User createUser(CreateUserRequest req){
        if(userRepository.existsByEmail(req.email())){
            throw new UserAlreadyExists("The user with email " + req.email() + " already exists");
        }
        User newuser = new User(req.email(),req.fullName());
        return userRepository.save(newuser);
    }
    public UserSummaryResponse findUserSummaryById(UUID userId){
        return userRepository.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with ID" + userId));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
