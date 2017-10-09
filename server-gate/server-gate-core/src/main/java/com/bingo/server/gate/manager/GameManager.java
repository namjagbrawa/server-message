package com.bingo.server.gate.manager;

import com.bingo.framework.common.URL;
import com.bingo.server.utils.AbstractMemory;

/**
 * Created by ZhangGe on 2017/7/26.
 */
public class GameManager extends AbstractMemory<Long/*Topic*/, URL> {

    private static class GameManagerHolder {
        private static final GameManager instance = new GameManager();
    }

    public static GameManager get() {
        return GameManager.GameManagerHolder.instance;
    }
}
