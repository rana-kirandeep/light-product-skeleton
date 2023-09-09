package com.ligthspeed.kirandeep.service;


import com.ligthspeed.kirandeep.domain.Role;
import com.ligthspeed.kirandeep.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =  User.builder().id(1l).email("demo@demo.com").username("kirandeep").
    password("password")
    .roles(Set.of(Role.builder().id(2l).name("USER").build())).build();


    return UserDetailsImpl.build(user);
  }

}
