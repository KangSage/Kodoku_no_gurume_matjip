package com.kodoku.matjip.entity;

import com.kodoku.matjip.entity.enums.SocialType;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = "matjip")
public class User implements PagingEntity {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  @Column(updatable = false, nullable = false, length = 100)
  private UUID idx;

  @Column(updatable = false, nullable = false, unique = true, length = 20)
  @Email(message = "${errors.invalid_email}")
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 20)
  private String firstName;

  @Column(length = 20)
  private String lastName;

  @Column(length = 100)
  private String principal;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Role> roles = new HashSet<>();

  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @Column(updatable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant registrationDate;

  @Column(
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Instant updateDate;

  public void addRole(Role role) {
    role.setUser(this);
    this.roles.add(role);
  }
}
