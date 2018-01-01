package com.hks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * spring mvc配置类
 * @author Huangkunsong
 */
/*@Configuration
@ComponentScan(basePackages = {"com.hks"}, useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
})*/
public class OldMvcConfig extends WebMvcConfigurationSupport {

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

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    /**
     * 设置模板解析器
     * @return
     */
    public SpringResourceTemplateResolver springResourceTemplateResolver(){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }

    /**
     * 设置模板引擎
     * @param resolver
     * @return
     */
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver resolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }

    /**
     * ThymeleafViewResolver页面解析器
     * @param engine
     * @return
     */
    public ViewResolver viewResolver(SpringTemplateEngine engine) {
        ThymeleafViewResolver view = new ThymeleafViewResolver();
        view.setTemplateEngine(engine);
        view.setCharacterEncoding("UTF-8");
        return view;
    }

    @Bean
    public ViewResolver viewResolver2() {
        InternalResourceViewResolver view = new InternalResourceViewResolver();
        view.setPrefix("/WEB-INF/views/");
        view.setSuffix(".html");
        view.setExposeContextBeansAsAttributes(true);
        return view;
    }

    /**
     * 静态资源转发到默认是Servlet容器中,不使用DispatcherServlet
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /*@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
        .addResourceLocations("/css/**");
    }*/
}
