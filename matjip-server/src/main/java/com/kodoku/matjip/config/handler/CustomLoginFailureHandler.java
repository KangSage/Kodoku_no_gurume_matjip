package com.kodoku.matjip.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kodoku.matjip.config.enums.ResponseBodyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    ObjectNode objNode = new ObjectMapper().createObjectNode();
    objNode.put("message", exception.getMessage());
    objNode.put("result", ResponseBodyResult.FAILURE.getResult());
    try (PrintWriter out = response.getWriter()) {
      out.print(objNode);
      out.flush();
    } catch (IOException e) {
      log.error("", e);
    }
  }
}
