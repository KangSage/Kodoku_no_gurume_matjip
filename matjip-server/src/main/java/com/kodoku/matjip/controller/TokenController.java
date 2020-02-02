package com.kodoku.matjip.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TokenController {

  @GetMapping("/csrf-token")
  public ResponseEntity<?> getCsrfToken(HttpServletRequest request) {
    CsrfToken token = new CookieCsrfTokenRepository().loadToken(request);
    log.debug("token: {}", token.getToken());
    return ResponseEntity.ok().body(token);
  }

  @GetMapping("/json-web-token")
  public ResponseEntity<?> getJsonWebToken(HttpServletRequest request) {
    // TODO: JWT 발행로직 적용
    return ResponseEntity.ok().body("");
  }
}
