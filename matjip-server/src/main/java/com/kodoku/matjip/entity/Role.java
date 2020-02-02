package com.kodoku.matjip.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Setter
@Getter
@ToString(exclude = "user")
@EqualsAndHashCode(callSuper = false, exclude = "user")
@Entity
@Table(schema = "matjip")
public class Role implements PagingEntity {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  @Column(updatable = false, nullable = false, length = 100)
  private UUID idx;

  @Column(nullable = false, length = 10)
  private String role;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private User user;

  @Column(updatable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant registrationDate;

  @Column(
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Instant updateDate;
}
