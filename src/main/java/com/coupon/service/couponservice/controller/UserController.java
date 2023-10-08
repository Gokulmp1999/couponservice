package com.coupon.service.couponservice.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coupon.service.couponservice.model.Role;
import com.coupon.service.couponservice.model.User;
import com.coupon.service.couponservice.repository.UserRepository;
import com.coupon.service.couponservice.security.SecurityServiceImp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	SecurityServiceImp securityServiceImp;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostMapping("/registerUser")
	public String registerUser(User user) {
		Set<Role> roles = new HashSet<>();
		Role role=new Role();
		role.setId(2l);
		role.setName("USER");
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login";
	}
	
	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(String email,String password,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		boolean loginResponse = securityServiceImp.login(email, password,httpServletRequest,httpServletResponse);
		if(loginResponse) {
			return "index";
		}else {
			return "login";
		}
	}
	
	@GetMapping("/showReg")
	public String showReg() {
		return "registerUser";
	}

}
