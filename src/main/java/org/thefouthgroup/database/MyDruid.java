package org.thefouthgroup.database;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDruid {
    private static DataSource dataSource = null;
    private static final Logger LOGGER = Logger.getLogger("DatabaseLogger");

    static {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        if (dataSource != null) {
            LOGGER.info("Datasource Created");
        }else {
            LOGGER.info("Datasource Failed");
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
    public static Logger getLogger(){
        return LOGGER;
    }
}
