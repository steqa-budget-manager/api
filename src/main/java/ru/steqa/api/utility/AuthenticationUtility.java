package ru.steqa.api.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.steqa.api.exception.user.AuthenticationFailedException;
import ru.steqa.api.model.User;
import ru.steqa.api.scheme.auth.LoginScheme;
import ru.steqa.api.scheme.user.ResponseUserScheme;
import ru.steqa.api.service.user.IUserService;

@Component
@RequiredArgsConstructor
public class AuthenticationUtility {
    private final IUserService userService;

    public void login(LoginScheme request) {
        ResponseUserScheme user = userService.getUserByEmail(request.getEmail());
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException();
        }
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
