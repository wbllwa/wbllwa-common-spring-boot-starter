package com.wbllwa.filter;

import cn.hutool.extra.spring.SpringUtil;
import com.wbllwa.contants.Contant;
import com.wbllwa.service.RedisService;
import com.wbllwa.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
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

        String token = jwtUtil.getToken(request);
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId;
        try
        {
            userId = jwtUtil.getUserIdFromToken(token);
        }
        catch (ExpiredJwtException e)
        {
            throw new BadCredentialsException("token已过期，请重新登陆", e);
        }
        catch (Exception e)
        {
            throw new BadCredentialsException("token获取失败", e);
        }

        String username = redisService.get(Contant.LOGIN_KEY + userId);
        if (StringUtils.isEmpty(username))
        {
            throw new BadCredentialsException("用户未登录，请重新登陆");
        }

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
