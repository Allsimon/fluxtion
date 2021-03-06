/* 
 * Copyright (C) 2018 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.ext.streaming.builder.util;

import com.fluxtion.builder.generation.GenerationContext;
import com.fluxtion.ext.streaming.api.Wrapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import lombok.Data;

/**
 * java bean classes for interaction with velocity
 */
@Data
public class ArraySourceInfo {

    public String type;
    public String typeFqn;
    public String id;
    public int count;
    public String getter;
    public boolean cast;
    public ArrayList sourceInstances;

    public ArraySourceInfo(Class clazz, Method getMethod, boolean cast) {
        this.type = clazz.getSimpleName();
        this.typeFqn = clazz.getCanonicalName();
        this.id = "sourceUpdated_" + type + "_" + GenerationContext.nextId();
        this.cast = cast;
        getter = getMethod.getName();
        if ( Wrapper.class.isAssignableFrom(clazz)) {
            getter = "event()." + getMethod.getName();
        }
        sourceInstances = new ArrayList();
    }

    public void addInstances(Object... instances) {
        for (Object instance : instances) {
            if (sourceInstances.contains(instances)) {
                continue;
            }
            sourceInstances.add(instance);
            count++;
        }
    }
}
