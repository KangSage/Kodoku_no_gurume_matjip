package com.kodoku.matjip.config;

import com.kodoku.matjip.entity.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SecurityMember extends User {

    private UUID idx;
//    private String active;
//    private boolean accountNonLocked;

    /**
     * 로그인 정보를 세션에 담기 위한 객체를 생성한다.
     * @param user email과 password가 확인된 사용자 객체
     */
    public SecurityMember(com.kodoku.matjip.entity.User user) {
        super(user.getEmail(), user.getPassword(), makeGrantedAutority(user.getRoles()));
        this.idx = user.getIdx();
    }


    /**
     * user의 권한들을 모두 세션에 담는다.
     * @param roles DB에서 조회한 권한들
     * @return grantedAuthorities
     */
    private static List<GrantedAuthority> makeGrantedAutority(List<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));
        return grantedAuthorities;
    }
}
