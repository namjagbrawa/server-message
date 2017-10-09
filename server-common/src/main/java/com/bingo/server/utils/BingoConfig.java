package com.bingo.server.utils;

import com.bingo.framework.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/12.
 */
public class BingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(BingoConfig.class);

    public static final String CLIENT = "client";

    public static final int TASK = 0;
    public static final int SERVER = 0;
    public static final int REUQEST = 0;
    public static final int ONEWAY = 0;

    public static ApplicationConfig getApplication(String applicationName) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(applicationName);
        return application;
    }

    public static RegistryConfig getRegistry(String address) {
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress(address);
        return registry;
    }

    public static ProtocolConfig getBingoProtocol(int port) {
        return getProtocol(false, port);
    }

    public static ProtocolConfig getClientProtocol(int port) {
        return getProtocol(true, port);
    }

    public static ProtocolConfig getProtocol(boolean isClient, int port) {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("bingo");
        if (isClient) {
            protocol.setCodec(CLIENT);
        }
        protocol.setPort(port);
        return protocol;
    }

    public static <T> T exportAsync(Class<T> provider, Class impl, ApplicationContext applicationContext, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        return export(provider, impl, true, true, applicationContext, application, registry, protocol);
    }

    public static <T> T exportRequest(Class<T> provider, Class impl, ApplicationContext applicationContext, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        return export(provider, impl, false, false, applicationContext, application, registry, protocol);
    }

    public static <T> T export(Class<T> provider, Class impl, boolean direct, boolean async, ApplicationContext applicationContext, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        return export(provider, (T) applicationContext.getBean(impl), false, false, application, registry, protocol);
    }

    public static <T> T exportAsync(Class<T> provider, T impl, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        return export(provider, impl, true, true, application, registry, protocol);
    }

    public static <T> T exportRequest(Class<T> provider, T impl, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        return export(provider, impl, false, false, application, registry, protocol);
    }

    public static <T> T export(Class<T> provider, T impl, boolean direct, boolean async, ApplicationConfig application, RegistryConfig registry, ProtocolConfig protocol) {
        ServiceConfig<T> service = new ServiceConfig<T>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(provider);
        service.setRef(impl);
        if (direct) {
            service.setLoadbalance("direct");
        }
        if (CLIENT.equals(protocol.getCodec())) {
            service.setParameters(new HashMap<String, String>() {{
                put("onconnect", "onConnect");
                put("ondisconnect", "onDisconnect");
                put("invocation.channel", "true");
            }});
        }
        if (async) {
            Method[] methods = provider.getMethods();
            if (methods == null || methods.length <= 0) {
                logger.warn("EXPORT METHOD IS NOT EXIST, PROVIDER : ", provider);
            } else {
                List<MethodConfig> methodConfigList = new ArrayList<>();
                for (Method method : methods) {
                    String name = method.getName();
                    MethodConfig methodConfig = new MethodConfig();
                    methodConfig.setName(name);
                    methodConfig.setAsync(true);
                    methodConfig.setReturn(false);
                    methodConfig.setSent(true);
                    methodConfigList.add(methodConfig);
                }
                service.setMethods(methodConfigList);
            }
            service.setTimeout(1000);
        } else {
            service.setTimeout(5000);
        }
        service.export();
        return service.getService();
    }

    public static <T> T reference(Class<T> provider, ApplicationConfig application, RegistryConfig registry) {
        ReferenceConfig<T> reference = new ReferenceConfig<T>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(provider);
        reference.setCheck(false);
        return reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
    }

    public static <T> void setBean(Class<T> provider, T ref, ApplicationContext applicationContext) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(provider);
        beanFactory.registerBeanDefinition("userService", beanDefinitionBuilder.getBeanDefinition());
    }
}
