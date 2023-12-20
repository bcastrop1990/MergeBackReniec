package pe.gob.reniec.rrcc.plataformaelectronica.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.reniec.rrcc.plataformaelectronica.config.SecurityProperties;

@Component
@AllArgsConstructor
public class JWTUtil {
  private SecurityProperties securityProperties;

  public String createExternalToken(UserInfo userInfo) {
    Map<String, Object> extra = new HashMap<>();
    long expirationTime = this.securityProperties.getExpirationTime() * 1000;
    Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
    extra.put("personaInfo", userInfo);
    return Jwts.builder()
        .setSubject(userInfo.getDni())
        .setExpiration(expirationDate)
        .setIssuedAt(new Date())
        .addClaims(extra)
        .signWith(Keys.hmacShaKeyFor(this.securityProperties.getSecretKey().getBytes()))
        .compact();
  }

}
