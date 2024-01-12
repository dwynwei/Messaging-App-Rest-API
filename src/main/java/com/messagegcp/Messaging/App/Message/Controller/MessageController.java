package com.messagegcp.Messaging.App.Message.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.messagegcp.Messaging.App.Message.Entity.Message;
import com.messagegcp.Messaging.App.Message.Service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private final MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping
    @Operation(summary = "Mesaj Oluştur", description = "Yeni bir mesaj kaydı oluşturur.")
	@ApiResponse(responseCode = "200", description = "Mesaj başarılı bir şekilde oluşturuldu")
    @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    public ResponseEntity<Message> createMessage(@RequestParam Long userId, 
                                                 @RequestParam String title, 
                                                 @RequestParam String content) throws Exception {
        Message message = messageService.createMessage(userId, title, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{messageId}")
    @Operation(summary = "Mesajı Getir", description = "Belirtilen ID'ye sahip mesajı getirir.")
    @ApiResponse(responseCode = "200", description = "Mesaj başarılı bir şekilde getirildi")
    @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    public ResponseEntity<Message> getMessage(@PathVariable Long messageId) {
        return messageService.getMessage(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Bütün Mesajları Getir", description = "Veritabanındaki bütün mesajları getirir.")
    @ApiResponse(responseCode = "200", description = "Mesajlar başarılı bir şekilde getirildi")
    @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PutMapping("/{messageId}")
    @Operation(summary = "Mesajı Güncelle", description = "Belirtilen ID'ye sahip mesajın içeriğini günceller.")
    @ApiResponse(responseCode = "200", description = "Mesaj başarılı bir şekilde güncellendi")
    @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    public ResponseEntity<Message> updateMessage(@PathVariable Long messageId, 
                                                 @RequestParam String title, 
                                                 @RequestParam String content) throws Exception {
        Message updatedMessage = messageService.updateMessage(messageId, title, content);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Mesajı Sil", description = "Belirtilen ID'ye sahip mesajı siler.")
    @ApiResponse(responseCode = "200", description = "Mesaj başarılı bir şekilde silindi")
    @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }
	
}
