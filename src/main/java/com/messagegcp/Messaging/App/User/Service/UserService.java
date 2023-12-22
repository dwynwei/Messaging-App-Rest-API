package com.messagegcp.Messaging.App.User.Service;

import java.util.List;
import java.util.Optional;

import com.messagegcp.Messaging.App.User.Dto.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	Optional<UserDto> getUser(Long id);
	Optional<UserDto> updateUser(Long id, UserDto userDTO);
	void deleteUser(Long id);
	List<UserDto> getAllUsers();
	Optional<UserDto> addRoleToUser(Long userId, String role);
	Optional<UserDto> updateRolesOfUser(Long userId, List<String> newRoles);
}
