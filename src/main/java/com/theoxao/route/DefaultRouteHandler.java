package com.theoxao.route;

import com.theoxao.entity.BaseRouteData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

/**
 * @author theo
 * @date 2019/5/24
 */
public class DefaultRouteHandler implements RouteHandler {

    private final RequestMappingHandlerMapping mappingHandler;

    public DefaultRouteHandler(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.mappingHandler = requestMappingHandlerMapping;
    }


    @Override
    public void removeRoute(String body) {

    }

    @Override
    public void addRoute(BaseRouteData routeData) {
        RequestMappingInfo.Builder mappingInfo = RequestMappingInfo.paths(routeData.getPath())
                .methods(RequestMethod.valueOf(routeData.getMethod()))
                .produces(MediaType.APPLICATION_JSON_VALUE);


    }

}
