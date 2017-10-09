package com.bingo.server.user.config;

import com.bingo.framework.config.ApplicationConfig;
import com.bingo.framework.config.ProtocolConfig;
import com.bingo.framework.config.RegistryConfig;
import com.bingo.server.user.provider.*;
import com.bingo.server.user.service.*;
import com.bingo.server.utils.BingoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@Configuration
public class UserBingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(UserBingoConfig.class);

    @Value("${application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeper;

    @Value("${user.port}")
    private int port;

    @Bean
    public ApplicationConfig getApplicationConfig() {
        return BingoConfig.getApplication(applicationName);
    }

    @Bean
    public RegistryConfig getRegistryConfig() {
        return BingoConfig.getRegistry(zookeeper);
    }

    @Bean
    public boolean getService(ApplicationContext applicationContext, ApplicationConfig application, RegistryConfig registry) {
        ProtocolConfig protocol = BingoConfig.getBingoProtocol(port);
        BingoConfig.exportRequest(CuBagProvider.class, CuBagService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(CuFriendProvider.class, CuFriendService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(CuOnlineUserProvider.class, CuOnlineUserService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(CuRoomTypeProvider.class, CuRoomTypeService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(CuUserProvider.class, CuUserService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(CuWalletProvider.class, CuWalletService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(ItemProvider.class, ItemService.class, applicationContext, application, registry, protocol);
        return true;
    }
}
