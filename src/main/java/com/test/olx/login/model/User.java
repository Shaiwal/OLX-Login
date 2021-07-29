package com.test.olx.login.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description="User Model Description")
public class User {
	@ApiModelProperty(value = "User Id", dataType = "Integer")
	private int id;
	@ApiModelProperty(value = "User First Name")
	private String firstName;
	@ApiModelProperty(value = "User Last Name")
	private String lastName;
	@ApiModelProperty(value = "User username")
	private String userName;
	@ApiModelProperty(value = "User password")
	private String password;
	@ApiModelProperty(value = "User email")
	private String email;
	@ApiModelProperty(value = "User phone")
	private String phone;
	@ApiModelProperty(value = "User JWT Token")
	private String jwtToken;
}
