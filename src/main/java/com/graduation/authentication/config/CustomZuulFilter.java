package com.graduation.authentication.config;

import com.graduation.authentication.security.SecurityUtils;
import com.graduation.authentication.service.UserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomZuulFilter extends ZuulFilter {
    private static final String FILTER_TYPE = "pre";
    private static final int FILTER_ORDER = 1;

    private final UserService userService;

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if (!SecurityUtils.isAuthenticated()) {
            return null;
        }

        var requestContext = RequestContext.getCurrentContext();
        try {
            var user = userService.getUser();
            requestContext.addZuulRequestHeader("username", user.getUsername());
        } catch (Exception e) {
            log.error("Error fetching user from token");
            throw new IllegalStateException("Error fetching user from token");
        }
        return null;
    }
}
