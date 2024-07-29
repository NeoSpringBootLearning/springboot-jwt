package com.example.demo.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

	@Autowired
	private MyUserRepository myUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<MyUser> user = myUserRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User Not Exits " + username);
		} else {
			var newUser = user.get();
			return User.builder().username(newUser.getUsername()).password(newUser.getPassword())
					.roles(newUser.getRole()).build();

		}
	}

}
