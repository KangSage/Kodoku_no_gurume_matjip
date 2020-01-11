package com.kodoku.matjip.config.listener;

import com.kodoku.matjip.util.ModernTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
@Component
public class RealHttpSessionListener implements HttpSessionListener {
  @Override
  public void sessionCreated(HttpSessionEvent se) {
    HttpSession session = se.getSession();
    String korZonedTime =
        ModernTimeUtil.getZonedTime(
            session.getCreationTime(), "yyyy-MM-dd HH:mm:ss z", "Asia/Seoul");
    log.debug("Real Session Creation event start: {}", korZonedTime);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    HttpSession session = se.getSession();
    String korZonedTime =
        ModernTimeUtil.getZonedTime(
            session.getCreationTime(), "yyyy-MM-dd HH:mm:ss z", "Asia/Seoul");
    log.debug("Real Session destroyed event start: {}", korZonedTime);
  }
}
