package br.com.microservices.security.filter;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import br.com.microservices.core.property.JwtConfiguration;
import br.com.microservices.security.token.converter.TokenConverter;
import br.com.microservices.security.util.SecurityContextUtil;

public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
	private final Logger log = LoggerFactory.getLogger(JwtTokenAuthorizationFilter.class);
	
	protected final JwtConfiguration jwtConfiguration;
	protected final TokenConverter tokenConverter;

    public JwtTokenAuthorizationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
		this.jwtConfiguration = jwtConfiguration;
		this.tokenConverter = tokenConverter;
	}

	@Override
    @SuppressWarnings("Duplicates")
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        SecurityContextUtil.setSecurityContext(equalsIgnoreCase("signed", jwtConfiguration.getType()) ? validate(token) : decryptValidating(token));

        chain.doFilter(request, response);
    }

    private SignedJWT decryptValidating(String encryptedToken) {
        String signedToken;
		try {
			signedToken = tokenConverter.decryptToken(encryptedToken);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
        
		return validate(signedToken);
    }

    private SignedJWT validate(String signedToken) {
		SignedJWT parse = null;
    	try {
			tokenConverter.validateTokenSignature(signedToken);
			parse = SignedJWT.parse(signedToken);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}

        return parse;
    }
}
