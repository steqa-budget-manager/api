package ru.steqa.api.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.user.UserEmailExistsException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.user.AddUserScheme;
import ru.steqa.api.scheme.user.ResponseUserScheme;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserScheme getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return ResponseUserScheme.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public ResponseUserScheme addUser(AddUserScheme request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailExistsException();
        }
        User userToAdd = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User user = userRepository.save(userToAdd);
        return ResponseUserScheme.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
