package com.bingo.server.hall.service;

import com.bingo.server.database.dao.VersionMapper;
import com.bingo.server.database.model.Version;
import com.bingo.server.database.model.VersionExample;
import com.bingo.server.exception.ServerException;
import com.bingo.server.hall.provider.VersionProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/24.
 */
@Service
public class VersionService implements VersionProvider {

    private final Logger logger = LoggerFactory.getLogger(VersionService.class);

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Integer getLastestVersionNumber() {
        List<Version> versions = versionMapper.selectByExample(new VersionExample());
        if (Check.isNull(versions)) {
            return null;
        }
        int number = -1;
        for (Version version : versions) {
            if (number <= version.getVersion()) {
                number = version.getVersion();
            }
        }
        if (number > -1) {
            return number;
        } else {
            return null;
        }
    }

    @Override
    public String getLastestUrl() {
        List<Version> versions = versionMapper.selectByExample(new VersionExample());
        if (Check.isNull(versions)) {
            return null;
        }
        Version lastest = versions.get(0);
        for (int i = 1; i < versions.size(); i++) {
            if (lastest.getVersion() <= versions.get(i).getVersion()) {
                lastest = versions.get(i);
            }
        }
        return lastest.getUrl();
    }

    @Override
    public Version getLastestVersion() {
        List<Version> versions = versionMapper.selectByExample(new VersionExample());
        if (Check.isNull(versions)) {
            throw new ServerException(MSG.MsgCode.NO_VERSION_ERROR, "没有版本信息");
        }
        Version lastest = versions.get(0);
        for (int i = 1; i < versions.size(); i++) {
            if (lastest.getVersion() <= versions.get(i).getVersion()) {
                lastest = versions.get(i);
            }
        }
        return lastest;
    }
}
