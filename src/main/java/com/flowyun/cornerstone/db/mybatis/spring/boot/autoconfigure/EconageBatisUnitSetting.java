package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;

import com.flowyun.cornerstone.db.mybatis.enums.DBType;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class EconageBatisUnitSetting {

    //配置名称
    private String name;
    //是否启用DynaBeanMapper
    private boolean dynaBeanMapperEnabled;
    //数据库类型，支持oracle、mysql、ms sql server等常见的数据库
    private DBType dbType;
    //从3.5.1版本开始，使用jdbc4.2特性处理3类本地时间，但是部分连接池如druid不支持，此处提供兼容写法
    private boolean localDateTimeCompatible;
    //密码解密用公钥
    private String pwdPublicKey;
    //需要操作的数据库属性
    private String pwdNameInProps;
    //连接池类型，推荐使用HikariCP
    private Class<? extends DataSource> datasourceType = HikariDataSource.class;

    private Properties dataSourceProps;

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

    public String getPwdPublicKey() {
        return pwdPublicKey;
    }

    public void setPwdPublicKey(String pwdPublicKey) {
        this.pwdPublicKey = pwdPublicKey;
    }

    public Class<? extends DataSource> getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(Class<? extends DataSource> datasourceType) {
        this.datasourceType = datasourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getDataSourceProps() {
        return dataSourceProps;
    }

    public void setDataSourceProps(Properties dataSourceProps) {
        this.dataSourceProps = dataSourceProps;
    }

    public String getPwdNameInProps() {
        return pwdNameInProps;
    }

    public void setPwdNameInProps(String pwdNameInProps) {
        this.pwdNameInProps = pwdNameInProps;
    }

}
