package com.theoxao.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

/**
 * @author theo
 * @date 2019/5/24
 */
@Getter
@Setter
public class BaseRouteData {

    String id = new ObjectId().toHexString();
    String path;
    String method = "GET";
    String script;

    public BaseRouteData() {
    }

    public BaseRouteData(String path, String script) {
        this.path = path;
        this.script = script;
    }
}
