package com.messagegcp.Messaging.App.Message.Service;

import java.util.List;
import java.util.Optional;

import com.messagegcp.Messaging.App.Message.Entity.Message;

public interface MessageService {
	
	Message createMessage(Long userId, String title, String content) throws Exception;
	Optional<Message> getMessage(Long id);
	List<Message> getAllMessages();
	List<Message> getMessagesByUserId(Long userId);
	Message updateMessage(Long id, String title, String content) throws Exception;
	void deleteMessage(Long id);

}
