package com.serious.business.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.serious.business.domain.Role;
import com.serious.business.domain.UserDTO;
import com.serious.business.repository.UserRepository;
import com.serious.business.repository.domain.User;
import com.serious.business.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public int createUser(UserDTO user) {
		Assert.notNull(user, "The given User must not be null!");
		User toCreate = new User();
		toCreate.setEmail(user.getEmail());
		toCreate.setPassword(user.getPassword());
		toCreate.setRole(Role.USER);
		toCreate.setDate(Instant.now());
		
		return userRepository.save(toCreate).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> fetchUsersById(Stream<Integer> ids,
			boolean withSensetive) {
		
		Assert.notNull(ids, "The given Stream of ids must not be null!");
		
		return userRepository.findAllById(ids.collect(Collectors.toSet()))
				.stream().map((User user) -> {
					UserDTO.Builder newUser = UserDTO.builder()
							.withId(user.getId())
							.withRole(user.getRole())
							.withEmail(user.getEmail())
							.withDate(user.getDate());
					
					if (withSensetive) {
						newUser.withPassword(user.getPassword());
					}
					
					return newUser.build();
				}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateUser(UserDTO user) {
		
		Assert.notNull(user, "The given User must not be null!");
		User toUpdate = new User();
		toUpdate.setEmail(user.getEmail());
		toUpdate.setPassword(user.getPassword());
		toUpdate.setId(user.getId());
		
		userRepository.save(toUpdate);
		
	}

	@Override
	public void deleteUserById(int id) {
		userRepository.deleteById(id);
		
	}

	

}
