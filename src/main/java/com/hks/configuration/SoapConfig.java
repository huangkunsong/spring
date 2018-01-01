package com.hks.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapConfig {
    /**
     * Spring soap 生成另一个服务端口的SOAP服务
     */
    /*@Bean
    public SimpleJaxWsServiceExporter buildSimpleJaxWsServiceExporter(){
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
        exporter.setBaseAddress("http://localhost:8080/services");
        return exporter;
    }*/
}
