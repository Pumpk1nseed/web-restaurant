package by.gaponenko.restaurant.controller.listener;

import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;

import jakarta.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AppContextListener implements ServletRequestListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void contextInitialized(ServletContextEvent sce) {

        try {
            ConnectionPool.getInstance().initPoolData();

        } catch (ClassNotFoundException e) {
            log.error("Error while trying to init connection pool", e);

        } catch (SQLException e) {
            log.error("Error with connection while working with database", e);
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().dispose();
        } catch (Exception e) {
            log.error("Error with dispose connection", e);
            throw new RuntimeException(e);
        }
    }
}

