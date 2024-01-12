package com.messagegcp.Messaging.App.Message.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.messagegcp.Messaging.App.Message.Entity.Message;

@Repository
public interface MessageRepository extends DatastoreRepository<Message, Long>{
	
	List<Message> findByUserId(Long id);

}
