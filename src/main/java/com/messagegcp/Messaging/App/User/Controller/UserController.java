package com.messagegcp.Messaging.App.User.Controller;

import com.messagegcp.Messaging.App.User.Dto.UserDto;
import com.messagegcp.Messaging.App.User.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Yeni Kullanıcı Oluştur", description = "Yeni bir kullanıcı kaydı oluşturur.")
    @ApiResponse(responseCode = "200", description = "Başarılı bir şekilde oluşturuldu")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
    	UserDto createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kullanıcıyı Getir", description = "Belirtilen ID'ye sahip kullanıcıyı getirir.")
    @ApiResponse(responseCode = "200", description = "Kullanıcı bulundu")
    @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Bütün Kullanıcıları Getir", description = "Veritabanındaki Bütün Kullanıcıları Getirir.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not_Found")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kullanıcıyı Güncelle", description = "Belirtilen ID'ye sahip kullanıcının bilgilerini günceller.")
    @ApiResponse(responseCode = "200", description = "Kullanıcı başarılı bir şekilde güncellendi")
    @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDTO) {
        return userService.updateUser(id, userDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Kullanıcıyı Sil", description = "Belirtilen ID'ye sahip kullanıcıyı siler.")
    @ApiResponse(responseCode = "200", description = "Kullanıcı başarılı bir şekilde silindi")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{userId}/roles/{role}")
    @Operation(summary = "Rol Ekle", description = "Kullanıcıya yeni bir rol tanımlaması yapar")
    @ApiResponse(responseCode = "200", description = "Başarılı bir şekilde rol oluşturuldu")
    public ResponseEntity<UserDto> addRoleToUser(@PathVariable Long userId, @PathVariable String role) {
        return userService.addRoleToUser(userId, role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{userId}/roles")
    @Operation(summary = "Rol Güncelle", description = "Kullanıcı rolünü günceller")
    @ApiResponse(responseCode = "200", description = "Başarılı bir şekilde rol güncellendi")
    public ResponseEntity<UserDto> updateRolesOfUser(@PathVariable Long userId, @RequestBody List<String> newRoles) {
        return userService.updateRolesOfUser(userId, newRoles)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
}
