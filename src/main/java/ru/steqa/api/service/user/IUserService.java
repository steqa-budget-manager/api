package ru.steqa.api.service.user;

import ru.steqa.api.scheme.user.AddUserScheme;
import ru.steqa.api.scheme.user.ResponseUserScheme;

public interface IUserService {
    ResponseUserScheme getUserByEmail(String email);
    ResponseUserScheme addUser(AddUserScheme request);
}
