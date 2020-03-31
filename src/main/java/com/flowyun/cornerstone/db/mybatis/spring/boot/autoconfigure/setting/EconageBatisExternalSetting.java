package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting;

import java.util.Map;

/*
* 外部数据源配置信息
* */
public class EconageBatisExternalSetting extends EconageBatisBasicSetting {

    private String name;
    private Map<String,String> dataSource;

    public Map<String, String> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, String> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
