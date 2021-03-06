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
package com.fluxtion.example.core.outstyle.naming;

import com.fluxtion.builder.generation.FilterDescription;
import com.fluxtion.builder.generation.FilterDescriptionProducer;
import com.fluxtion.builder.generation.NodeNameProducer;
import com.fluxtion.example.shared.DataEvent;
import com.fluxtion.api.event.Event;
import com.google.auto.service.AutoService;

/**
 *
 * @author V12 Technology Ltd.
 */
@AutoService({NodeNameProducer.class, FilterDescriptionProducer.class})
public class NamingStrategy implements NodeNameProducer, FilterDescriptionProducer{

    @Override
    public String mappedNodeName(Object nodeToMap) {
        if(nodeToMap instanceof DataHandler){
            return "handler_" + ((DataHandler)nodeToMap).filter;
        }
        return null;
    }

    @Override
    public FilterDescription getFilterDescription(Class<? extends Event> event, String filterString) {
        FilterDescription filterDescription = FilterDescriptionProducer.super.getFilterDescription(event, filterString); 
        if(event == DataEvent.class){
            filterDescription.comment = "filtering for " + filterString;
        }
        return filterDescription;
    }
    
}
