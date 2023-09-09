package com.ligthspeed.kirandeep.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {

	private Long id;


	private String username;


	private String email;


	private String password;
	

	private Set<Role> roles = new HashSet<>();


}
