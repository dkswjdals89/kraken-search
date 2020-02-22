package com.dkswjdals89.krakensearch.config.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.sendRedirect("/exception/accessdenied");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
    }
}
