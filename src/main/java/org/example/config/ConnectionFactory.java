package org.example.config;

import org.example.utitilty.PropertiesFIleReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

final public class ConnectionFactory {
    Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
    public static ConnectionFactory database = new ConnectionFactory();
    private DbProperty dbProperty;
    private Connection connection;

    private ConnectionFactory() {
        try {
            initDbProperty();
            this.connection = DriverManager.
                    getConnection(this.dbProperty.getDbURl(), this.dbProperty.getDbUserName(), this.dbProperty.getDbPassword());
            logger.log(Level.INFO, "Connection Successful");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection Error : Check Db URL,Username, or Password!");
            e.printStackTrace();
        }
    }

    private void initDbProperty() {
        Properties properties= PropertiesFIleReader.getProperties();
        this.dbProperty = new DbProperty.dbPropertyBuilder()
                .dbURl(properties.getProperty("sql.db.url"))
                .dbUserName(properties.getProperty("sql.db.username"))
                .dbPassword(properties.getProperty("sql.db.password"))
                .build();
        logger.log(Level.INFO,"DB property initiated with "+this.dbProperty);
    }

    public Connection getConnectionInstance() {
        if (this.connection == null)
            throw new RuntimeException("Connection Instance not initiated: Please check");
        return this.connection;
    }

}

