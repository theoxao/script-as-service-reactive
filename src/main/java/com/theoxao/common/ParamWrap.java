package com.theoxao.common;

import com.theoxao.service.ServiceHolder;

/**
 * @author theo
 * @date 2019/5/21
 */
public class ParamWrap {
    public final ServiceHolder serviceHolder;

    public ParamWrap(ServiceHolder servicesHolder) {
        this.serviceHolder = servicesHolder;
    }
}
