package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public class DatasourceHolder implements DisposableBean {

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

    @Override
    public void destroy() throws Exception {
        closeDataSource(primaryMaster);
        if(externals!=null){
            for(DataSource exDs : externals.values()){
                closeDataSource(exDs);
            }
        }

    }

    private void closeDataSource(DataSource ds) throws IOException {
        if(ds instanceof Closeable){
            ((Closeable)ds).close();
        }
    }


}
