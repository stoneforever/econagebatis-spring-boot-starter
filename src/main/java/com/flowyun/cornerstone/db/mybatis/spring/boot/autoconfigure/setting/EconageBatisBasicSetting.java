package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting;

import com.flowyun.cornerstone.db.mybatis.enums.DBType;

import javax.sql.DataSource;

public abstract class EconageBatisBasicSetting {

    //是否启用DynaBeanMapper
    private boolean dynaBeanMapperEnabled;
    //数据库类型，支持oracle、mysql、ms sql server等常见的数据库
    private DBType dbType;
    //从3.5.1版本开始，使用jdbc4.2特性处理3类本地时间，但是部分连接池如druid不支持，此处提供兼容写法
    private boolean localDateTimeCompatible;

    private String publicKey;

    private Class<? extends DataSource> datasourceType;

    public boolean isDynaBeanMapperEnabled() {
        return dynaBeanMapperEnabled;
    }

    public void setDynaBeanMapperEnabled(boolean dynaBeanMapperEnabled) {
        this.dynaBeanMapperEnabled = dynaBeanMapperEnabled;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public boolean isLocalDateTimeCompatible() {
        return localDateTimeCompatible;
    }

    public void setLocalDateTimeCompatible(boolean localDateTimeCompatible) {
        this.localDateTimeCompatible = localDateTimeCompatible;
    }

    public abstract String getName();

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Class<? extends DataSource> getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(Class<? extends DataSource> datasourceType) {
        this.datasourceType = datasourceType;
    }
}
