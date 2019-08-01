package com.kodoku.matjip.controller;

import com.kodoku.matjip.config.SecurityMember;
import com.kodoku.matjip.entity.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Slf4j
@Controller
public class MainPageController {

    @GetMapping(value= "/")
    public String redirectMainPage(@AuthenticationPrincipal SecurityMember securityMember) {
        String _forwardUrl = "redirect:/login.html";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            Collection<? extends GrantedAuthority> authorities = securityMember.getAuthorities();
            for (GrantedAuthority auth : authorities) {
                log.debug("auth.getAuthority(): {}", auth.getAuthority());
                _forwardUrl = "redirect:" + RoleType.valueOf(auth.getAuthority()).getForwardUrl();
                break;
            }
        }
        return _forwardUrl;
    }
}
