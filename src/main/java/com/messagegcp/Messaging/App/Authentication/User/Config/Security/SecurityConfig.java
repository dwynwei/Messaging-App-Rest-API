package com.messagegcp.Messaging.App.Authentication.User.Config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	

    public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler) {
		this.oAuth2SuccessHandler = oAuth2SuccessHandler;
	}


	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests((authz) -> authz
        		.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        		.requestMatchers("/send-email").permitAll()
        		.requestMatchers("/api/admin/**").hasRole("ADMIN")
        		.requestMatchers("/api/user/**").hasRole("USER")
        		.anyRequest().authenticated()
        		);
        http.csrf(csrf -> csrf.disable());

        http.oauth2Login(oauth2 -> oauth2
        		.successHandler(oAuth2SuccessHandler));

        return http.build();
    }
}
