package com.bingo.server.game.provider;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.game.provider.bean.enums.UserStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/22.
 */
public interface DdzUserProvider {

    DdzUser addDdzUser(long userId, long deskId, String siteUrl, UserStatus userStatus);

    void addServerUrl(URL url, List<Long> userIds);

    void addServerUrl(URL url, long deskId);

    void update(DdzUser ddzUser);

    boolean allReady(long deskId);

    List<DdzUser> getUsers(List<Long> userIds);

    Map<Long, DdzUser> getUserMap(List<Long> userIds);

    DdzUser getById(long userId);

    List<DdzUser> getByDeskId(long deskId);
}
