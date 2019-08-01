package com.kodoku.matjip.config;

import com.kodoku.matjip.service.serviceImpl.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PUBLIC/*, onConstructor_ = @Autowired*/)
@ToString
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailService;

    @Resource
    private PasswordEncoder passwordEncoder;

// Lombok @RequiredArgsConstructor로 대체
//    @Autowired
//    public CustomAuthProvider(UserDetailServiceImpl userDetailService) {
//        this.userDetailService = userDetailService;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails user;
        Collection<? extends GrantedAuthority> authorities;
        try {
            user = userDetailService.loadUserByUsername(username);
            if (!passwordEncoder.matches(authToken.getCredentials().toString().replaceAll("&amp;", "&"), user.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            } else if (!user.isAccountNonLocked()) {
                throw new LockedException("이메일 미인증");
            }
            authorities = user.getAuthorities();
        } catch (UsernameNotFoundException e) {
            log.error("", e);
            throw new UsernameNotFoundException(e.getMessage());
        } catch (BadCredentialsException e) {
            log.error("", e);
            throw new BadCredentialsException(e.getMessage());
        } catch (LockedException e) {
            log.error("", e);
            throw new LockedException(e.getMessage());
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


