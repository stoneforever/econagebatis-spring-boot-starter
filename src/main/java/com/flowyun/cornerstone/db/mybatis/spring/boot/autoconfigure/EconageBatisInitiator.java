package com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure;

import com.flowyun.cornerstone.db.mybatis.adaptation.MybatisConfiguration;
import com.flowyun.cornerstone.db.mybatis.adaptation.MybatisGlobalAssistant;
import com.flowyun.cornerstone.db.mybatis.enums.DBType;
import com.flowyun.cornerstone.db.mybatis.handlers.*;
import com.flowyun.cornerstone.db.mybatis.mapper.DynaBeanMapper;
import com.flowyun.cornerstone.db.mybatis.monitor.StatementMonitor;
import com.flowyun.cornerstone.db.mybatis.uid.dbincrementer.DB2KeyGenerator;
import com.flowyun.cornerstone.db.mybatis.uid.dbincrementer.H2KeyGenerator;
import com.flowyun.cornerstone.db.mybatis.uid.dbincrementer.OracleKeyGenerator;
import com.flowyun.cornerstone.db.mybatis.uid.dbincrementer.PostgreKeyGenerator;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;
import java.util.Locale;

/*
* 固定若干配置项,解析boot配置文件信息
* */
public class EconageBatisInitiator {

    private boolean parsed;
    private MybatisConfiguration configuration;
    private MybatisGlobalAssistant globalAssistant;

    public static EconageBatisInitiator create(){
        return new EconageBatisInitiator();
    }

    public EconageBatisInitiator(){
        this.configuration = new MybatisConfiguration();
        this.globalAssistant = new MybatisGlobalAssistant(configuration);
        this.configuration.setGlobalAssistant(globalAssistant);
    }


    /*--------------若干固定配置*/
    private void fixedConfig(DataSource dataSource){

        TransactionFactory transactionFactory = new SpringManagedTransactionFactory();
        Environment environment = new Environment("spring-environment", transactionFactory, dataSource);
        configuration.setEnvironment(environment);
        //禁止懒加载，服务器api化后，意义不大
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(false);
        //禁止多结果
        configuration.setMultipleResultSetsEnabled(false);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setMapUnderscoreToCamelCase(true);
        //禁用二级缓存
        configuration.setCacheEnabled(false);
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setDefaultEnumTypeHandler(DefaultEnumTypeHandler.class);
        configuration.getTypeHandlerRegistry().register(Locale.class,JdbcType.VARCHAR,new LocaleTypeHandler());
        configuration.setProxyFactory(new CglibProxyFactory());
    }

    private void refreshDbType(){
        if(globalAssistant.getDbType().equals(DBType.ORACLE)){
            //oracle特殊设置
            configuration.setJdbcTypeForNull(JdbcType.NULL);
            globalAssistant.setKeyGenerator(new OracleKeyGenerator());
        }else if(globalAssistant.getDbType().equals(DBType.H2)){
            globalAssistant.setKeyGenerator(new H2KeyGenerator());
        }else if(globalAssistant.getDbType().equals(DBType.POSTGRE)){
            globalAssistant.setKeyGenerator(new PostgreKeyGenerator());
        }else if(globalAssistant.getDbType().equals(DBType.DB2)){
            globalAssistant.setKeyGenerator(new DB2KeyGenerator());
        }
    }

    /*
     * java8,3类local时间设置
     * */
    private void enableLocalDateTimeCompatible(){
        configuration.getTypeHandlerRegistry().register(CompatibleLocalDateTimeTypeHandler.class);
        configuration.getTypeHandlerRegistry().register(CompatibleLocalDateTypeHandler.class);
        configuration.getTypeHandlerRegistry().register(CompatibleLocalTimeTypeHandler.class);
    }


    public EconageBatisInitiator parse(
            EconageBatisUnitSetting setting,
            DataSource dataSource,
            StatementMonitor monitor
    ){
        if (parsed) {
            throw new BuilderException("Each ConfigBuilder can only be used once.");
        }
        parsed = true;
        fixedConfig(dataSource);

        if(globalAssistant.getDbType()==null&&setting.getDbType()!=null){
            globalAssistant.setDbType(setting.getDbType());
        }
        refreshDbType();
        if(setting.isLocalDateTimeCompatible()){
            enableLocalDateTimeCompatible();
        }
        if(setting.isDynaBeanMapperEnabled()){
            configuration.addMapper(DynaBeanMapper.class);
        }
        if(monitor!=null){
            globalAssistant.setStatementMonitor(monitor);
        }
        return this;
    }

    public MybatisConfiguration getConfiguration() {
        return configuration;
    }

    public MybatisGlobalAssistant getGlobalAssistant() {
        return globalAssistant;
    }

    public SqlSessionTemplate build(){
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
