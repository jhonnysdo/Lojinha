package br.com.fiap.challengeecommercegestaoitens.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.qyvVsyK7ssLPk_DlljpF0YQW0XbXzQLVG6-j1o1KsFE";
    private static final String USERNAME = "Jhonny";
    private static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    // @Test
    // public void testExtractUsername() {
    //     JwtService jwtService = new JwtService();
    //     //generate a valid jwt token

    //     String username = jwtService.extractClaim(VALID_TOKEN, Claims::getSubject);
    //     assertEquals(USERNAME, username);
    // }

    // @Test
    // public void testExtractClaim() {
    //     JwtService jwtService = new JwtService();
    //     Claims claims = mock(Claims.class);
    //     when(claims.getSubject()).thenReturn(USERNAME);

    //     String extractedUsername = jwtService.extractClaim(VALID_TOKEN, Claims::getSubject);
    //     assertEquals(USERNAME, extractedUsername);
    // }

    // @Test
    // public void testExtractAllClaims() {
    //     JwtService jwtService = new JwtService();
    //     Claims claims = jwtService.extractAllClaims(VALID_TOKEN);
    //     String subject = claims.getSubject();
    //     assertEquals(USERNAME, subject);
    // }

    // @Test
    // public void testGetSignKey() {
    //     JwtService jwtService = new JwtService();
    //     Key signKey = jwtService.getSignKey();
    //     assertEquals(Keys.hmacShaKeyFor(SECRET.getBytes()), signKey);
    // }

}





