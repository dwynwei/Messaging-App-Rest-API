package com.messagegcp.Messaging.App.Authentication.User.Service;

import java.util.Optional;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.messagegcp.Messaging.App.Common.User.Entity.User;

public interface UserAuthService {

	public Optional<User> registerOrUpdateUser(OAuth2User oAuth2User);
	
	public Optional<User> loginUser(String email, String token);
	
	public void logoutUser(User user);
	
	public boolean isTokenExpired(User user);
	
	public Optional<User> findUserById(String userId);
	
}
