package com.serious.business.service;

import java.util.List;
import java.util.stream.Stream;

import com.serious.business.domain.UserDTO;

public interface UserService {

	int createUser(UserDTO user);
	
	List<UserDTO> fetchUsersById(Stream<Integer> id, boolean withSensetive);
	
	void updateUser(UserDTO user);
	
	void deleteUserById(int id);
}
