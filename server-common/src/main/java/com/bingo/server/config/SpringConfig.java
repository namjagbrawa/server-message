package com.bingo.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhangGe on 2017/5/23.
 */
@Configuration
public class SpringConfig {

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

}
