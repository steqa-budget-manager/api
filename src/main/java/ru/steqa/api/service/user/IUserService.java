package ru.steqa.api.service.user;

import ru.steqa.api.schema.user.AddUserScheme;
import ru.steqa.api.schema.user.ResponseUserScheme;

public interface IUserService {
    ResponseUserScheme getUserByEmail(String email);
    ResponseUserScheme addUser(AddUserScheme request);
}
