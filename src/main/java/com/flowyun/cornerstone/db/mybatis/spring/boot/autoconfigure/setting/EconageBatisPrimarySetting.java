package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting;

import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;

import java.util.Map;

/*
* 主要数据库配置
* todo，允许配置主库，从库
* */
public class EconageBatisPrimarySetting extends EconageBatisBasicSetting {

    //默认可用的连接池
    //主数据库连接池信息
    private Map<String,String> dataSourceMaster;
    //todo 从数据库连接池
    //private List<Map<String,String>> dataSourceSlave;

    public Map<String, String> getDataSourceMaster() {
        return dataSourceMaster;
    }

    public void setDataSourceMaster(Map<String, String> dataSourceMaster) {
        this.dataSourceMaster = dataSourceMaster;
    }

    @Override
    public String getName() {
        return EconageBatisSpringBootConst.PRIMARY_SETTING_NAME;
    }
}
