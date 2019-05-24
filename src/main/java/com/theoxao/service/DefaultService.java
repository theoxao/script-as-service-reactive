package com.theoxao.service;

import com.theoxao.common.CommonResult;
import com.theoxao.common.ParamWrap;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.Setter;
import org.springframework.util.Assert;

/**
 * @author theo
 * @date 2019/5/24
 */
public class DefaultService {

    private String script;

    private GroovyShell shell = new GroovyShell();

    public DefaultService(String script) {
        this.script = script;
    }

    //TODO inject request and response
    public CommonResult service(ParamWrap paramWrap) {
        Assert.notNull(script, "script should not be null");
        Script parse = shell.parse(script);
        return (CommonResult) parse.invokeMethod("service", paramWrap);
    }
}
