package com.kodoku.matjip.config.listener;

import com.kodoku.matjip.util.ModernTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionCreationEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginListener implements ApplicationListener<SessionCreationEvent> {
    @Override
    public void onApplicationEvent(SessionCreationEvent event) {
        String korZonedTime = ModernTimeUtil.getZonedTime(event.getTimestamp(), "yyyy-MM-dd HH:mm:ss z","Asia/Seoul");
        log.debug("Session Creation event start: {}", korZonedTime);
        HttpSession session = (HttpSession) event.getSource();
        log.debug("Created session timeout: {}", session.getServletContext().getSessionTimeout());
    }
}
