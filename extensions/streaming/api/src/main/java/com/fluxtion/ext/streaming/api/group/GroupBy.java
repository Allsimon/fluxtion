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
package com.fluxtion.ext.streaming.api.group;

import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.api.Stateful;
import com.fluxtion.ext.streaming.api.stream.StreamOperator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Runtime interface for GroupBy instances generated by SEP.
 *
 * @param <T> he target type of the group
 * @author greg
 */
public interface GroupBy<K, T> extends Wrapper<T>, Stateful {

    T value(K key);

    default T value(int i) {
        return null;
    }

    <V extends Wrapper<T>> Map<K, V> getMap();

    default List<T> expireTime(Long time, int joinNumber) {
        return Collections.emptyList();
    }

    default List<T> expireCount(Long time, int joinNumber) {
        return Collections.emptyList();
    }

    default List<T> expireAll() {
        return Collections.emptyList();
    }

    @Override
    default GroupBy<K, T> notifierOverride(Object eventNotifier) {
        return (GroupBy<K, T>) StreamOperator.service().notifierOverride(this, eventNotifier);
    }

    @Override
    default void reset() {
        //no-op
    }

}
