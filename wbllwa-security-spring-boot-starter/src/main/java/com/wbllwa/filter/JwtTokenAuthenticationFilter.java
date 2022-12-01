package com.wbllwa.filter;

import com.wbllwa.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt token登陆认证过滤器
 * @author libw
 * @since 2022/12/1 15:24
 */
@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException,
            IOException
    {
        try
        {
            String header = request.getHeader(JwtUtil.HEADER_KEY);
            String token = header.substring(JwtUtil.BEARER.length());
            Claims claims = jwtUtil.parseToken(token);
        }
        catch (Exception e)
        {
            log.error("登陆失败", e);
        }

        filterChain.doFilter(request, response);
    }
}
