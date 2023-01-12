package com.boot.www.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

	public UserAuthentication(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public UserAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	
}
