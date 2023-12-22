package com.messagegcp.Messaging.App.Authentication.User.Config.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.messagegcp.Messaging.App.Authentication.User.Service.UserAuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
    private final UserAuthService userService;

    public OAuth2SuccessHandler(UserAuthService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        // Kullanıcı kaydı veya güncellemesi
        userService.registerOrUpdateUser(oAuth2User);

        // Başarılı giriş sonrası istenen sayfaya yönlendirme
        getRedirectStrategy().sendRedirect(request, response, "/swagger-ui/index.html");
    }
}
