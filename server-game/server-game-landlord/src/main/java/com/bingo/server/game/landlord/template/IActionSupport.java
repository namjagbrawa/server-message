package com.bingo.server.game.landlord.template;


import com.bingo.server.msg.ClientMessage;
import com.bingo.server.msg.ServerMessage;

/**
 * 请求执行接口
 */
public interface IActionSupport {
	ServerMessage.SC execute(ClientMessage.CS cs, long userId);
}