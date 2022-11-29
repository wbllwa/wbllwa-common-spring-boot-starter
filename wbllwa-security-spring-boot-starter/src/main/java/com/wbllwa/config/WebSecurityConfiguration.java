package com.wbllwa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author libw
 * @since 2022/11/29 17:00
 */
@Configuration
public class WebSecurityConfiguration
{
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring()
                .antMatchers("/resources");
    }

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
     {
        http.authorizeRequests()
                .antMatchers("/public")
                .permitAll()
                .anyRequest()
                .hasRole("USER")
                .and()
                .formLogin()
                .permitAll();

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

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user = User.builder()
                .username("user")
                .password("123456")
                .roles("user")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("123456")
                .roles("admin")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
