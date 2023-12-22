package com.messagegcp.Messaging.App.Authentication.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.messagegcp.Messaging.App.Authentication.User.Service.UserAuthService;
import com.messagegcp.Messaging.App.Common.User.Entity.User;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAuthController {

	@Autowired
    private final UserAuthService userService;

    public UserAuthController(UserAuthService userService) {
        this.userService = userService;
    }

    // Kullanıcı kaydı veya güncellemesi
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> registerOrUpdateUser(@AuthenticationPrincipal OAuth2User principal) {
        Optional<User> userOpt = userService.registerOrUpdateUser(principal);
        return userOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Kullanıcı girişi
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String email) {
        String token = "";
        Optional<User> userOpt = userService.loginUser(email, token);
        return userOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Kullanıcı çıkışı
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestParam String userId) {
        Optional<User> userOpt = userService.findUserById(userId);
        userOpt.ifPresent(userService::logoutUser);
        return ResponseEntity.ok().build();
    }

    // Token süresi kontrolü
    @GetMapping("/token-expired")
    public ResponseEntity<Boolean> isTokenExpired(@RequestParam String userId) {
        Optional<User> userOpt = userService.findUserById(userId);
        return userOpt.map(userService::isTokenExpired)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
