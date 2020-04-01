package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;

import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.DatasourceHolder;
import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.DatasourceParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(EconageBatisSpringBootConst.PROPERTY_PREFIX)
public class EconageBatisSetting {
    private boolean enabled;

    /*
    * 监控配置
    * */
    private Duration slowSqlDuration;

    private boolean statementMonitorEnabled;
    /*
    * 主题配置
    * */
    private EconageBatisUnitSetting primary;

    /*
    * 外部配置
    * */
    private List<EconageBatisUnitSetting> externals;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    EconageBatisUnitSetting getPrimary() {
        return primary;
    }

    public void setPrimary(EconageBatisUnitSetting primary) {
        this.primary = primary;
    }

    public void setExternals(List<EconageBatisUnitSetting> externals) {
        this.externals = externals;
    }

    public EconageBatisUnitSetting selectExternalSetting(String externalSettingName){
        if(StringUtils.isEmpty(externalSettingName)|| CollectionUtils.isEmpty(externals)){
            return null;
        }
        for(EconageBatisUnitSetting externalSetting : externals){
            if(externalSettingName.equals(externalSetting.getName())){
                return externalSetting;
            }
        }
        return null;
    }

    public Duration getSlowSqlDuration() {
        return slowSqlDuration;
    }

    public void setSlowSqlDuration(Duration slowSqlDuration) {
        this.slowSqlDuration = slowSqlDuration;
    }

    public boolean isStatementMonitorEnabled() {
        return statementMonitorEnabled;
    }

    public void setStatementMonitorEnabled(boolean statementMonitorEnabled) {
        this.statementMonitorEnabled = statementMonitorEnabled;
    }




    DatasourceHolder createDatasourceHolder() throws Exception {
        Assert.notNull(primary,"no primary setting!");
        Assert.notNull(primary.getDataSourceProps(),"no primary datasource setting!");

        DataSource primaryMaster = DatasourceParser.buildDataSource(primary);

        Map<String,DataSource> externalDataSourceMap = Collections.emptyMap();
        if(!CollectionUtils.isEmpty(externals)){
            externalDataSourceMap = new HashMap<>();
            int idx=1;
            for(EconageBatisUnitSetting externalSetting : externals){
                String dataSourceName = externalSetting.getName();
                if(StringUtils.isEmpty(dataSourceName)){
                    dataSourceName = "external"+idx;
                }
                DataSource externalDataSource = DatasourceParser.buildDataSource(externalSetting);
                externalDataSourceMap.put(dataSourceName,externalDataSource);
                idx++;
            }
        }

        return new DatasourceHolder(primaryMaster,externalDataSourceMap);
    }
}
