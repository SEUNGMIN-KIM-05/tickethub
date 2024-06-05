package com.tickethub.backend.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickethub.backend.user.dto.CustomUserDetails;
import com.tickethub.backend.user.vo.RequestLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        RequestLogin requestLogin = new RequestLogin();

        try {

            log.info("============== Authentication ================");

            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            requestLogin = objectMapper.readValue(messageBody, RequestLogin.class);

            log.info("username={}", requestLogin.getUsername());
            log.info("password={}", requestLogin.getPassword());


        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = requestLogin.getUsername();
        String password = requestLogin.getPassword();

        log.info("username={}", requestLogin.getUsername());
        log.info("password={}", requestLogin.getPassword());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken); // authenticationManager가 검증

    }

    // 로그인 성공시 실행하는 메소드 (여기서 JWT 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("LOGIN SUCCESS");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername(); // username
        log.info("username={}", username);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority(); // role

        String token = jwtUtil.createJwt(username, role, 6 * 60 * 60 * 1000L);

        // 쿠키 생성
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1시간

        log.info("cookie.token={}" , cookie.getAttribute("token"));

        response.addCookie(cookie);
        response.addHeader("Authorization", "Bearer " + token);
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("LOGIN FAIL");
        response.setStatus(401);
    }
}