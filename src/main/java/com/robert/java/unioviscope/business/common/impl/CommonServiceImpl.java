package com.robert.java.unioviscope.business.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.common.CommonService;
import com.robert.java.unioviscope.model.User;
import com.robert.java.unioviscope.model.exception.UserNotFoundException;
import com.robert.java.unioviscope.persistence.UserRepository;

/**
 * Clase que implementa la interfaz CommonService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.CommonService
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User findUserDetails(String userName) throws UserNotFoundException {
		if (userName == null || userName.trim().isEmpty())
			throw new UserNotFoundException("userName", "common.find.userName.required");
		User user = userRepository.findByUserName(userName);
		if (user == null)
			throw new UserNotFoundException("userName", "common.find.userName.invalid");
		return user;
	}
}
