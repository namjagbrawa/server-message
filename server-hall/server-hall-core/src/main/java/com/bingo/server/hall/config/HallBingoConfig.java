package com.bingo.server.hall.config;

import com.bingo.framework.config.ApplicationConfig;
import com.bingo.framework.config.ProtocolConfig;
import com.bingo.framework.config.RegistryConfig;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.game.provider.ScoreProvider;
import com.bingo.server.hall.provider.*;
import com.bingo.server.hall.service.*;
import com.bingo.server.user.provider.*;
import com.bingo.server.utils.BingoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HallBingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(HallBingoConfig.class);

    @Value("${application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeper;

    @Value("${hall.port}")
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
        BingoConfig.exportRequest(BagProvider.class, BagService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(BroadcastProvider.class, BroadcastService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(LoginProvider.class, LoginService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(MessageProvider.class, MessageService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(OrderProvider.class, OrderService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(UserProvider.class, UserService.class, applicationContext, application, registry, protocol);
        BingoConfig.exportRequest(VersionProvider.class, VersionService.class, applicationContext, application, registry, protocol);
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

    // Game
    @Bean
    public ScoreProvider getScoreProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(ScoreProvider.class, application, registry);
    }

    @Bean
    public DdzUserProvider getDdzUserProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(DdzUserProvider.class, application, registry);
    }
}
