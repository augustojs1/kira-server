package com.augustodev.kiraserver.modules.users;

import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findUserByIdElseThrow(Integer userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with this id not found!");
        }

        return user.get();
    }
}
