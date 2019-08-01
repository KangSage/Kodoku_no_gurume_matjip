package com.kodoku.matjip.config.listener;

import com.kodoku.matjip.util.ModernTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SecurityLogoutListener implements ApplicationListener<SessionDestroyedEvent> {
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        String korZonedTime = ModernTimeUtil.getZonedTime(event.getTimestamp(), "yyyy-MM-dd HH:mm:ss z","Asia/Seoul");
        log.debug("Security session destroyed event start: {}", korZonedTime);
        List<SecurityContext> securityContextList = event.getSecurityContexts();
        UserDetails userDetails;
        for (SecurityContext securityContext : securityContextList) {
            userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
            log.debug("Security session destroyed username: {}", userDetails.getUsername());
            log.debug("Security session destroyed user role: {}", userDetails.getAuthorities());
        }
    }
}

