package by.gaponenko.restaurant.controller.listener;

import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;

import jakarta.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public void contextInitialized(ServletContextEvent sce) {

        try {
            connectionPool.initConnectionPool();

        } catch (ClassNotFoundException | SQLException e) {
            log.error("Error while trying to init connection pool", e);
            throw new RuntimeException("Error while trying to init connection pool", e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        connectionPool.dispose();
    }
}