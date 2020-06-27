package com.lxd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-24 16:55
 * @Feature: 过滤白名单
 */
@Configuration
@ConfigurationProperties(prefix = "leyou.filter")
//@RefreshScope
public class FilterProperties {

//    @Value("${leyou.filter.allowPaths}")
//    private String allowPaths;
//
//    public String getAllowPaths() {
//        return allowPaths;
//    }
//
//    public void setAllowPaths(String allowPaths) {
//        this.allowPaths = allowPaths;
//    }
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
