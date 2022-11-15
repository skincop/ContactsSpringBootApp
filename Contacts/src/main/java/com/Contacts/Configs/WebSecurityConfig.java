package com.Contacts.Configs;

import com.Contacts.Configs.Utils.RestAuthenticationEntryPoint;
import com.Contacts.service.CustomLogoutHandler;
import com.Contacts.Configs.jwt.JwtFilter;
import com.Contacts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private CustomLogoutHandler logoutHandler;
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;



    @Bean
    public PasswordEncoder getPasswordEncode() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/registration","/auth","/login").permitAll()
                .antMatchers("/user/**").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessUrl("/login").permitAll()
                        .permitAll()
                .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

    }
}