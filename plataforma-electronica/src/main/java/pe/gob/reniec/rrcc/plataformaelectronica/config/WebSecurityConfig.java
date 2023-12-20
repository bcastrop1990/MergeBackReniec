package pe.gob.reniec.rrcc.plataformaelectronica.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import pe.gob.reniec.rrcc.plataformaelectronica.security.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final SecurityProperties securityProperties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/registro-firmas/validar-datos").permitAll()
        .antMatchers(HttpMethod.POST, "/registro-libros/validar-datos").permitAll()
        .antMatchers(HttpMethod.POST, "/seguimientos/validar-datos").permitAll()
        .antMatchers(HttpMethod.GET, "/ubigeos/**").permitAll()
        .antMatchers(HttpMethod.POST, "/oficinas/orec").permitAll()
        .antMatchers(HttpMethod.POST, "/seguridad/identificar").permitAll()
            .antMatchers(HttpMethod.POST, "/seguridad/cambio-clave").permitAll()
        .antMatchers("/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}



