package com.hks.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * spring mvc配置类
 * @author Huangkunsong
 */
@Configuration
@ComponentScan(basePackages = {"com.hks"}, useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
})
public class MvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置url映射到Controller的配置
     * 取消后缀匹配
     * 默认情况下，Spring MVC执行".*"后缀模式匹配，以便映射到的控制器/person也隐式映射到/person.*。
     * 这是用于基于URL内容协商，例如/person.pdf，/person.xml等等。
     * @return
     */
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
        return requestMappingHandlerMapping;
    }

    /**
     * 特殊的视图解析器,根据客户端请求的类型来返回对应类型的数据
     * 1.看URL扩展名：
     *      .json返回application/json格式的数据
     *      .xml返回application/xml格式的数据
     *      .html返回text/html
     * 2.看请求头中的Accept头部信息
     *
     * @return
     */
    /*@Bean
    public ViewResolver cnViewResolver(ContentNegotiationManager contentNegotiationManager){
        ContentNegotiatingViewResolver cn = new ContentNegotiatingViewResolver();
        cn.setContentNegotiationManager(contentNegotiationManager);
        return cn;
    }*/

    /**
     * 配置一个ContextNegotiationManager(Spring 3.2才有)
     * 作用：
     *      指定默认的内容类型
     *      忽略指定请求头信息
     *      ...
     * @param configurer
     */
    /*@Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }*/

    /**
     * 静态资源转发到默认是Servlet容器中,不使用DispatcherServlet
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
