package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.EconageBatisSetting;
import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting.EconageBatisExternalSetting;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DatasourceHolder {

    public static DatasourceHolder create(EconageBatisSetting setting){
        Assert.notNull(setting.getPrimary(),"no primary setting!");
        Assert.notNull(setting.getPrimary().getDataSourceMaster(),"no primary datasource setting!");


        DataSource primaryMaster = DatasourceParser.parseDatasource(
                setting.getPrimary(),
                setting.getPrimary().getDataSourceMaster()
        );

        Map<String,DataSource> externalDataSourceMap = Collections.emptyMap();
        if(!CollectionUtils.isEmpty(setting.getExternalSettings())){
            externalDataSourceMap = new HashMap<>();
            int idx=1;
            for(EconageBatisExternalSetting externalSetting : setting.getExternalSettings()){
                String dataSourceName = externalSetting.getName();
                if(StringUtils.isEmpty(dataSourceName)){
                    dataSourceName = "external"+idx;
                }
                DataSource externalDataSource = DatasourceParser.parseDatasource(
                        externalSetting,
                        externalSetting.getDataSource()
                );
                externalDataSourceMap.put(dataSourceName,externalDataSource);
                idx++;
            }
        }

        return new DatasourceHolder(primaryMaster,externalDataSourceMap);
    }


    private final DataSource primaryMaster;

    private final Map<String, DataSource> externals;

    public DatasourceHolder(DataSource primaryMaster, Map<String, DataSource> externals) {
        this.primaryMaster = primaryMaster;
        this.externals = externals;
    }

    public DataSource getPrimaryMaster() {
        return primaryMaster;
    }

    public Map<String, DataSource> getExternals() {
        return externals;
    }
}
