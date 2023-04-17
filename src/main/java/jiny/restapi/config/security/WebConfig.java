package jiny.restapi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**") //서버
                .allowedOrigins("http://localhost:8081"); //프론트 서버
    }
}
