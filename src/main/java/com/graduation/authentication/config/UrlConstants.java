package com.graduation.authentication.config;

public final class UrlConstants {
    public static final String[] ADMIN_URLS = {
            "/api/admin/**"
    };

    public static final String[] PERMIT_ALL_URLS = {
            "/api/account",
            "/api/authenticate"
    };

    public static final String[] USER_ROLE = {
            "/api/reports/**"
    };
}

