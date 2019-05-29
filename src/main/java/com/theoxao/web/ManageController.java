package com.theoxao.web;

import com.theoxao.entity.BaseRouteData;
import com.theoxao.route.RouteHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author theo
 * @date 2019/5/29
 */
@RestController
@RequestMapping("/")
public class ManageController {

    private final RouteHandler routeHandler;

    public ManageController(RouteHandler routeHandler) {
        this.routeHandler = routeHandler;
    }

    @RequestMapping("/add")
    public String add(String path, String script) {
        routeHandler.addRoute(new BaseRouteData(path, script));
        return "success";
    }

}
