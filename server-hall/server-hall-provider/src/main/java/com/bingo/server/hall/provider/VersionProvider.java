package com.bingo.server.hall.provider;


import com.bingo.server.database.model.Version;

/**
 * Created by ZhangGe on 2017/7/24.
 */
public interface VersionProvider {

    Integer getLastestVersionNumber();

    String getLastestUrl();

    Version getLastestVersion();
}
