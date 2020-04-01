package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

public class DatasourceHolder {

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

    public DataSource selectExternalDataSource(String externalSettingName){
        if(StringUtils.isEmpty(externalSettingName)|| CollectionUtils.isEmpty(externals)){
            return null;
        }
        return externals.get(externalSettingName);
    }
}
