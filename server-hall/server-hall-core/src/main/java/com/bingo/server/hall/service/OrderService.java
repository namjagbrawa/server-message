package com.bingo.server.hall.service;

import com.bingo.server.hall.provider.OrderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class OrderService implements OrderProvider{
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
}
