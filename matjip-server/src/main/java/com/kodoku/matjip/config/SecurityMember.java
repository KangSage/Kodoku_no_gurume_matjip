package com.kodoku.matjip.config;

import com.kodoku.matjip.entity.Role;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SecurityMember extends User {

  /**
   *
   */
  private static final long serialVersionUID = -351884844165510144L;
  private UUID idx;

  /**
   * 로그인 정보를 세션에 담기 위한 객체를 생성한다.
   *
   * @param user email과 password가 확인된 사용자 객체
   */
  public SecurityMember(com.kodoku.matjip.entity.User user) {
    super(user.getEmail(), user.getPassword(), makeGrantedAuthority(user.getRoles()));
    this.idx = user.getIdx();
  }

  /**
   * user의 권한들을 모두 세션에 담는다.
   *
   * @param roles DB에서 조회한 권한들
   * @return grantedAuthorities
   */
  private static synchronized Set<GrantedAuthority> makeGrantedAuthority(Set<Role> roles) {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));
    return grantedAuthorities;
  }
}
