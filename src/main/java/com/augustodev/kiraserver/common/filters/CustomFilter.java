package com.augustodev.kiraserver.common.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Log4j2
@Component
@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        System.out.println("Request from: " + request.getRequestURI());

        log.info("Request from: {} ", request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
