package com.practice.management;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementApplication.class)
public class ManagementApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(ManagementApplicationTests.class);

    @Autowired
    static DataSource dataSource;

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        LOG.info("==========init project===========");
        LOG.info("=========get dataSource==========={}", dataSource);
        //读取sql脚本
        try {
            ClassPathResource recordsSys = new ClassPathResource("sql/init.sql");
            DataSourceInitializer dsi = new DataSourceInitializer();
            dsi.setDataSource(dataSource);
            dsi.setDatabasePopulator(new ResourceDatabasePopulator(true, true, "utf-8", recordsSys));
            dsi.setEnabled(true);
            dsi.afterPropertiesSet();
            LOG.info("============init sql success============");
        }
        catch (Exception e){
            LOG.error("init sql error = {}", e.getMessage());
        }
    }

    @Test
    public void contextLoad(){

    }
}

