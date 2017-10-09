package com.bingo.server.game.service;

import com.bingo.server.game.bean.Desk;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/21.
 */
@Service
public class ChatService {

    /**
     * 发送消息到所有用户
     * @param desk
     * @param userId
     * @param chairId
     */
    public void chat(Desk desk, long userId, int chairId) {

    }

    /**
     * 发送消息到用户
     * @param desk
     * @param userId
     * @param chairId
     * @param toUserId
     */
    public void chat(Desk desk, long userId, int chairId, long toUserId) {

    }
}
