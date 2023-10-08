package com.coupon.service.couponservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class WebSecurityConfig {
	
	
	@Autowired
	UserDetailsService detailsService;
	
	@Bean
    SecurityContextRepository contextRepository() {
    	return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
    			new HttpSessionSecurityContextRepository());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET,"/couponapi/coupon/{code:^[A-Z]*$}","/showGetCoupon")
        		.hasAnyRole("USER", "ADMIN")
        		.requestMatchers(HttpMethod.GET, "/showCreateCoupon","/createCoupon")
        		.hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/couponapi/createcoupon","/saveCoupon").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("ADMIN","USER")
                .requestMatchers("/","/login","/showReg","/registerUser").permitAll());
        http.logout(logout -> logout.logoutSuccessUrl("/"));
        http.csrf(csrf -> csrf.disable());
        http.securityContext(context->context.requireExplicitSave(true));
        return http.build();
    }

   
    
    @Bean
    AuthenticationManager authenticationManager() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(detailsService);
    	provider.setPasswordEncoder(passwordEncoder());
    	
		return new ProviderManager(provider);
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
