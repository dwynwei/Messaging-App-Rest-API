package com.messagegcp.Messaging.App.Common.User.Repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.messagegcp.Messaging.App.Common.User.Entity.User;

@Repository
public interface UserRepository extends DatastoreRepository<User, Long> {
	
	Optional<User> findByGoogleId(String googleId);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findUserById (String userId);
}
