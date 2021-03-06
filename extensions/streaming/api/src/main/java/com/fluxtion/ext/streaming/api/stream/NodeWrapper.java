package com.fluxtion.ext.streaming.api.stream;

import com.fluxtion.api.annotations.OnEvent;
import com.fluxtion.ext.streaming.api.Wrapper;
import lombok.EqualsAndHashCode;

/**
 * simple wrapper that wraps any node.
 * 
 * @author V12 Technology Ltd.
 * @param <T> 
 */
@EqualsAndHashCode(of = {"node"})
public class NodeWrapper<T> implements Wrapper<T> {

    private final T node;

    public NodeWrapper(T node) {
        this.node = node;
    }
    
    @OnEvent
    public boolean onEvent(){
        return true;
    }

    @Override
    public T event() {
        return node;
    }

    @Override
    public Class<T> eventClass() {
        return (Class<T>) node.getClass();
    }
}
