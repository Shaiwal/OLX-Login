package com.test.olx.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.olx.login.entity.UserEntity;
import com.test.olx.login.exception.DatabaseTransactionException;
import com.test.olx.login.exception.UserAlreadyPresentException;
import com.test.olx.login.exception.UserDoesNotExistException;
import com.test.olx.login.model.User;
import com.test.olx.login.repository.UserRepository;
import com.test.olx.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public String authenticateUserSer(User user) {
		return "temptoken1234";
	}

	@Override
	public Boolean logoutUserSer(String authToken) {
		return null;
	}

	@Override
	public User registerUserSer(User user) {
		
		List<UserEntity> userEntityList = userRepository.findByUserName(user.getUserName());
		if(userEntityList!=null && !userEntityList.isEmpty()) {
			throw new UserAlreadyPresentException("User with this username already present: " + user.getUserName());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPassword(user.getPassword());
		userEntity.setPhone(user.getPhone());
		userEntity.setEmail(user.getEmail());
		userEntity.setUserName(user.getUserName());
		userEntity = userRepository.save(userEntity);
		if(userEntity != null) {
			return user;
		}else {
			throw new DatabaseTransactionException("Error while Registering User. Please try again.");
		}
	}

	@Override
	public User getUserInfoSer(String username) {
		
		List<UserEntity> userEntityList = userRepository.findByUserName(username);
		UserEntity userEntity = userEntityList.get(0);
		User user = new User();
		user.setUserName(userEntity.getUserName());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setPhone(userEntity.getPhone());
		user.setEmail(userEntity.getEmail());
		System.out.println(user);
		return user;
		
	}

	@Override
	public User findByUsername(String userName) {
		List<UserEntity> userEntityList = userRepository.findByUserName(userName);
		if(userEntityList==null || userEntityList.size()==0) {
			throw new UserDoesNotExistException("User with this username does not exist: " + userName);
		}
		//UserEntity userEntity = opUserEntity.isPresent() ? opUserEntity.get() : null;
		UserEntity userEntity = userEntityList.get(0);
		User user = new User();
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setUserName(userEntity.getUserName());
		user.setEmail(userEntity.getEmail());
		user.setPhone(userEntity.getPhone());
		return user;
	}

}
