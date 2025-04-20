package ru.steqa.api.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.steqa.api.config.properties.SecurityProperties;
import ru.steqa.api.exception.auth.TokenExpiredException;
import ru.steqa.api.exception.auth.TokenInvalidException;
import ru.steqa.api.exception.auth.TokenNotProvidedException;
import ru.steqa.api.exception.auth.TokenTypeInvalidException;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.IUserRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenUtility {
    private final IUserRepository userRepository;
    private final SecurityProperties securityProperties;

    private final String TYPE_FIELD_NAME = "type";
    private final String ACCESS_TYPE = "access";
    private final String REFRESH_TYPE = "refresh";

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getJwt().getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String subject) {
        Date now = new Date();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + securityProperties.getJwt().getAccess().getExpirationMs()))
                .claim(TYPE_FIELD_NAME, ACCESS_TYPE)
                .signWith(getSecretKey())
                .compact();
    }

    public String createRefreshToken(String subject) {
        Date now = new Date();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + securityProperties.getJwt().getRefresh().getExpirationMs()))
                .claim(TYPE_FIELD_NAME, REFRESH_TYPE)
                .signWith(getSecretKey())
                .compact();
    }

    public User validateAccessToken(String token) {
        return validateToken(token, ACCESS_TYPE);
    }

    public User validateRefreshToken(String token) {
        return validateToken(token, REFRESH_TYPE);
    }

    private User validateToken(String token, String tokenType) {
        if (token == null) throw new TokenNotProvidedException();

        try {
            isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (MalformedJwtException e) {
            throw new TokenInvalidException();
        }

        String type = getTypeFromToken(token);
        if (!Objects.equals(type, tokenType)) throw new TokenTypeInvalidException(type, tokenType);

        String email = getSubjectFromToken(token);
        User user = userRepository.findByEmail(email).orElseThrow(TokenInvalidException::new);

        return user;
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public String getSubjectFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getTypeFromToken(String token) {
        return getClaims(token).get(TYPE_FIELD_NAME, String.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
