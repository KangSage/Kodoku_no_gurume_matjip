package com.kodoku.matjip.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kodoku.matjip.config.enums.ResponseBodyResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // MediaType을 UTF8까지 해주면 별도로 해줄 필요가 없다.
        // response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        ObjectNode objNode = new ObjectMapper().createObjectNode();
        objNode.put("result", ResponseBodyResults.SUCCESS.getResult());
        try (PrintWriter out = response.getWriter()) {
            out.print(objNode);
            out.flush();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
