package com.wggy.prune.configuration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wggy.prune.interceptor.ApiRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ping
 * @create 2019-05-16 17:32
 **/
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private ApiRequestInterceptor apiRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info(">>>>>>>>>>>>>>>>>>加载拦截器链<<<<<<<<<<<<<<<<<<<<<<");
        registry.addInterceptor(apiRequestInterceptor).addPathPatterns("/api/**").excludePathPatterns("/adminlte/**", "/js/**", "/plugins/**");
    }

    @Bean
    public Gson gson() {
        log.info(">>>>>>>>>>>>>>>>>>加载Gson<<<<<<<<<<<<<<<<<<<<<<");
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Bean
    public HttpMessageConverter<?> codeResponseConverter(Gson gson) {
        log.info(">>>>>>>>>>>>>>>>>>设置Http Gson格式化实例<<<<<<<<<<<<<<<<<<<<<<");
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }


}
