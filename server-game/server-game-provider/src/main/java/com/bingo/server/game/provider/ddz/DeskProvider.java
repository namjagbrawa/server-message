package com.bingo.server.game.provider.ddz;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.DdzDesk;

/**
 * Created by ZhangGe on 2017/7/26.
 */
public interface DeskProvider {

    void initDesk(URL url, DdzDesk ddzDesk);

    void callLord(URL url, long userId, int callNumber);

    void play(URL url, long userId, byte[] cards);
}
