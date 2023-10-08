package com.coupon.service.couponservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class SecurityServiceImp implements SecurityService {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository securityContextRepository;

	@Override
	public boolean login(String username, String password,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		UserDetails user=userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(user,password,user.getAuthorities());
		authenticationManager.authenticate(token);
		boolean result=token.isAuthenticated();
		if(result) {
			SecurityContext context=SecurityContextHolder.getContext();
			securityContextRepository.saveContext(context, httpServletRequest, httpServletResponse);
			context.setAuthentication(token);
		}
		return result;
	}

}
