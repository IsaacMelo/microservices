package br.com.microservices.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUser implements AbstractEntity {
	private static final long serialVersionUID = -5573370522167587728L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "The field 'username' is mandatory")
	@Column(nullable = false)
	private String username;

	@NotNull(message = "The field 'password' is mandatory")
	@Column(nullable = false)
	private String password;

	@NotNull(message = "The field 'role' is mandatory")
	@Column(nullable = false)
	private String role = "USER";

	public ApplicationUser() {
	}

	public ApplicationUser(@NotNull ApplicationUser applicationUser) {
		this.id = applicationUser.getId();
		this.username = applicationUser.getUsername();
		this.password = applicationUser.getPassword();
		this.role = applicationUser.getRole();
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUser other = (ApplicationUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ApplicationUser [id=" + id + ", username=" + username + ", role=" + role + "]";
	}
	
}
