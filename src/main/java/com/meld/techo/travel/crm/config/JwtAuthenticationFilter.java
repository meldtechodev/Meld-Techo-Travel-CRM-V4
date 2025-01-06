package com.meld.techo.travel.crm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.meld.techo.travel.crm.security.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter  {
	
	@Autowired
    private JwtUtils jwtUtils;
	 @Autowired
	    private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String token = resolveToken(request);
	        if (token != null && jwtUtils.validateToken(token)) {
	            String username = jwtUtils.getUsernameFromToken(token);
	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }
	        filterChain.doFilter(request, response);
	    }

	    private String resolveToken(HttpServletRequest request) {
	        String bearerToken = request.getHeader("Authorization");
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7);
	        }
	        return null;
	    }
	}


