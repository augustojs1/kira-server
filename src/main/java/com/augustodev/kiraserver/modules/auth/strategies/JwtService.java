package com.augustodev.kiraserver.modules.auth.strategies;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "E8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBCE8CEDAB29374D4DD3DBC1896D4DBC";
    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
