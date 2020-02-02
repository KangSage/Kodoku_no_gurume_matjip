package com.kodoku.matjip.controller;

import com.kodoku.matjip.config.enums.ResponseBodyResult;
import com.kodoku.matjip.entity.User;
import com.kodoku.matjip.service.UserRegisterService;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(
    value = "/user",
    method = {RequestMethod.GET, RequestMethod.POST})
public class UserRegisterController {

  // @Autowired를 사용하지 않고 생성자로 주입받아 사용한다.
  private final UserRegisterService userRegisterService;

  public UserRegisterController(UserRegisterService userRegisterService) {
    this.userRegisterService = userRegisterService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<Map<String, Object>> userRegister(@RequestBody User user) {
    log.debug("user: {}", user);
    Map<String, Object> body = new HashMap<>();
    try {
      userRegisterService.userRegister(user);
      body.put("result", ResponseBodyResult.SUCCESS.getResult());
      return ResponseEntity.ok().body(body);
    } catch (TransactionSystemException e) {
      body.put("body", e);
      log.error("Cause : ", e.getCause().getCause());
    } catch (Exception e) {
      log.error("Error: ", e);
    }
    return ResponseEntity.badRequest().body(body);
  }
}
