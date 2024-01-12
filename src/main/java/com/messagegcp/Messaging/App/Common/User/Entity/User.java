package com.messagegcp.Messaging.App.Common.User.Entity;

import org.springframework.data.annotation.Id;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
public class User {
    
    @Id
    private Long id;
    
    private String googleId;
    private String email; 
    private String displayName;
    private String givenName;
    private String familyName; 
    private String profilePicture; 
    private boolean emailVerified; 
    private String locale;
    private String token;
    private Date tokenExprationTime;
    private List<Role> roles = new ArrayList<>();
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExprationTime() {
		return tokenExprationTime;
	}

	public void setTokenExprationTime(Date tokenExprationTime) {
		this.tokenExprationTime = tokenExprationTime;
	}

	public List<Role> getRoles() {
		return roles == null ? new ArrayList<>() : roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
