package com.seo.config.beetl.fun;

import org.apache.commons.lang3.RandomUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

public class Fun implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        return RandomUtils.nextInt(100, 5000);
    }
}
