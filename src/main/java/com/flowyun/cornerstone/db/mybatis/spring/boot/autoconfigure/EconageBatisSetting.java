package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;

import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting.EconageBatisExternalSetting;
import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.setting.EconageBatisPrimarySetting;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

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
    private EconageBatisPrimarySetting primary;

    /*
    * 外部配置
    * */
    private List<EconageBatisExternalSetting> externalSettings;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public EconageBatisPrimarySetting getPrimary() {
        return primary;
    }

    public void setPrimary(EconageBatisPrimarySetting primary) {
        this.primary = primary;
    }

    public List<EconageBatisExternalSetting> getExternalSettings() {
        return externalSettings;
    }

    public void setExternalSettings(List<EconageBatisExternalSetting> externalSettings) {
        this.externalSettings = externalSettings;
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
}
