package com.saas.multitenantspring.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class SchemaResolver implements CurrentTenantIdentifierResolver {

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    static {
        currentTenant.set("public");
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.set(null);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
