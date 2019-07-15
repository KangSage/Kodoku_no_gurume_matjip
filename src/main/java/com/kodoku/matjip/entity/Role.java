package com.kodoku.matjip.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Setter @Getter @ToString @EqualsAndHashCode
@Entity @Table(schema = "kodoku", name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue @Type(type = "uuid-char")
    @Column(name = "idx", updatable = false, nullable = false, length = 100)
    private UUID idx;

    @Column(name = "role", nullable = false, length = 10)
    private String role;

    @Transient
    private UUID userIdx;

    @Column(name = "registration_date", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant registrationDate;

    @Column(name = "update_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updateDate;

}
