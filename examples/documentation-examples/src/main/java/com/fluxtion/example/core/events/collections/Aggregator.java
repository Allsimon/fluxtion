/*
 * Copyright (C) 2018 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.fluxtion.example.core.events.collections;

import com.fluxtion.api.annotations.OnEvent;
import com.fluxtion.api.annotations.OnParentUpdate;
import java.util.List;

/**
 *
 * @author V12 Technology Ltd.
 */
public class Aggregator {

    private final Object[] handlers;
    private final List<ConfigHandler> cfgHandlers;

    public Aggregator(Object[] handlers, List<ConfigHandler> cfgHandlers) {
        this.handlers = handlers;
        this.cfgHandlers = cfgHandlers;
    }

    @OnParentUpdate("handlers")
    public void parentUdated(Object parent) {

    }

    @OnParentUpdate
    public void parentCfgUdated(ConfigHandler parent) {

    }
    
    @OnEvent
    public void update(){
        
    }

}
