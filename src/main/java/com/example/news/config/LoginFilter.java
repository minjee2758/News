package com.example.news.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
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
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)){
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("loginUser") == null){
                throw new NotActiveException("로그인 해주세요");
            }
        }
        FilterChain chain = filterChain;
        chain.doFilter(servletRequest, servletResponse);
        log.info("로그인 인증 성공");

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
