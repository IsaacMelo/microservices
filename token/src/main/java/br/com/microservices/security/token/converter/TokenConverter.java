package br.com.microservices.security.token.converter;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import br.com.microservices.core.property.JwtConfiguration;

@Service
public class TokenConverter {
	private final Logger log = LoggerFactory.getLogger(TokenConverter.class);
	
	@Autowired
	private JwtConfiguration jwtConfiguration;

    public String decryptToken(String encryptedToken) throws ParseException, JOSEException {
        log.info("Decrypting token");

        JWEObject jweObject = JWEObject.parse(encryptedToken);

        DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());

        jweObject.decrypt(directDecrypter);

        log.info("Token decrypted, returning signed token . . . ");

        return jweObject.getPayload().toSignedJWT().serialize();
    }

    public void validateTokenSignature(String signedToken) throws ParseException, JOSEException {
        log.debug("Starting method to validate token signature...");
        SignedJWT signedJWT = SignedJWT.parse(signedToken);

        log.debug("Token Parsed! Retrieving public key from signed token");

        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());

        log.debug("Public key retrieved, validating signature. . . ");

        if (!signedJWT.verify(new RSASSAVerifier(publicKey)))
            throw new AccessDeniedException("Invalid token signature!");

        log.debug("The token has a valid signature");
    }
}
