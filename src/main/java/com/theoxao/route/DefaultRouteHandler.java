package com.theoxao.route;

import com.theoxao.common.ParamWrap;
import com.theoxao.entity.BaseRouteData;
import groovy.lang.GroovyShell;
import groovy.lang.MetaClass;
import groovy.lang.Script;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author theo
 * @date 2019/5/24
 */
@Component
public class DefaultRouteHandler implements RouteHandler {

    private final RequestMappingHandlerMapping mappingHandler;
    private GroovyShell shell = new GroovyShell();

    public DefaultRouteHandler(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.mappingHandler = requestMappingHandlerMapping;
    }


    @Override
    public void removeRoute(String body) {

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Script script = new GroovyShell().parse("import reactor.core.publisher.Mono\n" +
                "import com.theoxao.common.ParamWrap\n" +
                "\n" +
                "static Mono<String> service(ParamWrap paramWrap) {\n" +
                "    return Mono.just(paramWrap.toString().reverse())\n" +
                "}\n" +
                "\n");
//        Object bean = script.invokeMethod("bean", null);
//        Method method = bean.getClass().getMethod("service", String.class);
//        System.out.println(method.invoke(bean, "that my name"));

        MetaClass metaClass = script.getMetaClass();
        Class theClass = metaClass.getTheClass();
        Method service = theClass.getMethod("service", String.class);
        System.out.println(service.invoke(metaClass, "that my  name too"));
    }

    @Override
    public void addRoute(BaseRouteData routeData) {
        RequestMappingInfo mappingInfo = RequestMappingInfo.paths(routeData.getPath())
                .methods(RequestMethod.valueOf(routeData.getMethod()))
                .produces(MediaType.APPLICATION_JSON_VALUE).build();
        try {
            Method method;
            Object bean = null;
            Script script = shell.parse(routeData.getScript());
            //consider metaClass as service class
            MetaClass metaClass = script.getMetaClass();
            method = metaClass.getTheClass().getMethod("service", ParamWrap.class);
            if (method != null) {
                bean = metaClass;
            } else {
                //already defined a class in script
                //TODO auto generate bean method to get class instance
                //should i define the method name as "bean"  or define by class name
                //if its dynamically defined by class name, maybe i lately i could get this bean again only by its name
                bean = script.invokeMethod("bean", null);
                method = bean.getClass().getMethod("service", ParamWrap.class);
            }
            mappingHandler.registerMapping(mappingInfo, bean, method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
