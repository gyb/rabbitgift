package com.irelint.ttt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ttt")
public class MyDubboProperties {
    /**
     * dubbo服务版本号,默认值为空
     */
    private String version = "";
    /**
     * rpc服务调用超时时间
     */
    private Integer timeout = 3000;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
