package br.com.microservices.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import br.com.microservices.core.property.JwtConfiguration;

public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
	protected final JwtConfiguration jwtConfiguration;

    public SecurityTokenConfig(JwtConfiguration jwtConfiguration) {
    	this.jwtConfiguration = jwtConfiguration;
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html", "/config/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                .antMatchers("/course/v1/course/**").hasRole("ADMIN")
                .antMatchers("/auth/user/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated();
    }

}
