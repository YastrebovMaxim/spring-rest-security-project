package ru.myastrebov.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author Maxim
 */
@Configuration
@ComponentScan
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("user").password("password").roles(UserRole.ADMIN.getName(), UserRole.USER.getName());
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests()
                    .anyRequest().permitAll()
//                .antMatchers(HttpMethod.POST, "/api/version/products/**").hasRole(UserRole.ADMIN.getName())
//                .antMatchers(HttpMethod.PUT, "/api/version/products/**").hasRole(UserRole.ADMIN.getName())
//                .antMatchers(HttpMethod.DELETE, "/api/version/products/**").hasRole(UserRole.ADMIN.getName())
//                .antMatchers(HttpMethod.GET, "/api/version/products/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/version/orders/**").hasRole(UserRole.ADMIN.getName())
                .and()
            /*.exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()*/
            .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}
