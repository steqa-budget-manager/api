package ru.steqa.api.schema.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseJwtScheme {
    public String access;
    public String refresh;
    public String type;

    public ResponseJwtScheme(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
        this.type = "Bearer";
    }
}
