package by.gaponenko.restaurant.controller.listener;

import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;

import jakarta.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AppContextListener implements ServletRequestListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public void contextInitialized(ServletContextEvent sce) {

        try {
            connectionPool.initPoolData();

        } catch (ClassNotFoundException e) {
            log.error("Error while trying to init connection pool", e);

        } catch (SQLException e) {
            log.error("Error with connection while working with database", e);
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            connectionPool.dispose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

