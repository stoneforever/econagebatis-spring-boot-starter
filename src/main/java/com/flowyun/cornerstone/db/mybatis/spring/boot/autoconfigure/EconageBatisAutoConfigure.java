package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;


import com.flowyun.cornerstone.db.mybatis.mapper.BasicMapper;
import com.flowyun.cornerstone.db.mybatis.mapper.DynaBeanMapper;
import com.flowyun.cornerstone.db.mybatis.monitor.StatementMonitor;
import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.DatasourceHolder;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;

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
    @ConditionalOnProperty(prefix = EconageBatisSpringBootConst.PROPERTY_PREFIX,value = "statement-monitor-enabled",havingValue = "true")
    public StatementMonitor mybatisStatementMonitor(EconageBatisSetting econageBatisSetting){
        Duration slowSqlDuration = econageBatisSetting.getSlowSqlDuration();
        if(slowSqlDuration==null){
            slowSqlDuration = Duration.ofSeconds(10);
        }
        logger.info("Setting up StatementMonitor.");
        return new StatementMonitor(slowSqlDuration);
    }

    @Bean
    @ConditionalOnMissingBean
    public DatasourceHolder econageBatisDatasourceHolder(EconageBatisSetting setting){
        logger.info("Setting up default DatasourceHolder.");
        return setting.createDatasourceHolder();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(DatasourceHolder datasourceHolder) {
        logger.info("Setting up primary datasource.");
        return new DataSourceTransactionManager(datasourceHolder.getPrimaryMaster());
    }
    /*
     * 将默认事务管理器，设置为平台的默认事务管理器
     * */
    @Bean
    public TransactionManagementConfigurer createTransactionManagementConfigurer(
            DataSourceTransactionManager dataSourceTransactionManager
    ){
        logger.info("Setting up primary datasource.");
        return () -> dataSourceTransactionManager;
    }

    /**
     * This will just scan the same base package as Spring Boot does. If you want more power, you can explicitly use
     * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed mappers working correctly, out-of-the-box,
     * similar to using Spring Data JPA repositories.
     */
    public static class AutoConfiguredMapperScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        private BeanFactory beanFactory;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            if (!AutoConfigurationPackages.has(this.beanFactory)) {
                logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
                return;
            }

            logger.debug("Searching for mappers annotated with @Mapper Or BasicMapper Type");

            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                packages.forEach(pkg -> logger.debug("Using auto-configuration base package '{}'", pkg));
            }

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
            builder.addPropertyValue("processPropertyPlaceHolders", true);
            builder.addPropertyValue("annotationClass", Mapper.class);
            builder.addPropertyValue("markerInterface", BasicMapper.class);
            builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));
            registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

    }

    /**
     * If mapper registering configuration or mapper scanning configuration not present, this configuration allow to scan
     * mappers based on the same component-scanning path as Spring Boot itself.
     */
    @org.springframework.context.annotation.Configuration
    @Import(AutoConfiguredMapperScannerRegistrar.class)
    @ConditionalOnMissingBean({ MapperFactoryBean.class, MapperScannerConfigurer.class })
    public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            logger.debug(
                    "Not found configuration for registering mapper bean using @MapperScan, MapperFactoryBean and MapperScannerConfigurer.");
        }

    }
}
