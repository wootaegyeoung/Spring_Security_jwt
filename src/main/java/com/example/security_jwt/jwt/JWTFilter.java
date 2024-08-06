package com.example.security_jwt.jwt;


import com.example.security_jwt.dto.CustomUserDetails;
import com.example.security_jwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    public JWTFilter(JWTUtil jwtUtil, ObjectMapper objectMapper) {

        this.jwtUtil = jwtUtil;
        this.objectMapper=objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];
        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {


            System.out.println("token expired");
            String refreshToken = request.getHeader("refreshToken").split(" ")[1];;


            if (refreshToken != null && jwtUtil.RefreshIsExpired(refreshToken)) {
                //리프레시 토큰ok, 일반 토큰 X
                String newToken = jwtUtil.generateNewToken(refreshToken); // 새로운 Access Token 발급
                response.setStatus(303);
                response.addHeader("Authorization", "Bearer " + newToken);




            } else {
                // Refresh Token이 없거나 유효하지 않으면 401 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Refresh token has expired");
            }
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRoles(token);

        //userEntity를 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}