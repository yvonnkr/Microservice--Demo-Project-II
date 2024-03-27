package com.yvolabs.jobservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Not needed as we now use feign client
     */
    //@LoadBalanced
    //@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
