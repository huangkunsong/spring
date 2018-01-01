package com.hks.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/**
 * 负责建立springsecurity web 应用的filter
 * @author Huangkunsong
 */
@Order(2)
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {

}
