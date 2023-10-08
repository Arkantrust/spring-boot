package me.arkantrust.springboot.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // TODO: Use `System.getenv("SECRET_KEY")` instead of hardcoding the secret key
    private final String SECRET_KEY = "6DADBBB41B91D406A786D48405C51131E8EACF350933740EC6F376BD061CD72F";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public String generateToken(UserDetails user) {

        return generateToken(new HashMap<>(), user);

    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public Boolean isTokenValid(String token, UserDetails user) {

        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {

        return ExtractExpiration(token).before(new Date());

    }

    private Date ExtractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();

    }

    private Key getSigningKey() {

        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);

    }

}
