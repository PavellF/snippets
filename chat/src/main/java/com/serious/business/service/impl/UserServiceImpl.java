package com.serious.business.service.impl;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.serious.business.domain.UserDTO;
import com.serious.business.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void createUser(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stream<UserDTO> fetchUsersById(Stream<Long> id) {
		// TODO Auto-generated method stub
		return null;
	}

}
