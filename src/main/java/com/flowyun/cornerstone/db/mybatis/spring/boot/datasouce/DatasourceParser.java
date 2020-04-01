package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.EconageBatisUnitSetting;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Properties;

public class DatasourceParser {

    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", "jdbc-url");
        aliases.addAliases("username", "user");
    }


    public static DataSource buildDataSource(EconageBatisUnitSetting unitSetting) throws Exception {

        Properties dataSourceProps = resolveDataSourceProps(unitSetting);
        Class<? extends DataSource> type = unitSetting.getDatasourceType();
        if(type==null){
            type = HikariDataSource.class;
        }

        if(type == HikariDataSource.class){
            HikariConfig config = new HikariConfig(dataSourceProps);
            if(StringUtils.isEmpty(config.getPoolName())){
                config.setPoolName(unitSetting.getName());
            }
            return new HikariDataSource(config);
        }

        DataSource result = BeanUtils.instantiateClass(type);
        ConfigurationPropertySource source = new MapConfigurationPropertySource(dataSourceProps);
        Binder binder = new Binder(source.withAliases(aliases));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
        return result;
    }

    private static Properties resolveDataSourceProps(EconageBatisUnitSetting unitSetting) throws Exception {
        Properties dataSourceProps = new Properties(unitSetting.getDataSourceProps());

        /*
        * 解密
        * */
        if(!StringUtils.isEmpty(unitSetting.getPwdNameInProps() )
         &&!StringUtils.isEmpty(unitSetting.getPwdPublicKey())  ){
            String pwdValue = dataSourceProps.getProperty(unitSetting.getPwdNameInProps());
            String decryptPwd = ConfigTools.decrypt(unitSetting.getPwdPublicKey(),pwdValue);
            dataSourceProps.setProperty(unitSetting.getPwdNameInProps(),decryptPwd);
        }

        return dataSourceProps;
    }

}
