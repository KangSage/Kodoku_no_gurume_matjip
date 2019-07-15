package com.kodoku.matjip.controller;

import com.kodoku.matjip.entity.User;
import com.kodoku.matjip.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    // @Autowired를 사용하지 않고 생성자로 주입받아 사용한다.
    private final UserRegisterService userRegisterService;

    public UserController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping(value ="/register")
    public ResponseEntity<Map<String, Object>> userRegister(@RequestBody User user) {
        ResponseEntity<Map<String, Object>> resultEntity;
        Map<String, Object> body = new HashMap<>();
        try {
            body.put("registererdUser", userRegisterService.addRegister(user));
            resultEntity = ResponseEntity.ok().body(body);
        } catch (Exception e) {
            body.put("body", e);
            resultEntity = ResponseEntity.badRequest().body(body);
            log.error("error : ", e);
        }
        return resultEntity;
    }

}
