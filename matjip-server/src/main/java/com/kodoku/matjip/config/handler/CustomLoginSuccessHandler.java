package com.kodoku.matjip.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kodoku.matjip.config.enums.ResponseBodyResult;
import com.kodoku.matjip.entity.enums.RoleType;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    ObjectNode objNode = new ObjectMapper().createObjectNode();
    objNode.put("result", ResponseBodyResult.SUCCESS.getResult());

    for (GrantedAuthority auth : authentication.getAuthorities()) {
      if (RoleType.ROLE_USER.isEquals(auth.getAuthority())) {
        objNode.put("redirectUrl", "/html/user/list.html");
      } else if (RoleType.ROLE_ADMIN.isEquals(auth.getAuthority())) {
        objNode.put("redirectUrl", "/html/admin/list.html");
      }
    }

    try (PrintWriter out = response.getWriter()) {
      out.print(objNode);
      out.flush();
    } catch (IOException e) {
      log.error("", e);
    }
  }
}
