package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;


import com.flowyun.cornerstone.db.mybatis.mapper.DynaBeanMapper;
import com.flowyun.cornerstone.db.mybatis.monitor.StatementMonitor;
import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.DatasourceHolder;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SqlSessionTemplate.class)
@ConditionalOnProperty(prefix = EconageBatisSpringBootConst.PROPERTY_PREFIX,value = "enabled",havingValue = "true")
@EnableConfigurationProperties(EconageBatisSetting.class)
public class EconageBatisAutoConfigure {

    private static final Logger logger = LoggerFactory.getLogger(EconageBatisAutoConfigure.class);

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            EconageBatisSetting econageBatisSetting,
            DatasourceHolder datasourceHolder,
            @Autowired(required = false)
            StatementMonitor statementMonitor
    ){
        logger.info("Setting up Primary SqlSessionTemplate.");
        return EconageBatisInitiator.create()
                .parse(
                        econageBatisSetting.getPrimary(),
                        datasourceHolder.getPrimaryMaster(),
                        statementMonitor
                ).build();
    }

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = EconageBatisSpringBootConst.PROPERTY_PREFIX,value = "primary.dyna-bean-mapper-enabled",havingValue = "true")
    public DynaBeanMapper primaryDynaBeanMapper(SqlSessionTemplate sqlSessionTemplate){
        logger.info("Setting up Primary DynaBeanMapper.");
        return sqlSessionTemplate.getMapper(DynaBeanMapper.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public DatasourceHolder econageBatisDatasourceHolder(EconageBatisSetting setting){
        logger.info("Setting up DatasourceHolder.");
        return DatasourceHolder.create(setting);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = EconageBatisSpringBootConst.PROPERTY_PREFIX,value = "statement-monitor-enabled",havingValue = "true")
    public StatementMonitor mybatisStatementMonitor(EconageBatisSetting econageBatisSetting){
        Duration slowSqlDuration = econageBatisSetting.getSlowSqlDuration();
        if(slowSqlDuration==null){
            slowSqlDuration = Duration.ofSeconds(10);
        }
        logger.info("Setting up StatementMonitor.");
        return new StatementMonitor(slowSqlDuration);
    }


}
