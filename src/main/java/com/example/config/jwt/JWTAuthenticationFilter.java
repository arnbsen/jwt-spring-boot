package com.example.config.jwt;

import java.io.IOException;

import io.jsonwebtoken.JwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilterUtils jwtFilterUtils;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String bearerToken = getJwtFromRequest(request);
		String userName = null;
		if(StringUtils.hasText(bearerToken)) {
			try {
				userName = jwtFilterUtils.getUsernameFromToken(bearerToken);
			}catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (JwtException e) {
				System.out.println("JWT Token is Invalid");
			}
			
		} else {
			logger.warn("Bearer Does not exist");
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtFilterUtils.validateToken(bearerToken, userDetails)) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken
			.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
	

}
