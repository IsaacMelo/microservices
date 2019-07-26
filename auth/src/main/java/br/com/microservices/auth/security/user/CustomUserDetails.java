package br.com.microservices.auth.security.user;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.microservices.core.model.auth.User;

final class CustomUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = -8188448747803807558L;
	private Collection<? extends GrantedAuthority> authority;
	
	CustomUserDetails(@NotNull User user) {
		super(user);
	}

	public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authority) {
		super(user);
		this.authority = authority;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
		return authority;
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
