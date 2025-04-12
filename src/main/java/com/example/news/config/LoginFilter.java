package com.example.news.config;


import com.example.news.common.CommonResponse;
import com.example.news.exception.CustomException;
import com.example.news.exception.FailCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.NotActiveException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/news/signup", "/news/login"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");
        HttpSession session = httpRequest.getSession(false);

        try {
            if (!isWhiteList(requestURI) && (session == null || session.getAttribute("loginUser") == null)){
                throw new RuntimeException("로그인이 필요합니다");
            }
            FilterChain chain = filterChain;
            chain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            httpResponse.setContentType("application/json;charset=UTF-8");

            CommonResponse<Object> errorResponse = CommonResponse.from(FailCode.LOGIN_REQUIRED, null);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorResponse);

            httpResponse.getWriter().write(json);
        }

    }

    public boolean isWhiteList(String requestURI) {
        for (String s : WHITE_LIST) {
            if (requestURI.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
