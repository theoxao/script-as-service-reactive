package com.theoxao.common;

import com.theoxao.service.ServiceHolder;
import com.theoxao.service.ServicesHolder;
import com.theoxao.wrap.ApplicationCallWrap;
import com.theoxao.wrap.ContinuationWrap;
import io.ktor.application.ApplicationCall;
import kotlin.Unit;

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
