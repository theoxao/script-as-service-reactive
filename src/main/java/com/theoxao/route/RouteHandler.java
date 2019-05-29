package com.theoxao.route;

import com.theoxao.entity.BaseRouteData;
import org.springframework.stereotype.Service;

/**
 * @author theo
 * @date 2019/5/24
 */
public interface RouteHandler {
    void removeRoute(String body);

    void addRoute(BaseRouteData routeData);
}
