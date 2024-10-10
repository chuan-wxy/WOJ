package org.chuan.woj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 明确设置允许的源地址
        corsConfiguration.addAllowedOriginPattern("https://woj.asia");
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许所有请求头
        corsConfiguration.addAllowedHeader("*");

        // 允许所有请求方法（如GET, POST, PUT, DELETE等）
        corsConfiguration.addAllowedMethod("*");

        // 允许发送凭证（如Cookies）
        corsConfiguration.setAllowCredentials(true);

        // 对所有URL路径应用此CORS配置
        source.registerCorsConfiguration("/**", corsConfiguration);

        // 创建并返回CorsFilter
        return new CorsFilter(source);
    }
}