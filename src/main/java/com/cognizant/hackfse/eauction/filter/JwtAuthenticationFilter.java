package com.cognizant.hackfse.eauction.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Validating Token!!!!!");
        log.info("--------> requestURI - {}, method - {}, params - {}",request.getRequestURI(), request.getMethod(), request.getQueryString());
        //if (request.getRequestURI().contains("/api/v1/person")) {
        	filterChain.doFilter(request, response);
        	//return;
        //}
        
        //filterChain.doFilter(request, response);
    }

}