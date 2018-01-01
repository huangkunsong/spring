package com.hks.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;

/**
 * @author Huangkunsong
 */
@Configuration
@ImportResource("classpath:soap-config.xml")
@ComponentScan(basePackages = {"com.hks"}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
})
public class RootConfig {
}
