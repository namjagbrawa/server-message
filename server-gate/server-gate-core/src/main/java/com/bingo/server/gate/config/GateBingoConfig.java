package com.bingo.server.gate.config;

import com.bingo.framework.config.ApplicationConfig;
import com.bingo.framework.config.ProtocolConfig;
import com.bingo.framework.config.RegistryConfig;
import com.bingo.server.game.provider.GameInfoProvider;
import com.bingo.server.game.provider.StartGameProvider;
import com.bingo.server.game.provider.ddz.DeskProvider;
import com.bingo.server.game.provider.landlord.LandlordProvider;
import com.bingo.server.gate.client.ClientRequest;
import com.bingo.server.gate.client.ClientService;
import com.bingo.server.gate.provider.GateDeskProvider;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.gate.service.GateDeskService;
import com.bingo.server.gate.service.NotificationService;
import com.bingo.server.hall.provider.*;
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
public class GateBingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(GateBingoConfig.class);

    @Value("${application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeper;

    @Value("${client.port}")
    private int clientPort;

    @Value("${gate.port}")
    private int gatePort;

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
        // Client Protocol
        ProtocolConfig client = BingoConfig.getClientProtocol(clientPort);
        BingoConfig.exportRequest(ClientRequest.class,  ClientService.class, applicationContext, application, registry, client);
        // Bingo Protocol Request
        ProtocolConfig gate = BingoConfig.getBingoProtocol(gatePort);
        BingoConfig.exportRequest(GateDeskProvider.class,  GateDeskService.class, applicationContext, application, registry, gate);
        // Bingo Protocol Async
        BingoConfig.exportAsync(NotificationProvider.class, NotificationService.class, applicationContext, application, registry, gate);
        return true;
    }

    @Bean
    public LoginProvider getLoginProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(LoginProvider.class, application, registry);
    }
    @Bean
    public VersionProvider getVersionProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(VersionProvider.class, application, registry);
    }
    @Bean
    public UserProvider getUserProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(UserProvider.class, application, registry);
    }
    @Bean
    public BroadcastProvider getBroadcastProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(BroadcastProvider.class, application, registry);
    }
    @Bean
    public MessageProvider getMessageProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(MessageProvider.class, application, registry);
    }
    @Bean
    public FriendProvider getFriendProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(FriendProvider.class, application, registry);
    }
    @Bean
    public GameInfoProvider getGameInfoProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(GameInfoProvider.class, application, registry);
    }
    @Bean
    public StartGameProvider getStartGameProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(StartGameProvider.class, application, registry);
    }

    // Game
    @Bean
    public DeskProvider getDeskProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(DeskProvider.class, application, registry);
    }
    @Bean
    public LandlordProvider getLandlordProvider(ApplicationConfig application, RegistryConfig registry) {
        return BingoConfig.reference(LandlordProvider.class, application, registry);
    }
}
