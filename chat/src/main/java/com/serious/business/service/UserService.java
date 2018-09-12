package com.serious.business.service;

import java.util.stream.Stream;

import com.serious.business.domain.UserDTO;

public interface UserService {

	void createUser(UserDTO user);
	
	Stream<UserDTO> fetchUsersById(Stream<Long> id);
}
