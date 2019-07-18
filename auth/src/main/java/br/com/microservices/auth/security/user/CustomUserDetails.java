package br.com.microservices.auth.security.user;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.microservices.core.model.ApplicationUser;

final class CustomUserDetails extends ApplicationUser implements UserDetails {
	private static final long serialVersionUID = -8188448747803807558L;

	CustomUserDetails(@NotNull ApplicationUser applicationUser) {
		super(applicationUser);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
