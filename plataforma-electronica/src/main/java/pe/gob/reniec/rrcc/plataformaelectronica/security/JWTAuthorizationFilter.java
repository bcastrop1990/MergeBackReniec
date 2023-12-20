package pe.gob.reniec.rrcc.plataformaelectronica.security;

import static pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityConstant.AUTH_HEADER_NAME;
import static pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityConstant.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pe.gob.reniec.rrcc.plataformaelectronica.config.SecurityProperties;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private SecurityProperties securityProperties;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  SecurityProperties securityProperties) {
        super(authenticationManager);
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(AUTH_HEADER_NAME);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            try {
                // parse the token.
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(this.securityProperties.getSecretKey().getBytes())
                        .build()
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                ObjectMapper mapper = new ObjectMapper();
                UserInfo userInfo = mapper.convertValue(claims.get("personaInfo"), UserInfo.class);
                List<String> authorities;

                if (Objects.nonNull(userInfo.getPermisos())) {
                    authorities = userInfo.getPermisos();
                } else {
                    authorities = Collections.emptyList();
                }
                List<GrantedAuthority> grantedAuthorityList = authorities
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                if (userInfo != null) {
                    return new UsernamePasswordAuthenticationToken(userInfo, null, grantedAuthorityList);
                }
            } catch (JwtException e) {
                return null;
            }
            return null;
        }
        return null;
    }

}
