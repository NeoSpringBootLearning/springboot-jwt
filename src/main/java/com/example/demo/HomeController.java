package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwttoken.JwtService;
import com.example.demo.model.MyUserService;


@RestController
public class HomeController {

		@Autowired
		private AuthenticationManager authenticationManager;
		@Autowired
		private JwtService jwtService;
		@Autowired
		private MyUserService myUserService;
		
		@GetMapping("/home")
		public String handleWelcomeHome() {
			return "home";
		}
		
		@GetMapping("/admin/home")
		public String handleAdminHome() {
			return "admin_home";
		}
		
		@GetMapping("/user/home")
		public String handleUserHome() {
			return "user_home";
		}
		
		@PostMapping("/authenticate")
		public String authenticateUserAndGetJwtToken(@RequestBody LoginForm loginForm) {
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));
			if(authentication.isAuthenticated()) {
				return jwtService.generateJwtToken(myUserService.loadUserByUsername(loginForm.username()));
			}else {
				throw new UsernameNotFoundException("User does not exist");
			}
		}
}
