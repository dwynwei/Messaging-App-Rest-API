package com.messagegcp.Messaging.App.Authentication.User.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.messagegcp.Messaging.App.Common.User.Entity.User;
import com.messagegcp.Messaging.App.Common.User.Repository.UserRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
	private final UserRepository userRepository;
	
	@Autowired
	public UserAuthServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	

	@Override
	public Optional<User> registerOrUpdateUser(OAuth2User oAuth2User) {		
		try {
			String googleId = oAuth2User.getAttribute("sub");
			Optional<User> existingUser = userRepository.findByGoogleId(googleId);
			
			User user = existingUser.orElseGet(User::new);
			
			user.setGoogleId(googleId);
			user.setEmail(oAuth2User.getAttribute("email"));
			user.setDisplayName(oAuth2User.getAttribute("name"));
			user.setGivenName(oAuth2User.getAttribute("email"));
			user.setFamilyName(oAuth2User.getAttribute("family_name"));
			user.setProfilePicture(oAuth2User.getAttribute("picture"));
			user.setEmailVerified(oAuth2User.getAttribute("email_verified"));
			user.setLocale(oAuth2User.getAttribute("locale"));
			
			user.setToken(generateNewToken());
			user.setTokenExprationTime(calculateTokenExpration());
			userRepository.save(user);
			
			return existingUser;
			
		} catch (Exception e) {
			logger.error("Error saving user : ",e);
			throw e;		
		}
	}

	private String generateNewToken() {
		return UUID.randomUUID().toString();
	}

	private Date calculateTokenExpration() {
		final long EXPRATION_DURATION = 60*60*1000;
		return new Date(System.currentTimeMillis() + EXPRATION_DURATION);
	}


	@Override
	public Optional<User> loginUser(String email, String token) {
		try {
			Optional<User> userOpt = userRepository.findByEmail(email);
			userOpt.ifPresent(user -> {
				user.setToken(generateNewToken());
				user.setTokenExprationTime(calculateTokenExpration());
				userRepository.save(user);			
			});
			
			return userOpt;
		} catch (Exception e) {
			logger.error("Error user login user:",e);
			throw e;
		}
	}

	@Override
	public void logoutUser(User user) {
		user.setToken(null);
		user.setTokenExprationTime(null);
		userRepository.save(user);
	}

	@Override
	public boolean isTokenExpired(User user) {
		return new Date().after(user.getTokenExprationTime());
	}



	@Override
	public Optional<User> findUserById(String userId) {
		try {
			return userRepository.findUserById(userId);
		} catch (Exception e) {
			logger.error("user not found :",e,userId);
			throw e;
		}
	}

}
