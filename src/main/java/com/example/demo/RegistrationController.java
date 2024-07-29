package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MyUser;
import com.example.demo.model.MyUserRepository;

@RestController
public class RegistrationController {
		
		@Autowired
		private MyUserRepository myUserRepository;
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@PostMapping("/create/user")
		public MyUser createUser(@RequestBody MyUser myUser) {
				myUser.setPassword(passwordEncoder.encode(myUser.getPassword())); 
				return myUserRepository.save(myUser);
		}
}
