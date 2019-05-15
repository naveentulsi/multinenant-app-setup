package com.saas.multitenantspring.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class MultiTenantConfig extends DatasourceConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static final String SELECT_CONTEXT = "select set_config('context.tenant' ,? , false)";

    public MultiTenantConfig() {
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection(null);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Connection getConnection(String s) throws SQLException {

        if(StringUtils.isEmpty(s)){
            s = "public";
        }
        Connection connection = super.getConnection();
        if (!StringUtils.isEmpty(s)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTEXT);
                preparedStatement.setString(1, s);
                preparedStatement.execute();
            } catch (SQLException sqe) {
                throw new HibernateException("Unable to create connection.");
            }
        }
        return connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnection(null);
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return MultiTenantConnectionProvider.class.equals(aClass) || AbstractMultiTenantConnectionProvider.class.isAssignableFrom(aClass);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        if(this.isUnwrappableAs(aClass))
            return (T) this;
        else
            throw new UnknownUnwrapTypeException(aClass);
    }
}
