package com.kodoku.matjip.service.serviceImpl;

import com.kodoku.matjip.entity.Role;
import com.kodoku.matjip.entity.User;
import com.kodoku.matjip.entity.enums.RoleType;
import com.kodoku.matjip.repository.UserRepository;
import com.kodoku.matjip.service.UserRegisterService;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserRegisterServiceImpl(
      UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void userRegister(User user) {
      Role role = new Role();
      role.setRole(RoleType.ROLE_USER.name());
      user.addRole(role);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
  }
}
