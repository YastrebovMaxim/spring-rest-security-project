package ru.myastrebov.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maxim
 */
@Configuration
@ComponentScan(basePackages = {"ru.myastrebov.core.services"})
public class BaseBusinessConfiguration {
}
