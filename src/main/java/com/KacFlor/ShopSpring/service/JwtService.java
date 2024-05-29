package com.KacFlor.ShopSpring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService{

    private String jwtSecret = "Jh82xKs93vL64oP75mN48aS36dF19gG20hH73jK82lL94xO27pR35sT68uV25wZ36qA19tB47cD58eF39gH20iJ73kL82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20hI73jK82lM94oN27pS35rT68uV25wX36yZ47bC58dE39fG20h\n";

    public String extractLogin(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaim,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractLogin(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
