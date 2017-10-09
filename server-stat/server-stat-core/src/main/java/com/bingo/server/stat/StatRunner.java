package com.bingo.server.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@ComponentScan(basePackages = "com.bingo")
@SpringBootApplication
public class StatRunner {
    private static Logger logger = LoggerFactory.getLogger(StatRunner.class);

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        SpringApplication springApplication = new SpringApplicationBuilder()
                .sources(StatRunner.class)
                .web(false)
                .build();

        ApplicationContext applicationContext = springApplication.run(args);

        logger.info("Start Time : {}", new Date());
        logger.info("Server Process 启动成功 ... ");
        logger.info("Use Time : {}ms", System.currentTimeMillis() - startTime);

        CountDownLatch countDownLatch = applicationContext.getBean(CountDownLatch.class);
        countDownLatch.await();
    }
}
