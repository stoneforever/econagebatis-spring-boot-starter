package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting.EconageBatisBasicSetting;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import javax.sql.DataSource;
import java.util.Map;

public class DatasourceParser {

    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", "jdbc-url");
        aliases.addAliases("username", "user");
    }

    public static DataSource parseDatasource(
            EconageBatisBasicSetting batisBasicSetting,
            Map<String,String> dataSourceProps
    ){
        return buildDataSource(batisBasicSetting.getDatasourceType(),dataSourceProps);
    }

    private static DataSource buildDataSource(
            Class<? extends DataSource> type ,
            Map<String,String> dataSourceProps
    ){
        if(type==null){
            type = HikariDataSource.class;
        }
        DataSource result = BeanUtils.instantiateClass(type);
        ConfigurationPropertySource source = new MapConfigurationPropertySource(dataSourceProps);
        Binder binder = new Binder(source.withAliases(aliases));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
        return result;
    }

}
