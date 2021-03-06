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
 * Server Side License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.example.core.events.propagation.generated;

import com.fluxtion.api.lifecycle.BatchHandler;
import com.fluxtion.api.lifecycle.EventHandler;
import com.fluxtion.api.lifecycle.Lifecycle;
import com.fluxtion.example.core.events.propagation.PropagateControlledNode;
import com.fluxtion.example.core.events.propagation.PropagateControlledhandler;
import com.fluxtion.example.shared.ConfigEvent;
import com.fluxtion.example.shared.DataEvent;
import com.fluxtion.example.shared.DataEventHandler;
import com.fluxtion.example.shared.MyEvent;

public class SampleProcessor implements EventHandler, BatchHandler, Lifecycle {

  //Node declarations
  private final DataEventHandler dataEventHandler_1 = new DataEventHandler();
  private final PropagateControlledhandler propagateControlledhandler_3 =
      new PropagateControlledhandler();
  private final PropagateControlledNode propagateControlledNode_5 =
      new PropagateControlledNode(propagateControlledhandler_3, dataEventHandler_1);
  //Dirty flags

  //Filter constants

  public SampleProcessor() {}

  @Override
  public void onEvent(com.fluxtion.api.event.Event event) {
    switch (event.getClass().getName()) {
      case ("com.fluxtion.example.shared.ConfigEvent"):
        {
          ConfigEvent typedEvent = (ConfigEvent) event;
          handleEvent(typedEvent);
          break;
        }
      case ("com.fluxtion.example.shared.DataEvent"):
        {
          DataEvent typedEvent = (DataEvent) event;
          handleEvent(typedEvent);
          break;
        }
      case ("com.fluxtion.example.shared.MyEvent"):
        {
          MyEvent typedEvent = (MyEvent) event;
          handleEvent(typedEvent);
          break;
        }
    }
  }

  public void handleEvent(ConfigEvent typedEvent) {
    //Default, no filter methods
    propagateControlledhandler_3.silentEvent(typedEvent);
    //event stack unwind callbacks
    afterEvent();
  }

  public void handleEvent(DataEvent typedEvent) {
    //Default, no filter methods
    dataEventHandler_1.handleEvent(typedEvent);
    //event stack unwind callbacks
    afterEvent();
  }

  public void handleEvent(MyEvent typedEvent) {
    //Default, no filter methods
    propagateControlledhandler_3.broadcastEvent(typedEvent);
    propagateControlledNode_5.update();
    //event stack unwind callbacks
    afterEvent();
  }

  @Override
  public void afterEvent() {}

  @Override
  public void init() {}

  @Override
  public void tearDown() {}

  @Override
  public void batchPause() {}

  @Override
  public void batchEnd() {}
}
