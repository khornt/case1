package com.horntvedt.case1.integrasjon.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class ApplicationConfiguration {

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        //noinspection unchecked
        ServletRegistrationBean servlet = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/rs/*");
        servlet.setName("CamelServlet");
        servlet.setAsyncSupported(true);
        return servlet;
    }


}
