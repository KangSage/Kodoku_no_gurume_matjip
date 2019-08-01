package com.kodoku.matjip.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kodoku.matjip.entity.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ObjectNode objNode = new ObjectMapper().createObjectNode();
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			log.debug("auth.getAuthority(): {}", auth.getAuthority());
			objNode.put("forwardUrl", RoleType.valueOf(auth.getAuthority()).getForwardUrl());
			response.getWriter().print(objNode);
			break;
		}
	}
}