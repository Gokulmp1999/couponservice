package com.coupon.service.couponservice.security;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecurityService {
boolean login(String username,String password,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
}
