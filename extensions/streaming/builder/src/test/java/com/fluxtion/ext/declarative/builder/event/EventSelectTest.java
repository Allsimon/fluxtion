/* 
 *  Copyright (C) [2016]-[2017] V12 Technology Limited
 *  
 *  This software is subject to the terms and conditions of its EULA, defined in the
 *  file "LICENCE.txt" and distributed with this software. All information contained
 *  herein is, and remains the property of V12 Technology Limited and its licensors, 
 *  if any. This source code may be protected by patents and patents pending and is 
 *  also protected by trade secret and copyright law. Dissemination or reproduction 
 *  of this material is strictly forbidden unless prior written permission is 
 *  obtained from V12 Technology Limited.  
 */
package com.fluxtion.ext.declarative.builder.event;

import com.fluxtion.ext.declarative.builder.helpers.DataEvent;
import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.builder.event.EventSelect;
import com.fluxtion.generator.util.BaseSepTest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Greg Higgins
 */
public class EventSelectTest extends BaseSepTest{

    @Test
    public void testSimpleSelect() throws Exception {
        Wrapper<DataEvent> dataHandler = EventSelect.select(DataEvent.class);
        Wrapper<DataEvent> dataHandler_1 = EventSelect.select(DataEvent.class);
        Assert.assertEquals(dataHandler, dataHandler_1);
        
        
        Wrapper<DataEvent> dataHandler_int = EventSelect.select(DataEvent.class, 200);
        Wrapper<DataEvent> dataHandler_int2 = EventSelect.select(DataEvent.class, 200);
        Wrapper<DataEvent> dataHandler_int3 = EventSelect.select(DataEvent.class, 5454);
        Assert.assertEquals(dataHandler_int, dataHandler_int2);
        Assert.assertNotEquals(dataHandler_int, dataHandler_int3);
        Assert.assertNotEquals(dataHandler, dataHandler_int2);
        
        Wrapper<DataEvent> dataHandler_String = EventSelect.select(DataEvent.class, "Hello");
        Wrapper<DataEvent> dataHandler_String_1 = EventSelect.select(DataEvent.class, "Hello");
        Wrapper<DataEvent> dataHandler_String_bye = EventSelect.select(DataEvent.class, "bye");
        Assert.assertEquals(dataHandler_String, dataHandler_String_1);
        Assert.assertNotEquals(dataHandler_String, dataHandler_String_bye);
        Assert.assertNotEquals(dataHandler, dataHandler_String);
        Assert.assertNotEquals(dataHandler_int, dataHandler_String);
        
        Assert.assertEquals(dataHandler.eventClass(), DataEvent.class);
        Assert.assertEquals(dataHandler_int.eventClass(), DataEvent.class);
        Assert.assertEquals(dataHandler_String.eventClass(), DataEvent.class);
    }
}
