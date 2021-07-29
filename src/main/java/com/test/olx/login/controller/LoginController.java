package com.test.olx.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.olx.login.model.User;
import com.test.olx.login.service.LoginService;
import com.test.olx.login.service.impl.UserDetailsServiceImpl;
import com.test.olx.login.utils.JwtUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class LoginController {
	
	private static Map<String, String> userTokenMap = new HashMap<String, String>();
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@RequestMapping(path = "/user/authenticate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Returns user authentication token", notes = "API to authenticate user")
	public ResponseEntity<User> authenticateUser(@ApiParam(value = "User Object") @RequestBody User user){
		try {
			//flow control will go to authentication provider from here
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));//username, password));//
		}catch (BadCredentialsException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BadCredentialsException(user.getUserName());//username);//
		}
		User userObj = loginservice.findByUsername(user.getUserName());
		String jwtToken = jwtUtils.generateToken(user.getUserName());//username);//
		userObj.setJwtToken(jwtToken);
		userTokenMap.put(user.getUserName(), jwtToken);
		return new ResponseEntity<User>(userObj, HttpStatus.OK);
		//return new ResponseEntity<String>(this.loginservice.authenticateUserSer(user), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user/logout", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Returns boolean status on user logout", notes = "API to logout user")
	public ResponseEntity<Boolean> logoutUser(@RequestHeader("auth-token") String authToken){
		String username = jwtUtils.extractUsername(authToken);
		if(username !=null) {
			userTokenMap.remove(username);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Returns registered User", notes = "API to register a user")
	public ResponseEntity<User> registerUser(@ApiParam(value = "User Object") @RequestBody User user){
		return new ResponseEntity<User>(this.loginservice.registerUserSer(user), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Returns user info from authentication token", notes = "API to get user info")
	public ResponseEntity<User> getUserInfo(@RequestHeader("auth-token") String authToken){
		String username = jwtUtils.extractUsername(authToken);
		return new ResponseEntity<User>(this.loginservice.getUserInfoSer(username), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/token/validate", method = RequestMethod.GET)
	public ResponseEntity<Boolean> isTokenValid(@RequestHeader("Authorization") String jwtToken) {
		//Extract the jwtToken by removing the word "Bearer " from jwtToken
		jwtToken = jwtToken.substring(7, jwtToken.length());
		//Extract username from the jwtToken
		String username = jwtUtils.extractUsername(jwtToken);
		//Get UserDetails
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//Validate the token
		boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
		if(isTokenValid)
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

}
