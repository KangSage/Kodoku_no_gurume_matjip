package com.kodoku.matjip.repository;

import com.kodoku.matjip.entity.User;
import javax.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByEmail(String email);
}
