package com.bingo.server.game.manager;

import com.bingo.server.game.bean.User;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.utils.AbstractMemory;
import com.bingo.server.utils.Check;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Key Is Desk Id, Get it From Game
 * Created by ZhangGe on 2017/7/19.
 */
public class DeskManager extends AbstractMemory<Long, Desk> {

    private static class DeskManagerHolder {
        private static final DeskManager instance = new DeskManager();
    }

    public static DeskManager get() {
        return DeskManager.DeskManagerHolder.instance;
    }

    private final Map<Long/*userId*/, Long/*deskId*/> userMap = new ConcurrentHashMap<>();

    public Desk getByUser(long userId) {
        Long deskId = userMap.get(userId);
        return getValue(deskId, true, true);
    }

    public Desk getByDesk(long deskId) {
        return getValue(deskId, true, true);
    }

    public void put(Desk desk) {
        put(desk.getDeskId(), desk, true, true);
    }

    public void put(Desk desk, User user) {
        Desk byDesk = getByDesk(desk.getDeskId());
        if (Check.isNull(byDesk)) {
            put(desk);
        }
        userMap.put(user.getUserId(), desk.getDeskId());
    }

    public void put(Desk desk, List<User> users) {
        Desk byDesk = getByDesk(desk.getDeskId());
        if (Check.isNull(byDesk)) {
            put(desk);
        }
        for (User user : users) {
            userMap.put(user.getUserId(), desk.getDeskId());
        }
    }

    public void remove(Desk desk) {
        remove(desk.getDeskId(), true);
        for (User user : desk.getUsers()) {
            userMap.remove(user.getUserId());
        }
    }

}
