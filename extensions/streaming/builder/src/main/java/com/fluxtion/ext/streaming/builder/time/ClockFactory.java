/*
 * Copyright (C) 2019 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.ext.streaming.builder.time;

import com.fluxtion.builder.node.NodeFactory;
import com.fluxtion.builder.node.NodeRegistry;
import com.fluxtion.ext.streaming.api.time.Clock;
import com.google.auto.service.AutoService;
import java.util.Map;

/**
 *
 * @author V12 Technology Ltd.
 */
@AutoService(NodeFactory.class)
public class ClockFactory implements NodeFactory<Clock>{

    private static Clock SINGLETON = new  Clock();
    
    @Override
    public Clock createNode(Map config, NodeRegistry registry) {
        registry.registerAuditor(SINGLETON, "clock");
        return SINGLETON;
    }
    
}
