package org.kukuking.database;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDruid {
    private static DataSource dataSource = null;
    private static final Logger LOGGER = Logger.getLogger("DatabaseLogger");

    static {
        try {
            Properties prop = new Properties();
            URL url = MyDruid.class.getResource("/druid.properties");
            if (url != null) {
                prop.load(new FileInputStream(url.getFile()));
            }
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        if (dataSource != null) {
            LOGGER.info("Datasource Created");
        }else {
            LOGGER.info("Datasource Failed");
            System.exit(1);
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
    public static Logger getLogger(){
        return LOGGER;
    }
}
