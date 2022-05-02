package com.csuft.ucenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有都放行
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().csrf().disable();
    }

    @Bean
    public static BCryptPasswordEncoder CreatBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}