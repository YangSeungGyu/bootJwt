package com.boot.www.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info(request.getRequestURI());
		
		if(!request.getRequestURI().contains("login")&&!request.getRequestURI().contains("favicon")) {
			log.info("토큰 체크");
			try {
				String jwt = getJwtFromRequest(request);
				if(!StringUtils.isEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {
					String userId = JwtTokenProvider.getUserIdFromJWT(jwt);
					log.info("userId"+userId);
					
					UserAuthentication authentication = new UserAuthentication(userId,null,null);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext()
					.setAuthentication(authentication);
				} else {
					if(StringUtils.isEmpty(jwt)) {
						request.setAttribute("unauthorization", "401 인증키 없음.");
					}
					
					if(JwtTokenProvider.validateToken(jwt)) {
						request.setAttribute("unauthorization", "401-001 인증키 만료.");
					}
					
				}
			} catch(Exception e) {
				log.error("Could not net user authentication in security context");
			}
		} 
		
		filterChain.doFilter(request, response);
	}
	
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		log.info("bearerToken : "+bearerToken);
		
		if(!StringUtils.isEmpty(request) && bearerToken.startsWith("Bearer")) {
			log.info("Bearer exist");
			return bearerToken.substring("Bearer".length());
		}
		return null;
	}

}
