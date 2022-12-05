package com.wbllwa.config;

import com.wbllwa.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类
 * @author libw
 * @since 2022/11/29 17:00
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration

{
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring()
            .antMatchers(
                "/v3/api-docs",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/swagger-ui.html**",
                "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter()
    {
        return new JwtTokenAuthenticationFilter();
    }

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
     {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/user/login", "/user/register")
            .permitAll()
            .anyRequest()
            .authenticated();
        http.addFilterBefore(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
     }

    /**
     * 设置密码加密方式
     * @return
     */
     @Bean
    public PasswordEncoder passwordEncoder()
     {
         return new BCryptPasswordEncoder();
     }
}
