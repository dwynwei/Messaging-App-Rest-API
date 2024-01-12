package com.messagegcp.Messaging.App.User.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.messagegcp.Messaging.App.Common.User.Entity.Role;
import com.messagegcp.Messaging.App.Common.User.Entity.User;
import com.messagegcp.Messaging.App.Common.User.Repository.UserRepository;
import com.messagegcp.Messaging.App.User.Dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
		
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public Optional<UserDto> getUser(Long id) {
		return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class));
	}

	@Override
	public Optional<UserDto> updateUser(Long id, UserDto userDto) {
		return userRepository.findById(id).map(existingUser -> {
            modelMapper.map(userDto, existingUser);
            userRepository.save(existingUser);
            return modelMapper.map(existingUser, UserDto.class);
        });
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);		
	}

	@Override
	public List<UserDto> getAllUsers() {
	    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
	            .map(user -> modelMapper.map(user, UserDto.class))
	            .collect(Collectors.toList());
	}

	@Override
	public Optional<UserDto> addRoleToUser(Long userId, String role) {
	    try {
	    	if (!Role.contains(role.toUpperCase())) {
	            throw new IllegalArgumentException("Invalid role: " + role);
	        }

	    	return userRepository.findById(userId).map(user -> {
	            List<Role> roles;
	            try {
	                roles = user.getRoles();
	                if (roles == null) {
	                    roles = new ArrayList<>();
	                }
	                Role newRole = Role.valueOf(role.toUpperCase());
	                if (!roles.contains(newRole)) {
	                    roles.add(newRole);
	                }
	            } catch (UnsupportedOperationException e) {
	                roles = new ArrayList<>(user.getRoles());
	                roles.add(Role.valueOf(role.toUpperCase()));
	            }
	            user.setRoles(roles);
	            User updatedUser = userRepository.save(user);
	            return modelMapper.map(updatedUser, UserDto.class);
	        });
		} catch (Exception e) {
			logger.error("Role Error : ",e);
			e.getCause().printStackTrace();
			throw e;
		}
	}

	@Override
	public Optional<UserDto> updateRolesOfUser(Long userId, List<String> newRoles) {
	    return userRepository.findById(userId).map(user -> {
	        List<Role> updatedRoles = newRoles.stream()
	            .map(String::toUpperCase)
	            .filter(roleStr -> Role.contains(roleStr))
	            .map(Role::valueOf)
	            .collect(Collectors.toList());
	        user.setRoles(updatedRoles);
	        userRepository.save(user);
	        return modelMapper.map(user, UserDto.class);
	    });
	}


}
