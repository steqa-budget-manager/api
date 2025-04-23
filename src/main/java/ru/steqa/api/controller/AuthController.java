package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.steqa.api.scheme.auth.RefreshScheme;
import ru.steqa.api.scheme.auth.ResponseJwtScheme;
import ru.steqa.api.scheme.auth.LoginScheme;
import ru.steqa.api.scheme.user.AddUserScheme;
import ru.steqa.api.scheme.user.ResponseUserScheme;
import ru.steqa.api.service.user.IUserService;
import ru.steqa.api.utility.AuthenticationUtility;
import ru.steqa.api.utility.JwtTokenUtility;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@SecurityRequirements
public class AuthController {
    private final IUserService userService;
    private final JwtTokenUtility jwtTokenUtility;
    private final AuthenticationUtility authenticationUtility;

    @PostMapping("/signup")
    @Operation(summary = "Signup")
    public ResponseJwtScheme signup(@RequestBody @Valid AddUserScheme request) {
        ResponseUserScheme user = userService.addUser(request);
        String accessToken = jwtTokenUtility.createAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtTokenUtility.createRefreshToken(user.getEmail(), user.getId());
        return new ResponseJwtScheme(accessToken, refreshToken);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseJwtScheme login(@RequestBody @Valid LoginScheme request) {
        ResponseUserScheme user = authenticationUtility.login(request);
        String accessToken = jwtTokenUtility.createAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtTokenUtility.createRefreshToken(user.getEmail(), user.getId());
        return new ResponseJwtScheme(accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access and refresh tokens")
    public ResponseJwtScheme refresh(@RequestBody @Valid RefreshScheme request) {
        jwtTokenUtility.validateRefreshToken(request.getRefresh());
        String userEmail = jwtTokenUtility.getSubjectFromToken(request.getRefresh());
        Long userId = jwtTokenUtility.getUserIdFromToken(request.getRefresh());
        String accessToken = jwtTokenUtility.createAccessToken(userEmail, userId);
        String refreshToken = jwtTokenUtility.createRefreshToken(userEmail, userId);
        return new ResponseJwtScheme(accessToken, refreshToken);
    }
}