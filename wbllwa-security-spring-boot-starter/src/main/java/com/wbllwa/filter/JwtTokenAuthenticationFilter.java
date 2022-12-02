package com.wbllwa.filter;

import cn.hutool.extra.spring.SpringUtil;
import com.wbllwa.service.RedisService;
import com.wbllwa.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException,
            IOException
    {
        JwtUtil jwtUtil = SpringUtil.getBean(JwtUtil.class);
        RedisService redisService = SpringUtil.getBean(RedisService.class);

        String header = request.getHeader(JwtUtil.HEADER_KEY);
        if (StringUtils.isEmpty(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(JwtUtil.BEARER.length());
        String userId = jwtUtil.getUserIdFromToken(token);

        String username = redisService.get("login:" + userId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
