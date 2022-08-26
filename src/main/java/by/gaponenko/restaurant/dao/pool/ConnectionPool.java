package by.gaponenko.restaurant.dao.pool;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import by.gaponenko.restaurant.dao.DaoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

// код контроля работы с connection-ами
public class ConnectionPool {
    private static final int DEFAULT_POOLSIZE = 10;
    //private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String driverName;
    private final String url;
    private final String userName;
    private final String password;
    private BlockingQueue<Connection> connectionsQueue;
    private BlockingQueue<Connection> givenAwayQueue;
    private int poolSize;

    private static final ConnectionPool instance;

    static {
        try {
            instance = new ConnectionPool();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionPool() throws SQLException, ClassNotFoundException {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.userName = dbResourceManager.getValue(DBParameter.DB_USERNAME);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(
                    dbResourceManager.getValue(DBParameter.DB_POOLSIZE));
        } catch (NumberFormatException e) {
            this.poolSize = DEFAULT_POOLSIZE;
        }
        initPoolData();
    }

    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            return instance;
        }
    }

    public void initPoolData() throws ClassNotFoundException, SQLException {

        Class.forName(driverName);
        connectionsQueue = new ArrayBlockingQueue<>(poolSize, true);
        givenAwayQueue = new ArrayBlockingQueue<>(poolSize, true);

        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url, userName, password);
            PooledConnection pooledConnection = new PooledConnection(connection);
            connectionsQueue.add(pooledConnection);

        }
    }

    public void closeConnection(final Connection connection,
                                final Statement statement,
                                final ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            //log.error("Error while closing resultSet object", e);
        }
        try {
            statement.close();
        } catch (SQLException e) {
            //log.error("Error while closing statement object", e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            //log.error("Error while closing connection object", e);
        }
    }

    public void closeConnectionQueue(final BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
            ((PooledConnection) connection).reallyClose();
        }

    }

    public void clearConnectionQueue() {
        try {
            closeConnectionQueue(connectionsQueue);
            closeConnectionQueue(givenAwayQueue);
        } catch (SQLException e) {
            //log.error("Error closing connection queue", e);
        }
    }

    public void dispose() {
        clearConnectionQueue();
    }

    public Connection takeConnection() throws InterruptedException, SQLException, ClassNotFoundException {
        Connection connection;
        connection = connectionsQueue.take();
        givenAwayQueue.add(connection);
        return connection;
    }

    private class PooledConnection implements Connection, AutoCloseable {
        private final Connection connection;

        PooledConnection(final Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(final String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(final String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(final String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void setAutoCommit(final boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void close() throws SQLException {

            if (connection.isClosed()) {
                throw new SQLException("Attempting to close already closed connection");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayQueue.remove(this)) {
                throw new SQLException("Error while trying to remove connection from given away connection pool");
            }
            if (!connectionsQueue.offer(this)) {
                throw new SQLException("Error while trying to add connection to pool");
            }
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setReadOnly(final boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setCatalog(final String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public void setTransactionIsolation(final int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(final int resultSetType,
                                         final int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(final String sql,
                                                  final int resultSetType,
                                                  final int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(final String sql,
                                             final int resultSetType,
                                             final int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public void setHoldability(final int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(final String name) throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public void rollback(final Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(final int resultSetType,
                                         final int resultSetConcurrency,
                                         final int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(final String sql,
                                                  final int resultSetType,
                                                  final int resultSetConcurrency,
                                                  final int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(final String sql,
                                             final int resultSetType,
                                             final int resultSetConcurrency,
                                             final int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(final String sql,
                                                  final int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(final String sql,
                                                  final int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(final String sql,
                                                  final String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(final int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(final String name,
                                  final String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public String getClientInfo(final String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public void setClientInfo(final Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public Array createArrayOf(final String typeName,
                                   final Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(final String typeName,
                                   final Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void setSchema(final String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public void abort(final Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(final Executor executor,
                                      final int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(final Class<T> face) throws SQLException {
            return connection.unwrap(face);
        }

        @Override
        public boolean isWrapperFor(final Class<?> face) throws SQLException {
            return connection.isWrapperFor(face);
        }
    }
}