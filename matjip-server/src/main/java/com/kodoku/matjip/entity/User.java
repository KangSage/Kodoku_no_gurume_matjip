package com.kodoku.matjip.entity;

import com.kodoku.matjip.entity.enums.SocialType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = "kodoku", name = "user")
public class User implements Serializable {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  @Column(name = "idx", updatable = false, nullable = false, length = 100)
  private UUID idx;

  @Column(name = "email", updatable = false, nullable = false, unique = true, length = 20)
  @Email(message = "{errors.invalid_email}")
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name", nullable = false, length = 20)
  private String firstName;

  @Column(name = "last_name", length = 20)
  private String lastName;

  @Column(name = "principal")
  private String principal;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_idx")
  private List<Role> roles = new ArrayList<>();

  @Column(name = "social_type", length = 20)
  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @Column(name = "registration_date", updatable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant registrationDate;

  @Column(name = "update_date",
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Instant updateDate;
}
