package com.kodoku.matjip.service;

import com.kodoku.matjip.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserRegisterService {
    User userRegister(User user) throws Exception;
}
