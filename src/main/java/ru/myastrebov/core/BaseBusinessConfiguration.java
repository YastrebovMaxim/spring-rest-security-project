package ru.myastrebov.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maxim
 */
@Configuration
@ComponentScan(basePackages = {"ru.myastrebov.core.services", "ru.myastrebov.core.repositories.inMemory"})
public class BaseBusinessConfiguration {
}
