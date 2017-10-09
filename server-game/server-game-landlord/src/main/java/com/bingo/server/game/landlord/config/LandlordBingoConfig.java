package com.bingo.server.game.landlord.config;

import com.bingo.framework.config.ApplicationConfig;
import com.bingo.framework.config.ProtocolConfig;
import com.bingo.framework.config.RegistryConfig;
import com.bingo.server.game.landlord.service.NavigateService;
import com.bingo.server.game.provider.landlord.LandlordProvider;
import com.bingo.server.gate.provider.GateDeskProvider;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.user.provider.*;
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
public class LandlordBingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(LandlordBingoConfig.class);

    @Value("${application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeper;

    @Value("${landlord.port}")
    private int landlordPort;

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
        ProtocolConfig gate = BingoConfig.getBingoProtocol(landlordPort);
        BingoConfig.exportRequest(LandlordProvider.class, NavigateService.class, applicationContext, application, registry, gate);
        return true;
    }

    // User
    @Bean
    public CuBagProvider getCuBagProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuBagProvider.class, application, registry);
    }
    @Bean
    public CuFriendProvider getCuFriendProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuFriendProvider.class, application, registry);
    }
    @Bean
    public CuOnlineUserProvider getCuOnlineUserProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuOnlineUserProvider.class, application, registry);
    }
    @Bean
    public CuRoomTypeProvider getCuRoomTypeProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuRoomTypeProvider.class, application, registry);
    }
    @Bean
    public CuUserProvider getCuUserProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuUserProvider.class, application, registry);
    }
    @Bean
    public CuWalletProvider getCuWalletProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(CuWalletProvider.class, application, registry);
    }
    @Bean
    public ItemProvider getItemProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(ItemProvider.class, application, registry);
    }

    // Gate
    @Bean
    public NotificationProvider getNotificationProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(NotificationProvider.class, application, registry);
    }
    @Bean
    public GateDeskProvider getGateDeskProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(GateDeskProvider.class, application, registry);
    }

}
