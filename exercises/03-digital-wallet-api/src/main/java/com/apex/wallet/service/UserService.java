package com.apex.wallet.service;

import com.apex.wallet.dto.UserResponse;
import com.apex.wallet.dto.UserSummaryResponse;
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

    public UserResponse getUserById(UUID userId){
        User user=  userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with ID" + userId));
        return UserResponse.fromEntity(user);
    }

    public UserSummaryResponse findUserSummaryById(UUID userId){
        return userRepository.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with ID" + userId));
    }

    public List<UserResponse> getAllUsers(){

        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }
}
