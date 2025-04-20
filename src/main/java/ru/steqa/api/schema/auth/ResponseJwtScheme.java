package ru.steqa.api.schema.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
