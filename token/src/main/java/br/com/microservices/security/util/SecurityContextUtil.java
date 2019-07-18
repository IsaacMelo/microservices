package br.com.microservices.security.util;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.microservices.core.model.ApplicationUser;

public class SecurityContextUtil {
	private final static Logger log = LoggerFactory.getLogger(SecurityContextUtil.class);
    private SecurityContextUtil() {

    }

    public static void setSecurityContext(SignedJWT signedJWT) {
        try {
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String username = claims.getSubject();
            if (username == null)
                throw new JOSEException("Username missing from JWT");

            List<String> authorities = claims.getStringListClaim("authorities");

            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setId(claims.getLongClaim("userId"));
            applicationUser.setUsername(username);
            applicationUser.setRole(String.join(",", authorities));
            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(applicationUser, null, createAuthorities(authorities));
            auth.setDetails(signedJWT.serialize());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            log.error("Error setting security context ", e);
            SecurityContextHolder.clearContext();
        }
    }

    private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
