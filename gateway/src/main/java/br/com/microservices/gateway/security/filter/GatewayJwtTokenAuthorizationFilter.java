package br.com.microservices.gateway.security.filter;

import static br.com.microservices.security.util.SecurityContextUtil.setSecurityContext;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;

import br.com.microservices.core.property.JwtConfiguration;
import br.com.microservices.security.filter.JwtTokenAuthorizationFilter;
import br.com.microservices.security.token.converter.TokenConverter;

public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {
	private final Logger log = LoggerFactory.getLogger(GatewayJwtTokenAuthorizationFilter.class);

    public GatewayJwtTokenAuthorizationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();
        String signedToken = null;
        
        try {
            signedToken = tokenConverter.decryptToken(token);
            tokenConverter.validateTokenSignature(signedToken);

            setSecurityContext(SignedJWT.parse(signedToken));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}

        if (jwtConfiguration.getType().equalsIgnoreCase("signed"))
            RequestContext.getCurrentContext().addZuulRequestHeader("Authorization", jwtConfiguration.getHeader().getPrefix() + signedToken);

        chain.doFilter(request, response);
    }
}
