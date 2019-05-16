package com.saas.multitenantspring.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaResolver implements CurrentTenantIdentifierResolver {

    private static Log logger = LogFactory.getLog(SchemaResolver.class);

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    static {
        currentTenant.set("public");
    }

    public static void setCurrentTenant(String tenant) {
        logger.info("Setting Current Tenant :: "+ tenant);
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        logger.info("Get Current Tenant :: "+ currentTenant.get());
        return currentTenant.get();
    }

    public static void clear(){
        logger.info("Get Current Tenant :: "+ currentTenant.get());
        logger.info("Clear Tenant");
        currentTenant.set(null);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        logger.info("Resolving current tenant");
        return getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        logger.info("validateExistingCurrentSessions");
        return false;
    }
}
