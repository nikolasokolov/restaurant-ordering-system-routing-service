package com.graduation.authentication.config;

public final class UrlConstants {
    // TODO: Change the urls for each role
    public static final String[] SUPER_ADMIN_URLS = {
            "/api/admin/**"
    };

    public static final String[] ROLE_ADMIN_URLS = {
            "/api/admin/**"
    };

    public static final String[] ROLE_USER_URLS = {
            "/api/reports/**"
    };

    public static final String[] PERMIT_ALL_URLS = {
            "/api/account",
            "/api/authenticate"
    };
}

