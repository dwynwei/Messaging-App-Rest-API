package com.messagegcp.Messaging.App.Message.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.messagegcp.Messaging.App.Common.Async.Service.TaskCreateService;
import com.messagegcp.Messaging.App.Common.User.Entity.User;
import com.messagegcp.Messaging.App.Common.User.Repository.UserRepository;
import com.messagegcp.Messaging.App.Message.Entity.Message;
import com.messagegcp.Messaging.App.Message.Repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	private final TaskCreateService taskCreateService;
	private final Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
    public MessageServiceImpl(MessageRepository messageRepository, 
                              UserRepository userRepository, 
                              TaskCreateService taskCreateService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.taskCreateService = taskCreateService;
    }

	@Override
	public Message createMessage(Long userId, String title, String content) throws Exception {
	    try {
	        Message message = new Message();
	        User user = userRepository.findById(userId).orElseThrow(
	            () -> new RuntimeException("User not found")
	        );
	        message.setUser(user);
	        message.setTitle(title);
	        message.setContent(content);
	        message.setTimeStamp(LocalDateTime.now());
	        taskCreateService.createTask(user.getEmail(), title, content);
	        return messageRepository.save(message);
	    } catch (Exception e) {
	        logger.error("Error creating message: ", e);
	        throw e;
	    }
	}


	@Override
	public Optional<Message> getMessage(Long id) {
		try {
			return messageRepository.findById(id);			
		} catch (Exception e) {
			logger.error("Get Message Error : ",e);
			e.getCause().printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Message> getAllMessages() {
		try {			
			Iterable<Message> allMessages = messageRepository.findAll();
			return StreamSupport.stream(allMessages.spliterator(), false)
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Get All Messages Error : ",e);
			e.getCause().printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Message> getMessagesByUserId(Long userId) {
		try {			
			return messageRepository.findByUserId(userId);
		} catch (Exception e) {
			logger.error("getMessagesByUserId Error : ",e);
			e.getCause().printStackTrace();
			throw e;
		}
	}

	@Override
	public Message updateMessage(Long id, String title, String content) throws Exception {
		try {			
			Message message = getMessage(id)
					.orElseThrow(() -> new RuntimeException("Message not found"));
			message.setTitle(title);
			message.setContent(content);
			message.setEditedAt(LocalDateTime.now());
			taskCreateService.createTask(message.getUser().getEmail(),title, content);
			return messageRepository.save(message);
		} catch (Exception e) {
			logger.error("Error creating message: ", e);
	        throw e;
		}
	}

	@Override
	public void deleteMessage(Long id) {
		try {
			messageRepository.deleteById(id);		
		} catch (Exception e) {
			logger.error("deleteMessage Error : ",e);
			e.getCause().printStackTrace();
			throw e;
		}
	}
	
}
