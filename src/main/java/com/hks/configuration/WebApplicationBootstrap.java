package com.hks.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.sun.xml.ws.transport.http.servlet.WSSpringServlet;
import org.springframework.lang.Nullable;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用根配置类
 * @author Huangkunsong
 */
public class WebApplicationBootstrap extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
            RootConfig.class
        };
    }

    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Nullable
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{
            characterEncodingFilter
        };
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        /**
         * 添加阿里druid监控页面
         */
        Map<String, String> initParams = new HashMap<>();
        initParams.put("resetEnable", String.valueOf(true));
        ServletRegistration.Dynamic druidRegistration = servletContext.addServlet("DruidStatView", StatViewServlet.class);
        druidRegistration.addMapping("/druid/*");
        druidRegistration.setInitParameters(initParams);

        /**
         * SOAP使用服务端端口发布配置servlet
         */
        ServletRegistration.Dynamic wsRegistration = servletContext.addServlet("JaxWsServlet", WSSpringServlet.class);
        wsRegistration.addMapping("/soap/*");
    }
}
