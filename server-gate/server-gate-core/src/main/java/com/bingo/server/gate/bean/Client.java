package com.bingo.server.gate.bean;

import com.bingo.framework.remoting.Channel;

/**
 * Created by ZhangGe on 2017/7/12.
 */
public class Client {

    private long userId;
    private long topic;
    private Channel channel;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTopic() {
        return topic;
    }

    public void setTopic(long topic) {
        this.topic = topic;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
