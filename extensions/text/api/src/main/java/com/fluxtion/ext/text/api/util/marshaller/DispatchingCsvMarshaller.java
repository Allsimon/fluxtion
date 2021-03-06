package com.fluxtion.ext.text.api.util.marshaller;

import com.fluxtion.api.event.Event;
import com.fluxtion.api.lifecycle.BatchHandler;
import com.fluxtion.api.lifecycle.EventHandler;
import com.fluxtion.api.lifecycle.Lifecycle;
import com.fluxtion.ext.text.api.ascii.Csv2ByteBufferTemp;
import com.fluxtion.ext.text.api.event.CharEvent;

public class DispatchingCsvMarshaller implements EventHandler, BatchHandler, Lifecycle {

    //Node declarations
    private final Csv2ByteBufferTemp csv2ByteBufferTemp_1 = new Csv2ByteBufferTemp();
    public final CsvMultiTypeMarshaller dispatcher = new CsvMultiTypeMarshaller();
    //Dirty flags
    private boolean isDirty_csv2ByteBufferTemp_1 = false;
    private boolean isDirty_dispatcher = false;
    //Filter constants

    public DispatchingCsvMarshaller() {
        csv2ByteBufferTemp_1.fieldNumber = (int) 0;
        csv2ByteBufferTemp_1.headerLines = (int) 0;
        dispatcher.type = csv2ByteBufferTemp_1;
        init();
    }

    public DispatchingCsvMarshaller addMarshaller(Class wrapper, EventHandler handler) {
        dispatcher.addMarshaller(wrapper, handler);
        return this;
    }

    public DispatchingCsvMarshaller addMarshaller(Class wrapper, String handlerClass) throws Exception {
        dispatcher.addMarshaller(wrapper, (EventHandler) Class.forName(handlerClass).newInstance());
        return this;
    }

    public DispatchingCsvMarshaller addSink(EventHandler handler) {
        return addSink(handler, true);
    }

    public DispatchingCsvMarshaller addSink(EventHandler handler, boolean init) {
        dispatcher.setSink(handler);
        if(init){
            if(handler instanceof Lifecycle){
                ((Lifecycle)handler).init();
            }
        }
        return this;
    }

    @Override
    public void onEvent(Event event) {
        switch (event.eventId()) {
            case (CharEvent.ID): {
                CharEvent typedEvent = (CharEvent) event;
                handleEvent(typedEvent);
                break;
            }
        }
    }

    public void handleEvent(CharEvent typedEvent) {
        switch (typedEvent.filterId()) {
            //Event Class:[com.fluxtion.ext.text.api.event.CharEvent] filterId:[10]
            case (10):
                isDirty_csv2ByteBufferTemp_1 = csv2ByteBufferTemp_1.onEol(typedEvent);
                if (isDirty_csv2ByteBufferTemp_1) {
                    dispatcher.onTypeUpdated(csv2ByteBufferTemp_1);
                }
                isDirty_dispatcher = true;
                dispatcher.pushCharToMarshaller(typedEvent);
                afterEvent();
                return;
            //Event Class:[com.fluxtion.ext.text.api.event.CharEvent] filterId:[44]
            case (44):
                isDirty_csv2ByteBufferTemp_1 = csv2ByteBufferTemp_1.onDelimiter(typedEvent);
                if (isDirty_csv2ByteBufferTemp_1) {
                    dispatcher.onTypeUpdated(csv2ByteBufferTemp_1);
                }
                isDirty_dispatcher = true;
                dispatcher.pushCharToMarshaller(typedEvent);
                afterEvent();
                return;
        }
        //Default, no filter methods
        isDirty_csv2ByteBufferTemp_1 = csv2ByteBufferTemp_1.appendToBuffer(typedEvent);
        isDirty_dispatcher = true;
        dispatcher.pushCharToMarshaller(typedEvent);
        //event stack unwind callbacks
        afterEvent();
    }

    @Override
    public void afterEvent() {
        csv2ByteBufferTemp_1.onEventComplete();
        isDirty_csv2ByteBufferTemp_1 = false;
        isDirty_dispatcher = false;
    }

    @Override
    public void init() {
        csv2ByteBufferTemp_1.init();
        dispatcher.init();
    }

    @Override
    public void tearDown() {
        dispatcher.tearDown();
    }

    @Override
    public void batchPause() {
    }

    @Override
    public void batchEnd() {
    }
}
