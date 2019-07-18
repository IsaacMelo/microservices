package br.com.microservices.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration {
	private String loginUrl = "/login/**";
	@NestedConfigurationProperty
	private Header header = new Header();
	private int expiration = 3600;
	private String privateKey = "qxBEEQv7E8aviX1KUcdOiF5ve5COUPAr";
	private String type = "encrypted";

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "JwtConfiguration [loginUrl=" + loginUrl + ", header=" + header + ", expiration=" + expiration
				+ ", privateKey=" + privateKey + ", type=" + type + "]";
	}

}
