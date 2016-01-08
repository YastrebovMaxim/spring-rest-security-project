package ru.myastrebov.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.myastrebov.core.security.SecurityConfig;

/**
 * @author Maxim
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ru.myastrebov.rest.controllers"})
@Import({SecurityConfig.class})
public class WebConfiguration {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
