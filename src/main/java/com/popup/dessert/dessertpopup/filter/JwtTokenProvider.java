package com.popup.dessert.dessertpopup.filter;

import com.popup.dessert.dessertpopup.Dto.TokenResponse;
import com.popup.dessert.dessertpopup.Respository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private final UserRepository userRepository;

  public JwtTokenProvider(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final String SECRET_KEY = "next_plan_api_server_jwt_secret_key";
  private final long ACCESS_TTL = 20 * 60 * 1000;
  private final long REFRESH_TTL = 24 * 60 * 60 * 1000;

  public TokenResponse generateToken(Authentication authentication) {
    List<String> authorities = authentication.getAuthorities()
        .stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    Date now = new Date();
    Date accessExpiredDate = new Date(now.getTime() + ACCESS_TTL);
    Date refreshExpiredDate = new Date(now.getTime() + REFRESH_TTL);

    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    String accessToken = Jwts.builder()
        .setSubject(authentication.getName())
        .claim("auth", authorities)
        .setExpiration(accessExpiredDate)
        .signWith(secretKey)
        .compact();

    String refreshToken = Jwts.builder()
        .setExpiration(refreshExpiredDate)
        .signWith(secretKey)
        .compact();

    return new TokenResponse("Bearer " + accessToken, "Bearer " + refreshToken);
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(Encoders.BASE64.encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(accessToken)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("auth").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetails userDetails = userRepository.findUserByEmail(claims.getSubject())
        .orElseThrow();

    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

  public String generateSMSToken(String phone, String code) {
    SecretKey secretKey = Keys.hmacShaKeyFor((SECRET_KEY + code).getBytes(StandardCharsets.UTF_8));

    Date accessExpiredDate = new Date(new Date().getTime() + 3 * 60 * 1000);

    String accessToken = Jwts.builder()
        .claim("phone", phone)
        .setExpiration(accessExpiredDate)
        .signWith(secretKey)
        .compact();

    return "Bearer " + accessToken;
  }

  public Boolean checkSMSToken(String phone, String code, String accessToken) throws SignatureException {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(Encoders.BASE64.encode((SECRET_KEY + code).getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(accessToken)
        .getBody();

    return claims.get("phone").equals(phone);
  }


}
